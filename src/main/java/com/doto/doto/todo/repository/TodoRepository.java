package com.doto.doto.todo.repository;

import com.doto.doto.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
  List<Todo> findByIsDelete(Integer isDelete);

}
