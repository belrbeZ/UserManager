package com.vasiliev.test.userapp.persistance.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * The type User entity.
 *
 * @author Alexandr Vasiliev <alexandrvasilievby@gmail.com>
 */
@Entity
@Table(name = "user_account")
public class UserEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, columnDefinition = "CHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(length = 60)
    private String password;

    private boolean enabled;

    private boolean emailVerified;

    private Timestamp createdAt;

    @Basic
    @Column(name = "changed_at", nullable = false)
    private Timestamp changedAt;

    @Basic
    @Column(name = "changed_by", nullable = false, length = 32)
    private String changedBy;

    /**
     * Instantiates a new User entity.
     */
    public UserEntity() {
        super();
        this.enabled = true;
        this.emailVerified = false;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Is enabled boolean.
     *
     * @return the boolean
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets enabled.
     *
     * @param enabled the enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Is email verified boolean.
     *
     * @return the boolean
     */
    public boolean isEmailVerified() {
        return emailVerified;
    }

    /**
     * Sets email verified.
     *
     * @param emailVerified the email verified
     */
    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    /**
     * Gets changed at.
     *
     * @return the changed at
     */
    public Timestamp getChangedAt() {
        return changedAt;
    }

    /**
     * Sets changed at.
     *
     * @param changedAt the changed at
     */
    @Deprecated
    public void setChangedAt(Timestamp changedAt) {
        this.changedAt = changedAt;
    }

    /**
     * Gets changed at str.
     *
     * @return the changed at str
     */
    public String getChangedAtStr() {
        return changedAt == null ? null : OffsetDateTime.of(changedAt.toLocalDateTime(), ZoneOffset.UTC).withOffsetSameInstant(OffsetDateTime.now().getOffset()).format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss"));
    }

    /**
     * Sets changed at str.
     *
     * @param changedAt the changed at
     */
    public void setChangedAtStr(String changedAt) {
//        this.changedAt = Timestamp.valueOf(LocalDateTime.parse(changedAt, DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")));
        this.changedAt = Timestamp.valueOf(changedAt);
    }

    /**
     * Gets changed by.
     *
     * @return the changed by
     */
    public String getChangedBy() {
        return changedBy;
    }

    /**
     * Sets changed by.
     *
     * @param changedBy the changed by
     */
    @Deprecated
    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    /**
     * Changed at offset date time offset date time.
     *
     * @return the offset date time
     */
    public OffsetDateTime changedAtOffsetDateTime() {
        return changedAt == null ? null : OffsetDateTime.of(changedAt.toLocalDateTime(), ZoneOffset.UTC);
    }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    @Basic
    @Column(name = "created_at", nullable = false)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets created at.
     *
     * @param createdAt the created at
     */
    @Deprecated
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Created at offset date time offset date time.
     *
     * @return the offset date time
     */
    public OffsetDateTime createdAtOffsetDateTime() {
        return createdAt == null ? null : OffsetDateTime.of(createdAt.toLocalDateTime(), ZoneOffset.UTC);
    }

    /**
     * Before insert.
     */
    @PrePersist
    protected void beforeInsert() {
        createdAt = Timestamp.valueOf(OffsetDateTime.now().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
        changedAt = Timestamp.valueOf(OffsetDateTime.now().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
    }

    /**
     * Before update.
     */
    @PreUpdate
    protected void beforeUpdate() {
        changedAt = Timestamp.valueOf(OffsetDateTime.now().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("User [id=").append(id).append(", firstName=").append(firstName).append(", lastName=").append(lastName).append(", email=").append(email).append(", password=").append(password).append(", enabled=").append(enabled).append(", isUsing2FA=")
                .append(", secret=").append(", roles=").append("]");
        return builder.toString();
    }
}
