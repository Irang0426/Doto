package com.doto.doto.user.controller;

import com.doto.doto.user.dto.UserDTO;
import com.doto.doto.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
  private final UserService userService;

  // 회원가입 폼
  @GetMapping("/register")
  public String registerForm(Model model) {
    model.addAttribute("user", new UserDTO());
    return "user/register";
  }

  // 회원가입 처리
  @PostMapping("/register")
  public String register(@ModelAttribute UserDTO userDTO) {
    userService.register(userDTO);
    return "redirect:/login";  // 회원가입 후 로그인 페이지로 이동
  }

  // 회원 리스트 조회 (관리용)
  @GetMapping
  public String list(Model model) {
    model.addAttribute("users", userService.findAll());
    return "user/list";
  }

  // 회원 상세 조회
  @GetMapping("/{id}")
  public String detail(@PathVariable Long id, Model model) {
    model.addAttribute("user", userService.findById(id));
    return "user/detail";
  }
}
