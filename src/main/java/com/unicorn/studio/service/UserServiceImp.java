package com.unicorn.studio.service;



import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import com.unicorn.studio.exception.NotFoundException;
import com.unicorn.studio.exception.TokenExistsException;
import com.unicorn.studio.exception.TokenExpiredException;
import com.unicorn.studio.projection.UserProjection;
import com.unicorn.studio.utils.TokenGenerator;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import com.unicorn.studio.dao.*;
import com.unicorn.studio.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MediaStorageRepository mediaStorageRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MailerService mailerService;

    public UserServiceImp(NotificationRepository notificationRepository, RoleRepository roleRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    /**
     * Get all notifications
     * @return List<Notification>
     */
    @Override
    @Transactional
    public List<Notification> getNotifications() {
        return notificationRepository.findAll();
    }

    /**
     * Save notification
     * @param notification
     */
    @Override
    @Transactional
    public void saveNotification(Notification notification) {
        notificationRepository.save(notification);
    }

    /**
     * Get notification by id
     * @param id
     * @return notification
     */
    @Override
    @Transactional
    public Notification getNotification(long id) {
        Optional<Notification> result = notificationRepository.findById(id);
        Notification notification = null;
        if (result.isPresent()) {
            notification = result.get();
        }
        else {
            throw new NotFoundException("Did not find notification id: " + id);
        }
        return notification;
    }

    /**
     * Delete notification by id
     * @param id
     */
    @Override
    @Transactional
    public void deleteNotification(long id) {
        notificationRepository.deleteById(id);
    }

    /**
     * Get all user roles
     * @return List<Role>
     */
    @Override
    @Transactional
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    /**
     * Save role
     * @param role
     */
    @Override
    @Transactional
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    /**
     * Get role by id
     * @param id
     * @return role
     */
    @Override
    @Transactional
    public Role getRole(long id) {
        Optional<Role> result = roleRepository.findById(id);
        Role role = null;
        if (result.isPresent()) {
            role = result.get();
        }
        else {
            throw new NotFoundException("Did not find role id: " + id);
        }
        return role;
    }

    /**
     * Delete role by id
     * @param id
     */
    @Override
    @Transactional
    public void deleteRole(long id) {
        roleRepository.deleteById(id);
    }

    /**
     * Get all users
     * @return List<User>
     */
    @Override
    @Transactional
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Save user
     * @param user
     */
    @Override
    @Transactional
    public void saveUser(User user) {
        String token = TokenGenerator.generateToken(user.getEmail());
        LocalDateTime timeWindow = TokenGenerator.generateTimeWindow(24);
        if (getUserByEmail(user.getEmail()) == null) {
            user.setConfirmationToken(token);
            user.setConfirmationSendAt(LocalDateTime.now());
            user.setConfirmationTokenExpireAt(timeWindow);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
//            mailerService.sendEmail(user.getFullName(), user.getEmail(), "confirm", token);
        }
        else {
            userRepository.save(user);
        }
    }

    /**
     * Update user
     * @param user
     */
    @Override
    @Transactional
    public void updateUser(User user) {
        User authUser = userRepository.findByEmail(user.getEmail()).get();
        if (!authUser.getFullName().equals(user.getFullName()) && !user.getFullName().equals("")) {
            authUser.setFullName(user.getFullName());
            saveUser(authUser);
        }
    }

    /**
     * Get user by id
     * @param uid
     * @return user
     */
    @Override
    @Transactional
    public UserProjection getUser(String uid) {
        Optional<UserProjection> result = userRepository.findUserByUid(uid);
        UserProjection userProjection = null;
        if (result.isPresent()) {
            userProjection = result.get();
        }
        else {
            throw new NotFoundException("Did not find user id: " + uid);
        }
        return userProjection;
    }

    /**
     * Delete user by uid
     * @param uid
     */
    @Override
    @Transactional
    public void deleteUser(String uid) {
        Optional<User> result = userRepository.findByUid(uid);
        if (result.isPresent()) {
            userRepository.deleteByUid(uid);
        }
        else {
            throw new NotFoundException("Did not find user id: " + uid);
        }
    }

    /**
     * Get user by email id
     * @param email
     * @return User
     */
    @Override
    @Transactional
    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    /**
     * Get user basic details by email id
     * @param email
     * @return UserProjection
     */
    @Override
    @Transactional
    public UserProjection getUserBasicDetailsByEmail(String email) {
        Optional<UserProjection> result = userRepository.findUserByEmail(email);
        UserProjection userProjection = null;
        if (result.isPresent()) {
            userProjection = result.get();
        }
        else {
            throw new NotFoundException("User not found");
        }
        return userProjection;
    }

    /**
     * Confirm user email address after sign up
     * @param email
     * @param token
     */
    @Override
    @Transactional
    public void emailConfirmation(String email, String token) {
        User user = userRepository.findByEmail(email).get();
        StripeCustomer customer = new StripeCustomer();
        customer.setName(user.getFullName());
        customer.setEmail(user.getEmail());
        if (user != null && user.getConfirmationToken().equals(token)) {
            user.setConfirmationToken(null);
            user.setConfirmationSendAt(null);
            user.setConfirmationTokenExpireAt(null);
            user.setCustomer(customer);
            userRepository.save(user);
//            mailerService.sendEmail(user.getFullName(), email,"verified",null);
        } else {
            throw new NotFoundException("Invalid token");
        }
    }

    /**
     * Update user password
     * @param obj
     * @param userId
     * @return user
     */
    @Override
    @Transactional
    public User changePassword(Object obj, String userId) {
        User user = userRepository.findByUid(userId).get();
        String oldPassword = ((LinkedHashMap)obj).get("oldPassword").toString();
        String newPassword = ((LinkedHashMap)obj).get("newPassword").toString();
        if (!passwordEncoder.matches(oldPassword,user.getPassword())) {
            return null;
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        saveUser(user);
        return user;
    }

    /**
     * Generate and send by email password reset token
     * @param email
     */
    @Override
    @Transactional
    public void resetPasswordTokenGeneration(String email) {
        User user = userRepository.findByEmail(email).get();
        if (user != null && user.getPasswordResetToken().equals(null)) {
            String token = TokenGenerator.generateToken(email);
            LocalDateTime timeWindow = TokenGenerator.generateTimeWindow(2);
            user.setPasswordResetToken(token);
            user.setPasswordResetSendAt(LocalDateTime.now());
            user.setPasswordResetTokenExpireAt(timeWindow);
            userRepository.save(user);
//            mailerService.sendEmail(user.getFullName(), email,"password_reset",token);
        } else if (user != null && !user.getPasswordResetToken().equals(null)) {
            throw new TokenExistsException("Reset password token already send to your registered email");
        }
        else {
            throw new NotFoundException("Did not find user email:" + email);
        }
    }

    /**
     * Validate reset password token
     * @param email
     * @param token
     * @return
     */
    @Override
    @Transactional
    public boolean validateResetPasswordToken(String email, String token) {
        User user = userRepository.findByEmail(email).get();
        if (user == null || !user.getPasswordResetToken().equals(token)) {
            throw new NotFoundException("Invalid token");
        }
        else if (user.getPasswordResetTokenExpireAt().isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException("This token has been expired");
        }
        return true;
    }

    /**
     * Reset password from generated reset password token
     * @param email
     * @param token
     * @param password
     */
    @Override
    @Transactional
    public void resetPassword(String email, String token, String password) {
        User user = userRepository.findByEmail(email).get();
        if (user != null && user.getPasswordResetToken().equals(token)) {
            user.setPasswordResetToken(null);
            user.setPasswordResetSendAt(null);
            user.setPasswordResetTokenExpireAt(null);
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
//            mailerService.sendEmail(user.getFullName(), email,"password_changed",null);
        } else {
            throw new NotFoundException("Invalid token");
        }
    }

    /**
     * Get role by name
     * @param name
     * @return Role
     */
    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    /**
     * Get authenticated user id
     * @return userId
     */
    @Override
    public User getUserIdByAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getName().equals("anonymousUser")) {
            return null;
        }
        return getUserByEmail(auth.getName());
    }
}
