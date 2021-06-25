package com.unicorn.studio.projection;

import com.unicorn.studio.entity.Role;

public interface UserProjection {
    String getUid();
    String getFullName();
    String getEmail();
    Role getRole();
    MediaStorageProjection getMedia();
}
