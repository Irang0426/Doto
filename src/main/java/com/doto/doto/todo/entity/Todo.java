package com.doto.doto.todo.entity;

import com.doto.doto.todo.enums.Priority;
import com.doto.doto.todo.enums.PriorityConverter;
import com.doto.doto.todo.enums.RepeatType;
import com.doto.doto.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "todos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Todo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 연관관계 설정 (User ↔ Todos : 다대일)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private String title;

  @Column(columnDefinition = "TEXT")
  private String content;

  private LocalDate startDate;

  private LocalDate endDate;

  private LocalTime startTime;

  @Enumerated(EnumType.STRING)
  private RepeatType repeatType;

  private LocalDate repeatEndDate;

  @Convert(converter = PriorityConverter.class)
  private Priority priority;

  @Builder.Default
  private Integer completed = 0;   // 0: 미완료, 1: 완료

  @Builder.Default
  @Column(nullable = false, name = "is_delete")
  private Integer isDelete = 0;    // 0: 사용, 1: 삭제

  @CreatedDate
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  // 가본값 세팅
  @PrePersist
  public void prePersist() {
    if (isDelete == null) {
      isDelete = 0;
    }
    if (completed == null) {
      completed = 0;
    }
  }
}
