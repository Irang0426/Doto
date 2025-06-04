package com.doto.doto.todo.controller;

import com.doto.doto.security.CustomUserDetails;
import com.doto.doto.todo.dto.TodoDTO;
import com.doto.doto.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {

  private final TodoService todoService;

  // 할 일 추가 폼 보여주기
  @GetMapping("/new")
  public String showForm(@RequestParam(value = "date",required = false) String date,
                         @AuthenticationPrincipal CustomUserDetails userDetails,
                         Model model) {
    TodoDTO todo = new TodoDTO();
    if (date == null || date.isEmpty()) {
      date = LocalDate.now().toString();  // 오늘 날짜를 기본값으로
    }

    LocalDate startDate = LocalDate.parse(date);
    todo.setStartDate(startDate);
    todo.setUserId(userDetails.getUser().getId()); // 로그인한 사용자 ID 주입
    model.addAttribute("todo", todo);
    model.addAttribute("startDateString", startDate.toString());
    return "todo/form";
  }

  // 할 일 추가
  @PostMapping("/todos")
  public String create(@ModelAttribute TodoDTO todoDTO,
                       @RequestParam(value = "completed", required = false) String completedStr,
                       @RequestParam(value = "redirect", required = false) String redirectUrl) {

    int completed = "1".equals(completedStr) ? 1 : 0;
    todoDTO.setCompleted(completed);

    todoService.save(todoDTO);

    return (redirectUrl != null && !redirectUrl.isEmpty())
        ? "redirect:" + redirectUrl
        : "redirect:/todo/todos";
  }



  // 할 일 수정 폼 보여주기
  @GetMapping("/todos/{id}/edit")
  public String showEditForm(@PathVariable Long id, Model model) {
    TodoDTO todoDTO = todoService.findById(id);
    model.addAttribute("todo", todoDTO);

    // 포맷된 날짜 문자열 추가
    if (todoDTO.getStartDate() != null) {
      model.addAttribute("startDateString", todoDTO.getStartDate().toString()); // LocalDate는 기본 yyyy-MM-dd 포맷이라 OK
    } else {
      model.addAttribute("startDateString", "");
    }
    return "todo/form";
  }

  // 할 일 수정 처리
  @PostMapping("/todos/{id}/edit")
  public String update(@PathVariable Long id,
                       @ModelAttribute TodoDTO todoDTO,
                       @RequestParam(value = "completed", required = false) String completedStr) {

    int completed = "1".equals(completedStr) ? 1 : 0;
    todoDTO.setCompleted(completed);

    todoService.update(id, todoDTO);
    return "redirect:/todo/todos";
  }


  // 삭제한 목록
  @GetMapping("/trash")
  public String trash(Model model) {
    model.addAttribute("todos", todoService.findDeleted());  // is_delete = 1
    return "todo/trash";
  }

  // 할 일 소프트 삭제
  @PostMapping("/todos/{id}/softDelete")
  public String softDelete(@PathVariable Long id) {
    todoService.softdelete(id);
    return "redirect:/todo/todos";
  }

  // 할 일 복원
  @PostMapping("/todos/{id}/restore")
  public String restore(@PathVariable Long id) {
    todoService.restore(id);
    return "redirect:/todo/trash";
  }

  // 할 일 완전히 삭제
  @PostMapping("/todos/{id}/delete")
  public String delete(@PathVariable Long id) {
    todoService.delete(id);
    return "redirect:/todo/trash";
  }

  // 전체 삭제
  @PostMapping("/todos/deleteAll")
  public String deleteAllInTrash() {
    todoService.deleteAllInTrash();
    return "redirect:/todo/trash";
  }

  // 할 일 목록
  @GetMapping("/todos")
  public String list(Model model) {
    model.addAttribute("todos", todoService.findAll());
    return "todo/todos";
  }

  // 달력
  @GetMapping("/calendar")
  public String calendar(Model model) {
    model.addAttribute("todos", todoService.findAll());
    return "todo/calendar";
  }

  @GetMapping("/calendar/events")
  @ResponseBody
  public List<Map<String, Object>> getCalendarEvents(@AuthenticationPrincipal CustomUserDetails userDetails) {
    Long userId = userDetails.getUser().getId();

    List<TodoDTO> todos = todoService.findAll().stream()
            .filter(todo -> todo.getUserId() != null && todo.getUserId().equals(userId))
            .filter(todo -> Integer.valueOf(0).equals(todo.getIsDelete()))
            .filter(todo -> todo.getStartDate() != null)
            .collect(Collectors.toList());

    List<Map<String, Object>> events = new ArrayList<>();
    for (TodoDTO todo : todos) {
      Map<String, Object> event = new HashMap<>();
      event.put("id", todo.getId());
      event.put("title", todo.getTitle());
      event.put("start", todo.getStartDate().toString());

      if (todo.getEndDate() != null) {
        event.put("end", todo.getEndDate().plusDays(1).toString());
      }

      events.add(event);
    }
    return events;
  }

}
