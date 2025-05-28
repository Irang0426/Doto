package com.doto.doto.user.dto;

import com.doto.doto.user.enums.Grade;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
  private Long id;

  private String email;

  private String password;

  private String nickname;

  private String username;

  private Grade grade;
}
