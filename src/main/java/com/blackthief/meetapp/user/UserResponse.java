package com.blackthief.meetapp.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@Builder
@AllArgsConstructor
public class UserResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String name;

}
