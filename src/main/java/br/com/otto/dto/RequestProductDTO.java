package br.com.otto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestProductDTO {

	@NotBlank(message = "Nome do produto deve ser informado")
	private String name;
	@NotNull(message = "{campo.preco.obrigatorio}")
	private Integer price;
	
}
