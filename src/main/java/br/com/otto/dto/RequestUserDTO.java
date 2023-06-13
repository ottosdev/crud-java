package br.com.otto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestUserDTO {
	
	@NotNull(message = "Primeiro nome precisa ser informado")
	private String firstName;
	
	@NotNull(message = "Sobrenome precisa ser informado")
	private String lastName;
	
	@NotNull(message = "Idade precisa ser informada")
	private Integer age;

	@NotBlank(message = "Email deve ser preenchido")
	private String email;
}

