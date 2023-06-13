package br.com.otto.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.otto.dto.RequestDownloadDTO;
import br.com.otto.entity.Product;
import br.com.otto.entity.User;
import br.com.otto.service.ProductService;
import br.com.otto.service.UserService;
import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/product/download")
public class DownloadProduct {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping()
	public ResponseEntity downloadExcel(@RequestParam("email") String email) throws IOException {
		

		Optional<User> userOptional = userService.findByUserEmail(email);
		if(!userOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NOT_FOUND: There's no user");
		}
		
		List<Product> products = productService.findByUserProducts(userOptional.get());

		Workbook workbook = productService.createExcel(userOptional.get(), products);;
	
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);

		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+userOptional.get().getFirstName()+ "_produtos" + ".xlsx");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(new InputStreamResource(inputStream));

	}

}
