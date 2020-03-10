package com.blackthief.meetapp.user;

import com.blackthief.meetapp.security.AuthorizationRequest;

public class UserMapper {

	private UserMapper() {
	}

	public static UserResponse toResponse(User user) {
		return UserResponse.builder().name(user.getName()).id(user.getId()).build();
	}

	public static User toDomain(AuthorizationRequest authorizationRequest) {
		return User.builder().name(authorizationRequest.getUserName()).password(authorizationRequest.getPassword())
				.build();
	}
}
