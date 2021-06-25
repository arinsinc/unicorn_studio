package com.unicorn.studio.service;

import com.unicorn.studio.entity.User;
import com.unicorn.studio.projection.UserProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImp implements AuthenticationService {
    @Autowired
    private UserService userService;

    /**
     * Getting logged in user by email id
     * @return authenticatedUser
     */
    @Override
    public UserProjection getAuthenticatedUserByEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getName() == "anonymousUser") {
            return null;
        }
        else {
            return userService.getUserBasicDetailsByEmail(auth.getName());
        }
    }
}
