package com.honeybee.work_log.service;


import com.honeybee.work_log.domain.SiteUser;
import com.honeybee.work_log.dto.AddUserRequest;
import com.honeybee.work_log.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserService {
    final private UserRepository userRepository;


    public Long save(AddUserRequest dto) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


        return userRepository.save(SiteUser.builder()
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail()).username(dto.getUsername()).build()).getId();
    }

    public SiteUser findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public SiteUser findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

}
