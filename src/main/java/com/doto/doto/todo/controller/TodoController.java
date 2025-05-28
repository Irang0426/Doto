package com.doto.doto.todo.controller;

import com.doto.doto.todo.dto.TodoDTO;
import com.doto.doto.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {

  private final TodoService todoService;

  @GetMapping("/new")
  public String createForm(Model model) {
    model.addAttribute("todo", new TodoDTO());
    return "todo/form";
  }

  @PostMapping("/todos")
  public String create(@ModelAttribute TodoDTO todoDTO) {
    todoService.save(todoDTO);
    return "redirect:/todo/todos";
  }

  @GetMapping("/todos")
  public String list(Model model) {
    model.addAttribute("todos", todoService.findAll());
    return "todo/todos";
  }
}
