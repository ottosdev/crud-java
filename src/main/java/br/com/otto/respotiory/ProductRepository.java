package br.com.otto.respotiory;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.otto.entity.Product;
import br.com.otto.entity.User;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

	boolean existsByName(String name);
	
	List<Product> findByUser(User user);
}
