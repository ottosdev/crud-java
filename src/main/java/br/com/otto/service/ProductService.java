package br.com.otto.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.otto.entity.Product;
import br.com.otto.entity.User;
import br.com.otto.respotiory.ProductRepository;
import br.com.otto.respotiory.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;


	@Transactional
	public Product save(Product newProduct) {
		return repository.save(newProduct);
	}

	public boolean existsByName(String name) {
		return repository.existsByName(name);
	}

	public List<Product> findByUserProducts(User user) {
		return repository.findByUser(user);
	}

	public Optional<Product> findById(UUID id) {
		return repository.findById(id);
	}

	@Transactional
	public void deleteProduct(Product product) {
		repository.delete(product);

	}

	public Workbook createExcel(User user, List<Product> products) {

		Workbook workbook = new XSSFWorkbook();
	
		Sheet sheet = workbook.createSheet("Planilha de " + user.getFirstName() );

		Row headerRow = sheet.createRow(0);
		Cell headerCell1 = headerRow.createCell(0);
		headerCell1.setCellValue("Produto");

		Cell headerCell2 = headerRow.createCell(1);
		headerCell2.setCellValue("Preço");
		
		int count = 0;
		
		for (Product product : products) {
			Row dataRow1 = sheet.createRow(count + 1);
			Cell dataCell1 = dataRow1.createCell(0);
			dataCell1.setCellValue(product.getName());

			Cell dataCell2 = dataRow1.createCell(1);
			dataCell2.setCellValue(product.getPrice());
			count++;
		}

        return workbook;
	}

}
