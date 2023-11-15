package com.xaliocode.bookstracker.controller;

import com.xaliocode.bookstracker.dto.BookDTO;
import com.xaliocode.bookstracker.entity.Book;
import com.xaliocode.bookstracker.entity.Category;
import com.xaliocode.bookstracker.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class OpenController {

	private final BookRepository bookRepository;

	@GetMapping("/")
	public ResponseEntity<?> getAllBooks() {
		try {
			// Get all books from Database
			List<Book> books = bookRepository.findAll();

			// Transfer all books to BookDTO List
			List<BookDTO> allBooks = BookDTO.updatedBook(books);

			return ResponseEntity.ok(allBooks);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			                     .body("An error occurred while fetching books.");
		}

	}

	@GetMapping("/categories")
	public ResponseEntity<List<String>> getCategory() {
		return ResponseEntity.ok(Category.categoryList());
	}

	@GetMapping("/category")
	public ResponseEntity<?> getBooksByCategory(@RequestParam(name = "name", required = true) String category) {
		try {
			List<Book> books = bookRepository.findBookByCategory(Category.valueOf(category.toUpperCase()));
			if (!books.isEmpty()) {
				List<BookDTO> allBooks = BookDTO.updatedBook(books);
				return ResponseEntity.ok(allBooks);
			}
			return ResponseEntity.badRequest()
			                     .body("No books found for the given category.");
		} catch (Exception e) {
			return ResponseEntity.badRequest()
			                     .body("Invalid category.");
		}

	}


}
