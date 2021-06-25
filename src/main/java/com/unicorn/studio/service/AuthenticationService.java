package com.unicorn.studio.service;

import com.unicorn.studio.entity.User;
import com.unicorn.studio.projection.UserProjection;

public interface AuthenticationService {
    UserProjection getAuthenticatedUserByEmail();
}
