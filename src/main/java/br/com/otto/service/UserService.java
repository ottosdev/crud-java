package br.com.otto.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.otto.entity.User;
import br.com.otto.respotiory.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public Optional<User> findUserbyId(UUID user_id) {
		return repository.findById(user_id);
	}

	@Transactional
	public Object save( User user) {
		return repository.save(user);
	}


	public Optional<User> findByUserEmail(String email) {
		return repository.findByEmail(email);
	}

	public boolean existsByEmail(String email) {
		return repository.existsByEmail(email);
	}

	
	
}
