package com.blackthief.meetapp.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
@AllArgsConstructor
public class AuthorizationRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("user")
	@NotNull
	@Size(min=8, message="UserName should have atleast 8 characters")
	private String userName;

	@NotNull
	@Size(min=6, message="UserName should have atleast 6 characters")
	private String password;

	public AuthorizationRequest() {
	}
}