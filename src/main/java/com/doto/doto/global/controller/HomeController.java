package com.doto.doto.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
  @GetMapping("/")
  public String index(@RequestParam(value = "logout", required = false) String logout, Model model) {
    if (logout != null) {
      model.addAttribute("message", "로그아웃 되었습니다.");
    }
    return "index";
  }

  @GetMapping("/login")
  public String login(@RequestParam(value = "error", required = false) String error, Model model) {
    if (error != null) {
      model.addAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다.");
    }
    return "login";  // 로그인 뷰 이름
  }

}
