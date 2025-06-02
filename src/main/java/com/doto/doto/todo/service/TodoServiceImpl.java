package com.doto.doto.todo.service;

import com.doto.doto.todo.dto.TodoDTO;
import com.doto.doto.todo.entity.Todo;
import com.doto.doto.todo.repository.TodoRepository;
import com.doto.doto.user.entity.User;
import com.doto.doto.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        .filter(todo -> todo.getIsDelete() == null || todo.getIsDelete() != 1) // null 방어
        .map(todo -> TodoDTO.builder()
            .id(todo.getId())
            .title(todo.getTitle())
            .content(todo.getContent())
            .priority(todo.getPriority())
            .completed(todo.getCompleted())
            .startDate(todo.getStartDate())
            .endDate(todo.getEndDate())
            .userId(todo.getUser() != null ? todo.getUser().getId() : null)
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
        .orElseThrow(() -> new IllegalArgumentException("해당 ID의 할 일이 존재하지 않습니다."));

    // 삭제된 항목이면 예외 처리하거나 무시할 수 있음
    if (todo.getIsDelete() != null && todo.getIsDelete() == 1) {
      throw new IllegalStateException("삭제된 할 일입니다.");
    }

    return TodoDTO.builder()
        .id(todo.getId())
        .title(todo.getTitle())
        .content(todo.getContent())
        .priority(todo.getPriority())
        .completed(todo.getCompleted())
        .startDate(todo.getStartDate())
        .endDate(todo.getEndDate())
        .userId(todo.getUser() != null ? todo.getUser().getId() : null)
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
            .completed(completed)
            .isDelete(isDelete)
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
    todo.setCompleted(dto.getCompleted());
    todo.setIsDelete(dto.getIsDelete());

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

  @Transactional
  @Override
  public void deleteAllInTrash() {
    List<Todo> deletedTodos = todoRepository.findByIsDelete(1); // isDelete가 1인 것들만 조회
    todoRepository.deleteAllByIsDelete(1);
  }


}
