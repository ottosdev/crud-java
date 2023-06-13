package br.com.otto.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.otto.dto.RequestProductDTO;
import br.com.otto.entity.Product;
import br.com.otto.entity.User;
import br.com.otto.service.ProductService;
import br.com.otto.service.UserService;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private ProductService service;

	@Autowired
	private UserService userService;

	
	@GetMapping("/{email}")
	public ResponseEntity<Object> getProductUser(@PathVariable(value = "email") String email) {
		Optional<User> user = userService.findByUserEmail(email);
		
		if (!user.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT-FOUND: There's no product");
		} 
		
		List<Product> products = service.findByUserProducts(user.get());
		return ResponseEntity.status(HttpStatus.OK).body(products);

	}
	

	@PostMapping("{user_id}")
	public ResponseEntity<Object> registerProduct(@PathVariable(value = "user_id") UUID user_id, @RequestBody @Valid RequestProductDTO dto) {
		Optional<User> userOptional = userService.findUserbyId(user_id);
		
		if(!userOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT_FOUND: There's no user");
		}
		
		
		Product newProduct = new Product();
		BeanUtils.copyProperties(dto, newProduct);
		
		newProduct.setUser(userOptional.get()); 
		newProduct.setCreated_at(LocalDateTime.now(ZoneId.of("UTC")));

		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(newProduct));
	}

	@DeleteMapping("/{product_id}")
	public ResponseEntity<String> deleteProduct(@PathVariable(value = "product_id") UUID product_id) {
		Optional<Product> product = service.findById(product_id);
		if (!product.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT-FOUND: There's no product");
		}

		service.deleteProduct(product.get());

		return ResponseEntity.status(HttpStatus.OK).body("Product deleted");

	}

	@PutMapping("/{product_id}")
	public ResponseEntity<Object> updateProdut(@PathVariable(value = "product_id") UUID product_id,
			@RequestBody @Valid RequestProductDTO dto) {
		Optional<Product> productOptional = service.findById(product_id);
		
		if (!productOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT-FOUND: There's no product");
		}
		
		Product updateProduct = new Product();
		BeanUtils.copyProperties(dto, updateProduct); 

		updateProduct.setId(productOptional.get().getId());
		updateProduct.setCreated_at(productOptional.get().getCreated_at());
		updateProduct.setUser(productOptional.get().getUser());
	
		return ResponseEntity.status(HttpStatus.OK).body(service.save(updateProduct));

	}
	
}
