package com.vasiliev.test.userapp.service.security;

import com.vasiliev.test.userapp.persistance.dao.UserRepository;
import com.vasiliev.test.userapp.persistance.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type User details service bean.
 *
 * @author Alexandr Vasiliev <alexandrvasilievby@gmail.com>
 */
@Service("userDetailsService")
@Transactional
public class UserDetailsServiceBean implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        try {
            final UserEntity user = userRepository.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("No user found with username: " + email);
            }
            User.UserBuilder builder = null;
            builder = org.springframework.security.core.userdetails.User.withUsername(user.getEmail());
            builder.password(user.getPassword());
            builder.disabled(!user.isEnabled());
            builder.roles("USER");
            builder.authorities("ADMIN");
            return builder.build();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
