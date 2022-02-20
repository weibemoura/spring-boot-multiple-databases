package br.com.weibe.spring.boot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.com.weibe.spring.boot.product.entity.Product;
import br.com.weibe.spring.boot.product.repository.ProductRepository;
import br.com.weibe.spring.boot.user.entity.User;
import br.com.weibe.spring.boot.user.repository.UserRepository;

@SpringBootTest
@EnableTransactionManagement
public class JpaMultipleDBIntegrationTest {

	private @Autowired UserRepository userRepository;
	private @Autowired ProductRepository productRepository;
	
	@Test
	void whenCreateUser_ThenCreated() {
		User user = new User();
		user.setEmail("user1@simple.test");
		user.setPassword("123456");
		
		User createdUser = userRepository.save(user);
		System.out.println(createdUser);
		
		assertTrue(createdUser.getId() > 0);
		assertEquals(user.getEmail(), createdUser.getEmail());
	}
	
	@Test
	void whenCreateProduct_ThenCreated() {
		Product product = new Product();
		product.setName("Product A");
		
		Product createdProduct = productRepository.save(product);
		System.out.println(createdProduct);
		
		assertTrue(createdProduct.getId() > 0);
		assertEquals(product.getName(), createdProduct.getName());
	}
}
