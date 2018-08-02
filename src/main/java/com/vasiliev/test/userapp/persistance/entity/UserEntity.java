package com.vasiliev.test.userapp.persistance.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

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

    public UserEntity() {
        super();
        this.enabled = true;
        this.emailVerified = false;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Timestamp getChangedAt() {
        return changedAt;
    }

    public String getChangedAtStr() {
        return changedAt == null ? null : OffsetDateTime.of(changedAt.toLocalDateTime(), ZoneOffset.UTC).withOffsetSameInstant(OffsetDateTime.now().getOffset()).format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss"));
    }

    public void setChangedAtStr(String changedAt) {
//        this.changedAt = Timestamp.valueOf(LocalDateTime.parse(changedAt, DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")));
        this.changedAt = Timestamp.valueOf(changedAt);
    }

    @Deprecated
    public void setChangedAt(Timestamp changedAt) {
        this.changedAt = changedAt;
    }

    public String getChangedBy() {
        return changedBy;
    }

    @Deprecated
    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public OffsetDateTime changedAtOffsetDateTime() {
        return changedAt == null ? null : OffsetDateTime.of(changedAt.toLocalDateTime(), ZoneOffset.UTC);
    }

    @Basic
    @Column(name = "created_at", nullable = false)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    @Deprecated
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime createdAtOffsetDateTime() {
        return createdAt == null ? null : OffsetDateTime.of(createdAt.toLocalDateTime(), ZoneOffset.UTC);
    }

    @PrePersist
    protected void beforeInsert() {
        createdAt = Timestamp.valueOf(OffsetDateTime.now().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
        changedAt = Timestamp.valueOf(OffsetDateTime.now().atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());
    }

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
