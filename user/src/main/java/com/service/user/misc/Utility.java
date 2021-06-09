package com.service.user.misc;

import org.springframework.util.StringUtils;

import com.service.user.dto.UserDTO;

public class Utility {

	public static boolean validateRequest(UserDTO userDTO) {
		if (userDTO == null || StringUtils.isEmpty(userDTO.getEmail()) || StringUtils.isEmpty(userDTO.getFirstName())
				|| StringUtils.isEmpty(userDTO.getPhone())) {
			return false;
		}
		return true;
	}

}
