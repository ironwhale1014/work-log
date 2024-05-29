package com.honeybee.work_log.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.honeybee.work_log.domain.SiteUser}
 */
@Value
public class AddUserRequest implements Serializable {
    String username;
    String password;
    String email;
}