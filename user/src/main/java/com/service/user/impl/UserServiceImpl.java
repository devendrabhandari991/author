package com.service.user.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.service.user.IService.UserService;
import com.service.user.dto.UserDTO;
import com.service.user.entity.Users;
import com.service.user.respository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRespository;
	
	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public List<UserDTO> getAllUsers() {
		List<UserDTO> userDTOs = new ArrayList<>();
		List<Users> users = userRespository.findByDeletedFalse();
		for (Users user : users) {
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(user, userDTO);
			userDTOs.add(userDTO);
		}
		return userDTOs;
	}

	@Override
	public UserDTO getUserById(Long userId) {
		UserDTO userDTO = new UserDTO();
		Optional<Users> user = userRespository.findById(userId);
		if(user.isPresent()) {
			BeanUtils.copyProperties(user.get(), userDTO);
		}
		return userDTO;
	}

	@Override
	public boolean saveUser(UserDTO userDTO) {
		try {
			Users user = new Users();
			BeanUtils.copyProperties(userDTO, user);
			user.setCreated(new Date());
			user.setUpdated(new Date());
			user.setDeleted(false);
			Users users = userRespository.save(user);
			userDTO.setUserId(users.getUserId());
			return true;
		} catch (Exception e) {
			log.error("Error occur while saving user : {}",e.getStackTrace()[0]);
		}
		return false;
	}

	@Override
	public boolean deleteUser(Long userId) {
		try {
			Optional<Users> userOptional = userRespository.findById(userId);
			if(userOptional.isPresent()) {
				Users user = userOptional.get();
				user.setDeleted(true);
				userRespository.save(user);
				return true;
			}
		}catch(Exception e) {
			log.error("Exception occurr while deleting user info {}",e.getStackTrace()[0]);
		}
		return false;
	}

	@Override
	public boolean updateUser(UserDTO userDTO) {
		try {
			Optional<Users> user = userRespository.findById(userDTO.getUserId());
			if(user.isPresent()) {
				Users userDetails = user.get();
				if(isUpdateRequired(userDTO, userDetails)) {
					userRespository.save(userDetails);
					return true;
				}
			}
		}catch(Exception e) {
			log.error("Exception occurr while updating user info {}",e.getStackTrace()[0]);
		}
		return false;
	}

	private boolean isUpdateRequired(UserDTO userDTO, Users user) {
		boolean isUpdateFound = false;
		if (!StringUtils.isEmpty(userDTO.getFirstName())) {
			user.setFirstName(userDTO.getFirstName());
			isUpdateFound = true;
		}
		if (!StringUtils.isEmpty(userDTO.getLastName())) {
			user.setLastName(userDTO.getLastName());
			isUpdateFound = true;
		}
		if (!StringUtils.isEmpty(userDTO.getPhone())) {
			user.setPhone(userDTO.getPhone());
			isUpdateFound = true;
		}
		return isUpdateFound;
	}

}
