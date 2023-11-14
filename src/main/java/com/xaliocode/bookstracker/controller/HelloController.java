package com.xaliocode.bookstracker.controller;


import com.xaliocode.bookstracker.dto.BookDTO;
import com.xaliocode.bookstracker.entity.Book;
import com.xaliocode.bookstracker.entity.Category;
import com.xaliocode.bookstracker.entity.User;
import com.xaliocode.bookstracker.repository.BookRepository;
import com.xaliocode.bookstracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/hello")
@RequiredArgsConstructor
public class HelloController {

	private final BookRepository bookRepository;
	private final UserRepository userRepository;

	@GetMapping
	public ResponseEntity<String> helloController() {
		return ResponseEntity.ok("Hey from the secured Controller");
	}

	@GetMapping("/categories")
	public ResponseEntity<List<String>> getCategory() {
		return ResponseEntity.ok(Category.categoryList());
	}

	@GetMapping("/book")
	public ResponseEntity<List<BookDTO>> getBook(Authentication authentication) {
		String email = authentication.getName();
		int id = 1;
		//		List<Book> books = bookRepository.findAll();
		List<Book> books = bookRepository.findBookByUser_Email(email);

		List<BookDTO> updatedBooks = new ArrayList<>();

		for (Book book : books) {
			BookDTO updateBook = BookDTO.builder()
			                            .id(id)
			                            .title(book.getTitle())
			                            .author(book.getAuthor())
			                            .category(book.getCategory())
			                            .completed(book.isCompleted())
			                            .currentPage(book.getCurrentPage())
			                            .startedAt(book.getStartedAt())
			                            .build();
			updatedBooks.add(updateBook);
			id++;
		}
		return ResponseEntity.ok(updatedBooks);
	}

	@PostMapping("/book")
	public ResponseEntity<Object> postBook(Authentication authentication, @RequestBody Book book) {

		String email = authentication.getName();
		User user = userRepository.findByEmail(email)
		                          .get();

		Book newBook = Book.builder()
		                   .title(book.getTitle())
		                   .author(book.getAuthor())
		                   .completed(book.isCompleted())
		                   .currentPage(book.getCurrentPage())
		                   .startedAt(new Date())
		                   .category(book.getCategory())
		                   .user(user)
		                   .build();

		return ResponseEntity.ok(bookRepository.save(newBook));
	}
}
