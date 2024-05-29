package com.honeybee.work_log.controller;

import com.honeybee.work_log.dto.AddUserRequest;
import com.honeybee.work_log.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @PostMapping("/user")
    public String signup(AddUserRequest req) {
        userService.save(req);
        return "redirect:/login";
    }
}
