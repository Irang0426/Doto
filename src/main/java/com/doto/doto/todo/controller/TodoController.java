package com.doto.doto.todo.controller;

import com.doto.doto.todo.dto.TodoDTO;
import com.doto.doto.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {

  private final TodoService todoService;

  @GetMapping("/new")
  public String showForm(@RequestParam(value = "date", required = false) String date, Model model) {
    TodoDTO todo = new TodoDTO();
    if (date != null && !date.isEmpty()) {
      todo.setStartDate(LocalDate.parse(date));  // 날짜를 시작일로 세팅
    }
    model.addAttribute("todo", todo);
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

  @GetMapping("/calendar")
  public String calendar(Model model) {
    model.addAttribute("todos", todoService.findAll());
    return "todo/calendar";
  }
}
