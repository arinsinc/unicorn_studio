package com.unicorn.studio.entity;



import com.unicorn.studio.utils.IdGenerator;
import com.unicorn.studio.validation.AllowedValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GenericGenerator(name="user_seq", strategy = "sequence")
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "user_seq")
    @Column(name="id")
    private long id;

    @Column(name="uid")
    @NotNull
    @Size(max=64)
    private String uid = IdGenerator.generateUId();

    @Column(name="full_name")
    @NotNull
    @Size(min=2, max=64)
    private String fullName;

    @Column(name="email", unique = true)
    @NotNull
    @Email
    @Size(min=2, max=64)
    private String email;

    @Column(name="password")
    @NotNull
    @Size(max=132)
    private String password;

    @Column(name="is_active")
    @NotNull
    private Boolean isActive = true;

    @Column(name="last_login")
    private LocalDateTime lastLogin;

    @Column(name="provider")
    @AllowedValue(type = "Oauth")
    private String provider;

    @Column(name="confirmation_token")
    @Size(max=512)
    private String confirmationToken;

    @Column(name="confirmation_send_at")
    private LocalDateTime confirmationSendAt;

    @Column(name="confirmation_token_expire_at")
    private LocalDateTime confirmationTokenExpireAt;

    @Column(name="password_reset_token")
    @Size(max=512)
    private String passwordResetToken;

    @Column(name="password_reset_send_at")
    private LocalDateTime passwordResetSendAt;

    @Column(name="password_reset_token_expire_at")
    private LocalDateTime passwordResetTokenExpireAt;

    @Column(name="created_at")
    @NotNull
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    @NotNull
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="role_id",nullable = false)
    private Role role;

    @JsonIgnore
    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade= {CascadeType.ALL})
    private Company company;

    @JsonIgnore
    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade= {CascadeType.ALL})
    private Investor investor;

    @JsonIgnore
    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade= {CascadeType.ALL})
    private StartupProgram startupProgram;

    @JsonIgnore
    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER, cascade= {CascadeType.ALL})
    private StripeCustomer customer;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name="media_id")
    private MediaStorage media;


    public User() {}

    public User(@NotNull @Size(min = 2, max = 64) String fullName, @NotNull @Email @Size(min = 2, max = 64) String email,
                @NotNull @Size(max = 132) String password, @NotNull Boolean isActive, LocalDateTime lastLogin, @Size(max = 32) String provider,
                @Size(max = 512) String confirmationToken, LocalDateTime confirmationSendAt, LocalDateTime confirmationTokenExpireAt,
                @Size(max = 512) String passwordResetToken, LocalDateTime passwordResetSendAt, LocalDateTime passwordResetTokenExpireAt) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.lastLogin = lastLogin;
        this.provider = provider;
        this.confirmationToken = confirmationToken;
        this.confirmationSendAt = confirmationSendAt;
        this.confirmationTokenExpireAt = confirmationTokenExpireAt;
        this.passwordResetToken = passwordResetToken;
        this.passwordResetSendAt = passwordResetSendAt;
        this.passwordResetTokenExpireAt = passwordResetTokenExpireAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public LocalDateTime getConfirmationSendAt() {
        return confirmationSendAt;
    }

    public void setConfirmationSendAt(LocalDateTime confirmationSendAt) {
        this.confirmationSendAt = confirmationSendAt;
    }

    public LocalDateTime getConfirmationTokenExpireAt() {
        return confirmationTokenExpireAt;
    }

    public void setConfirmationTokenExpireAt(LocalDateTime confirmationTokenExpireAt) {
        this.confirmationTokenExpireAt = confirmationTokenExpireAt;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public LocalDateTime getPasswordResetSendAt() {
        return passwordResetSendAt;
    }

    public void setPasswordResetSendAt(LocalDateTime passwordResetSendAt) {
        this.passwordResetSendAt = passwordResetSendAt;
    }

    public LocalDateTime getPasswordResetTokenExpireAt() {
        return passwordResetTokenExpireAt;
    }

    public void setPasswordResetTokenExpireAt(LocalDateTime passwordResetTokenExpireAt) {
        this.passwordResetTokenExpireAt = passwordResetTokenExpireAt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setCustomer(StripeCustomer customer) {
        this.customer = customer;
    }

    public StripeCustomer getCustomer() {
        return customer;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Investor getInvestor() {
        return investor;
    }

    public void setInvestor(Investor investor) {
        this.investor = investor;
    }

    public StartupProgram getStartupProgram() {
        return startupProgram;
    }

    public void setStartupProgram(StartupProgram startupProgram) {
        this.startupProgram = startupProgram;
    }

    public MediaStorage getMedia() {
        return media;
    }

    public void setMedia(MediaStorage media) {
        this.media = media;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", lastLogin=" + lastLogin +
                ", provider='" + provider + '\'' +
                ", confirmationToken='" + confirmationToken + '\'' +
                ", confirmationSendAt=" + confirmationSendAt +
                ", passwordResetToken='" + passwordResetToken + '\'' +
                ", passwordResetSendAt=" + passwordResetSendAt +
                ", role=" + role +
                '}';
    }
}
