package com.honeybee.work_log.service;

import com.honeybee.work_log.domain.SiteUser;
import com.honeybee.work_log.dto.AddUserRequest;
import com.honeybee.work_log.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {


    final private UserRepository userRepository;
    final private BCryptPasswordEncoder passwordEncoder;


    public Long save(AddUserRequest dto) {
        return userRepository.save(SiteUser.builder().username(dto.getUsername())
                .email(dto.getEmail()).password(passwordEncoder.encode(dto.getPassword())).build()).getId();
    }
}
