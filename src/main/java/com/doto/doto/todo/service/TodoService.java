package com.doto.doto.todo.service;

import com.doto.doto.todo.dto.TodoDTO;

import java.util.List;

public interface TodoService {
  List<TodoDTO> findAll();
  List<TodoDTO> findDeleted();
  TodoDTO findById(Long id);
  TodoDTO save(TodoDTO dto);
  TodoDTO update(Long id, TodoDTO dto);
  void softdelete(Long id);
  void restore(Long id);
  void delete(Long id);
}
