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
    return todoRepository.findAll().stream()
        .filter(todo -> todo.getIsDelete() != 1) // 삭제되지 않은 것만 보여줌
        .map(todo -> TodoDTO.builder()
            .id(todo.getId())
            .title(todo.getTitle())
            .content(todo.getContent())
            .priority(todo.getPriority())
            .completed(todo.getCompleted())
            .startDate(todo.getStartDate())
            .endDate(todo.getEndDate())
            .userId(todo.getUser().getId())
            .build())
        .collect(Collectors.toList());
  }

  // 소프트삭제한 목록 리스트 -> is_delete = 1
  @Override
  public List<TodoDTO> findDeleted() {
    return todoRepository.findByIsDelete(1).stream()
        .map(todo -> TodoDTO.builder()
            .id(todo.getId())
            .title(todo.getTitle())
            .content(todo.getContent())
            .priority(todo.getPriority())
            .completed(todo.getCompleted())
            .startDate(todo.getStartDate())
            .endDate(todo.getEndDate())
            .userId(todo.getUser().getId())
            .isDelete(todo.getIsDelete())
            .build())
        .collect(Collectors.toList());
  }

  @Override
  public TodoDTO findById(Long id) {
    Todo todo = todoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("할 일을 찾을 수 없습니다."));
    return TodoDTO.builder()
        .id(todo.getId())
        .title(todo.getTitle())
        .content(todo.getContent())
        .startDate(todo.getStartDate())
        .build();
  }

  @Override
  public TodoDTO save(TodoDTO dto) {
    User user = userRepository.findById(dto.getUserId())
            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

    // null 방어 로직
    Integer isDelete = (dto.getIsDelete() != null) ? dto.getIsDelete() : 0;
    Integer completed = (dto.getCompleted() != null) ? dto.getCompleted() : 0;

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
            .startDate(saved.getStartDate())
            .userId(saved.getUser().getId())  // userId도 넘기기
            .build();
  }

  @Override
  public TodoDTO update(Long id, TodoDTO dto) {
    Todo todo = todoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("할 일을 찾을 수 없습니다."));

    // 값 업데이트
    todo.setTitle(dto.getTitle());
    todo.setContent(dto.getContent());
    todo.setStartDate(dto.getStartDate());
    todo.setEndDate(dto.getEndDate());
    todo.setPriority(dto.getPriority());
    todo.setCompleted(dto.getCompleted() != null ? dto.getCompleted() : 0);
    todo.setIsDelete(dto.getIsDelete() != null ? dto.getIsDelete() : 0);

    Todo updated = todoRepository.save(todo);

    return TodoDTO.builder()
        .id(updated.getId())
        .title(updated.getTitle())
        .content(updated.getContent())
        .userId(updated.getUser().getId())
        .startDate(updated.getStartDate())
        .endDate(updated.getEndDate())
        .priority(updated.getPriority())
        .completed(updated.getCompleted())
        .isDelete(updated.getIsDelete())
        .build();
  }


  // 소프트삭제 is_delete = 1
  @Override
  public void softdelete(Long id) {
    Todo todo = todoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("할 일을 찾을 수 없습니다."));
    todo.setIsDelete(1); // 1로 설정 → 소프트 삭제
    todoRepository.save(todo);
  }

  // 삭제 복원 is_delete = 0
  @Override
  public void restore(Long id) {
    Todo todo = todoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("할 일을 찾을 수 없습니다."));
    todo.setIsDelete(0);  // 복원
    todoRepository.save(todo);
  }

  // 데이터 완전 삭제
  @Override
  public void delete(Long id) {
    Todo todo = todoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("할 일을 찾을 수 없습니다."));
    todoRepository.delete(todo);  // 완전 삭제
  }

}
