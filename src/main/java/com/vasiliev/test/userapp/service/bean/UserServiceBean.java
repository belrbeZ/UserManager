package com.vasiliev.test.userapp.service.bean;

import com.vasiliev.test.userapp.model.User;
import com.vasiliev.test.userapp.model.UserList;
import com.vasiliev.test.userapp.model.UserManageEvent;
import com.vasiliev.test.userapp.model.UserRegisterInvoice;
import com.vasiliev.test.userapp.persistance.dao.UserRepository;
import com.vasiliev.test.userapp.persistance.entity.UserEntity;
import com.vasiliev.test.userapp.service.UserService;
import com.vasiliev.test.userapp.util.reliability.OperationException;
import com.vasiliev.test.userapp.util.reliability.OperationResultStatus;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceBean implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    public ApplicationEventPublisher eventPublisher;

    @Autowired
    @Qualifier("userMapper")
    private Mapper userMapper;

    @Override
    public User registerNewUserAccount(final UserRegisterInvoice userInvoice) {
        final UserEntity userEntity = userMapper.map(userInvoice, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userInvoice.getPassword()));
        String changedBy = null;
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            changedBy = SecurityContextHolder.getContext().getAuthentication().getName();
        } else {
            changedBy = "System";
        }
        userEntity.setChangedBy(changedBy);
        UserEntity savedUser = null;
        synchronized (UserServiceBean.class) {
            if (emailExist(userInvoice.getEmail())) {
                throw new OperationException(OperationResultStatus.FAILURE_ALREADY_EXISTS, "There is an account with that email adress: " + userInvoice.getEmail());
            }
            savedUser = userRepository.save(userEntity);
        }
        eventPublisher.publishEvent(new UserManageEvent()
                .uuid(savedUser.getId().toString())
                .eventType(UserManageEvent.EventTypeEnum.CREATED)
                .date(OffsetDateTime.now()));
        return userMapper.map(savedUser, User.class);
    }

    @Override
    public UserList getAllUsers() {
        List<User> userList = userRepository.findAll().stream().map(usr -> userMapper.map(usr, User.class)).collect(Collectors.toList());
        UserList users = new UserList();
        users.addAll(userList);
        return users;
    }

    @Override
    public void deleteUser(final String id) {
        if (!userRepository.exists(UUID.fromString(id))) {
            throw new OperationException(OperationResultStatus.FAILURE_LOGIC_RECORD_NOT_FOUND, "get.id.incorrect");
        }
        userRepository.delete(UUID.fromString(id));
    }

    @Override
    public User getUserByID(final String id) {
        return userMapper.map(userRepository.findOne(UUID.fromString(id)), User.class);
    }

    @Override
    public User updateUser(String id, User userUpdateInvoice) {
        userUpdateInvoice.setId(id);
//        checkIfValidOldPassword(userUpdateInvoice, userRepository.getOne(UUID.fromString(id)).getPassword());
        UserEntity userEntityToSave = userMapper.map(userUpdateInvoice, UserEntity.class);
        String changedBy = null;
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
            changedBy = SecurityContextHolder.getContext().getAuthentication().getName();
        } else {
            changedBy = "System";
        }
        UserEntity userEntityExists = userRepository.findOne(UUID.fromString(id));
        if (userEntityToSave.getFirstName() != null)  {
            userEntityExists.setFirstName(userEntityToSave.getFirstName());
        }
        if (userEntityToSave.getLastName() != null)  {
            userEntityExists.setLastName(userEntityToSave.getLastName());
        }
        userEntityExists.setEnabled(userEntityToSave.isEnabled());
        userEntityExists.setChangedBy(changedBy);
        UserEntity userEntity = null;
        synchronized (UserServiceBean.class) {
            if (userEntityToSave.getEmail() != null && !userEntityToSave.getEmail().equalsIgnoreCase(userEntityExists.getEmail()))  {
                if (emailExistExcludeOwn(userEntityToSave.getEmail(), id)) {
                    throw new OperationException(OperationResultStatus.FAILURE_ALREADY_EXISTS, "There is an account with that email adress: " + userEntityToSave.getEmail());
                }
                userEntityExists.setEmail(userEntityToSave.getEmail());
            }
            userEntity = userRepository.save(userEntityExists);
        }
        eventPublisher.publishEvent(new UserManageEvent()
                .uuid(userEntity.getId().toString())
                .eventType(UserManageEvent.EventTypeEnum.MODIFIED)
                .date(OffsetDateTime.now()));
        return userMapper.map(userEntity, User.class);
    }

    @Override
    public UserEntity findUserFromSecurityContextHolder() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Override
    public List<String> getUsersFromSessionRegistry() {
        return sessionRegistry.getAllPrincipals().stream().filter((u) -> !sessionRegistry.getAllSessions(u, false).isEmpty()).map(t -> ((org.springframework.security.core.userdetails.User) t).getUsername()).collect(Collectors.toList());
    }

    /*private boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }*/

    private boolean emailExist(final String email) {
        return userRepository.existsByEmail(email);
    }

    private boolean emailExistExcludeOwn(final String email, final String id) {
        return userRepository.existsByEmailAndIdIsNot(email, UUID.fromString(id));
    }
}