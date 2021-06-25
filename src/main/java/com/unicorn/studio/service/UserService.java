package com.unicorn.studio.service;

import java.util.List;
import java.util.Optional;

import com.unicorn.studio.entity.*;
import com.unicorn.studio.projection.UserProjection;

public interface UserService {
    List<Notification> getNotifications();
    void saveNotification(Notification notification);
    Notification getNotification(long id);
    void deleteNotification(long id);

    List<Role> getRoles();
    void saveRole(Role role);
    Role getRole(long id);
    void deleteRole(long id);
    Role getRoleByName(String name);

    List<User> getUsers();
    void saveUser(User user);
    void updateUser(User user);
    UserProjection getUser(String uid);
    void deleteUser(String uid);
    User getUserByEmail(String email);
    UserProjection getUserBasicDetailsByEmail(String email);
    void emailConfirmation(String email, String token);
    User changePassword(Object obj, String userId);
    void resetPasswordTokenGeneration(String email);
    boolean validateResetPasswordToken(String email, String token);
    void resetPassword(String email, String token, String password);
    User getUserIdByAuthentication();
}
