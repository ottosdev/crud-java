package br.com.otto.respotiory;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.otto.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	boolean existsByEmail(String name);

	Optional<User> findByEmail(String email);

}
