package com.doto.doto.config;

import com.doto.doto.user.dto.UserDTO;
import com.doto.doto.user.enums.Grade;
import com.doto.doto.user.repository.UserRepository;
import com.doto.doto.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserService userService;
    private final UserRepository userRepository; // 존재 확인용

    @Override
    public void run(String... args) {
        String email = "123@123";

        // 이미 유저가 존재하면 등록하지 않음
        if (userRepository.findByEmail(email).isEmpty()) {
            UserDTO testUser = UserDTO.builder()
                    .email(email)
                    .password("123")
                    .nickname("testnick")
                    .username("testuser")
                    .grade(Grade.BASIC)
                    .build();

            userService.register(testUser);
            System.out.println("테스트 유저 생성 완료");
        } else {
            System.out.println("테스트 유저 이미 존재");
        }
    }
}

