//package com.honeybee.work_log.service;
//
//import com.honeybee.work_log.domain.SiteUser;
//import com.honeybee.work_log.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//
//    final private UserRepository userRepository;
//    final private PasswordEncoder encoder;
//
//    public SiteUser createSiteUser(String username, String password, String email) {
//
//        SiteUser siteUser = SiteUser.builder().username(username).password(encoder.encode(password)).email(email).build();
//        userRepository.save(siteUser);
//        return siteUser;
//    }
//}
