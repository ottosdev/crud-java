package br.com.otto.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class ResponseUserDTO {

	private UUID id;
	private String firstName;
	private String lastName; // sobrenome
	private String email;
	private Integer age;
}
