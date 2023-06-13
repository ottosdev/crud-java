package br.com.otto.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.otto.dto.RequestUserDTO;
import br.com.otto.dto.ResponseUserDTO;
import br.com.otto.entity.User;
import br.com.otto.service.UserService;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping()
	public ResponseEntity<Object> registerUser(@RequestBody @Valid RequestUserDTO userDTO) {
		
		if(userService.existsByEmail(userDTO.getEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: There's an user with the same email, please set other email");
		}
		
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));

	}

	@GetMapping("{email}")
	public ResponseEntity<Object> getUser(@PathVariable(value = "email") String email) {
		Optional<User> userOptional = userService.findByUserEmail(email);
		
		if (!userOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT_FOUND: There's no user");
		}
		
		ResponseUserDTO response = new ResponseUserDTO();
		BeanUtils.copyProperties(userOptional.get(), response);
	
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
}
