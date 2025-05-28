package com.doto.doto.user.service;

import com.doto.doto.user.dto.UserDTO;
import com.doto.doto.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  @Override
  public UserDTO register(UserDTO userDTO) {
    User user = User.builder()
        .email(userDTO.getEmail())
        .password(passwordEncoder.encode(userDTO.getPassword()))
        .nickname(userDTO.getNickname())
        .username(userDTO.getUsername())
        .grade(userDTO.getGrade())
        .build();

    User saved = userRepository.save(user);
    return toDto(saved);
  }

  @Override
  public List<UserDTO> findAll() {
    return userRepository.findAll().stream()
        .map(this::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public UserDTO findById(Long id) {
    com.doto.doto.user.entity.User user = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    return toDto(user);
  }

  private UserDTO toDto(User user) {
    return UserDTO.builder()
        .id(user.getId())
        .email(user.getEmail())
        .nickname(user.getNickname())
        .username(user.getUsername())
        .grade(user.getGrade())
        .build();
  }
}
