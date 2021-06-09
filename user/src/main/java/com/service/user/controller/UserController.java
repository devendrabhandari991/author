package com.service.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.user.IService.UserService;
import com.service.user.dto.UserDTO;
import com.service.user.misc.Utility;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		return new ResponseEntity<List<UserDTO>>(userService.getAllUsers(),HttpStatus.OK);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDTO> getUser(@PathVariable("userId") Long userId) {
		return new ResponseEntity<UserDTO>(userService.getUserById(userId),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<String> saveUser(@RequestBody UserDTO userDTO) {
		if (!Utility.validateRequest(userDTO)) {
			return new ResponseEntity<String>("Please ensure all inputs are correct !!", HttpStatus.BAD_REQUEST);
		}
		if (userService.saveUser(userDTO)) {
			return new ResponseEntity<String>("User Id :"+ userDTO.getUserId() + " ,user saved sucessfully !!", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Error occurered, Please try after sometime", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId){
		if(userService.deleteUser(userId)) {
			return new ResponseEntity<String>("User record deleted successfully !! ",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Issue while deleting record Please try aftersome time !! ",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping
	public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO){
		if(StringUtils.isEmpty(userDTO.getUserId())) {
			return new ResponseEntity<String>("User Id mandatory, Please provide !!", HttpStatus.BAD_REQUEST); 
		}
		if(userService.updateUser(userDTO)) {
			return new ResponseEntity<String>("User record updated successfully !! ",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Issue while updating record Please try aftersome time !! ",HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
