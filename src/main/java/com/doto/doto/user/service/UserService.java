package com.doto.doto.user.service;

import com.doto.doto.user.dto.UserDTO;

import java.util.List;

public interface UserService {
  UserDTO register(UserDTO userDto);

  List<UserDTO> findAll();

  UserDTO findById(Long id);
}
