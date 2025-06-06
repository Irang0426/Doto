package com.doto.doto.config;

import com.doto.doto.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
  private final CustomUserDetailsService userDetailsService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http //.csrf(csrf -> csrf.disable()) // 개발 시 편의를 위해 임시 비활성화, 운영 시 활성화 권장
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/login", "/user/register", "/css/**", "/js/**", "/images/**").permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(form -> form
            .loginPage("/login")
            .usernameParameter("email")
            .passwordParameter("password")
            .failureUrl("/login?error")
            .defaultSuccessUrl("/todo/todos", true)  // 로그인 성공 후 리다이렉트
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/?logout")  // 로그아웃 홈으로 리다이렉트
            .invalidateHttpSession(true)        // 세션 무효화
            .deleteCookies("JSESSIONID")        // 인증 세션 쿠키 제거
            .clearAuthentication(true)          // 인증 정보 제거
            .permitAll()
        )
        .userDetailsService(userDetailsService);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // AuthenticationManager 빈 등록 (필요 시)
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }
}
