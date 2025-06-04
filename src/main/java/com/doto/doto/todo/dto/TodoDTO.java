package com.doto.doto.todo.dto;

import com.doto.doto.todo.enums.Priority;
import com.doto.doto.todo.enums.RepeatType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDTO {
  private Long id;

  private Long userId;

  private String title;

  private String content;

  private LocalDate startDate;

  private LocalDate endDate;

  private LocalTime startTime;

  private RepeatType repeatType;

  private LocalDate repeatEndDate;

  private Priority priority;

  private Integer completed;

  private Integer isDelete = 0;
}
