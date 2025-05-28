package com.doto.doto.todo.service;

import com.doto.doto.todo.dto.TodoDTO;
import com.doto.doto.todo.entity.Todo;
import com.doto.doto.todo.repository.TodoRepository;
import com.doto.doto.user.entity.User;
import com.doto.doto.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

  private final TodoRepository todoRepository;
  private final UserRepository userRepository;

  @Override
  public List<TodoDTO> findAll() {
    return null;
  }

  @Override
  public TodoDTO findById(Long id) {
    Todo todo = todoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("할 일을 찾을 수 없습니다."));
    return TodoDTO.builder()
        .id(todo.getId())
        .title(todo.getTitle())
        .content(todo.getContent())
        .build();
  }

  @Override
  public TodoDTO save(TodoDTO dto) {
    User user = userRepository.findById(dto.getUserId())
            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

    Todo todo = Todo.builder()
            .title(dto.getTitle())
            .content(dto.getContent())
            .user(user)   // user 세팅
            .startDate(dto.getStartDate())
            .endDate(dto.getEndDate())
            .priority(dto.getPriority())
            .completed(dto.getCompleted())
            .isDelete(dto.getIsDelete())
            .build();

    Todo saved = todoRepository.save(todo);
    return TodoDTO.builder()
            .id(saved.getId())
            .title(saved.getTitle())
            .content(saved.getContent())
            .userId(saved.getUser().getId())  // userId도 넘기기
            .build();
  }

  @Override
  public TodoDTO update(Long id, TodoDTO dto) {
    return null;
  }

  @Override
  public void delete(Long id) {

  }
}
