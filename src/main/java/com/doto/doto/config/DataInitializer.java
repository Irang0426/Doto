package com.doto.doto.config;

import com.doto.doto.user.dto.UserDTO;
import com.doto.doto.user.enums.Grade;
import com.doto.doto.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserService userService;

    @Override
    public void run(String... args) {
        UserDTO testUser = UserDTO.builder()
                .email("123@123")
                .password("123")
                .nickname("testnick")
                .username("testuser")
                .grade(Grade.BASIC)
                .build();

        userService.register(testUser);
    }
}

