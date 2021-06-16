package com.service.user.IService;

import java.util.List;

import com.service.user.dto.UserDTO;

public interface UserService {

  List<UserDTO> getAllUsers();

  UserDTO getUserById(Long userId);

  boolean saveUser(UserDTO userDTO);

  boolean deleteUser(Long userId);

  boolean updateUser(UserDTO userDTO);

}
