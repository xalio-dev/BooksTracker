package com.xaliocode.bookstracker.controller;


import com.xaliocode.bookstracker.dto.BookDTO;
import com.xaliocode.bookstracker.entity.Book;
import com.xaliocode.bookstracker.entity.Category;
import com.xaliocode.bookstracker.entity.User;
import com.xaliocode.bookstracker.repository.BookRepository;
import com.xaliocode.bookstracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user/books")
@RequiredArgsConstructor
public class BookController {

	private final BookRepository bookRepository;
	private final UserRepository userRepository;

	//Get User Books
	@GetMapping("/get")
	public ResponseEntity<?> userBooks(Authentication authentication) {
		try {
			String email = authentication.getName();
			User user = userRepository.findByEmail(email)
			                          .get();

			List<Book> books = bookRepository.findBookByUser(user);
			if (books.isEmpty()) {
				return ResponseEntity.badRequest()
				                     .body("You don't have any book yet");
			}
			List<BookDTO> allBooks = BookDTO.updatedBook(books);
			return ResponseEntity.ok(allBooks);

		} catch (Exception e) {
			return ResponseEntity.badRequest()
			                     .body("An error occurred while fetching books.");
		}
	}


	//Get books for the user by category
	@GetMapping("/getbycategory")
	public ResponseEntity<?> getBooksByCategory(Authentication authentication, @RequestParam(name = "name", required = true) String cat) {
		Category category = Category.valueOf(cat.toUpperCase());
		String email = authentication.getName();
		User user = userRepository.findByEmail(email)
		                          .get();
		try {
			List<Book> books = bookRepository.findBookByCategoryAndUser(category, user);
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

	//Post new book
	@PostMapping("/create")
	public ResponseEntity<?> postNewBook(Authentication authentication, @RequestBody Book book) {
		try {
			//Get the user
			String email = authentication.getName();
			User user = userRepository.findByEmail(email)
			                          .get();

			//Create Book Object
			Book userBook = Book.builder()
			                    .title(book.getTitle())
			                    .description(book.getDescription())
			                    .user(user)
			                    .author(book.getAuthor())
			                    .startedAt(book.getStartedAt())
			                    .currentPage(book.getCurrentPage())
			                    .completed(book.isCompleted())
			                    .category(Category.valueOf(book.getCategory()
			                                                   .name()
			                                                   .toUpperCase()))
			                    .currentPage(book.getCurrentPage())
			                    .build();

			//Save the book
			bookRepository.save(userBook);

			return ResponseEntity.status(HttpStatus.CREATED)
			                     .body("The book was created successfully.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest()
			                     .body("An error occurred while posting the book.");
		}
	}

	//Update a book
	@PutMapping("/update/{id}")
	public ResponseEntity<?> updateBook(Authentication authentication, @PathVariable int id, @RequestBody Book book) {
		//Get the user
		String email = authentication.getName();
		User user = userRepository.findByEmail(email)
		                          .get();
		try {

			Optional<Book> getBook = bookRepository.findBookById(id);
			Book newBook = null;

			if (getBook.isPresent()) {
				newBook = getBook.get();
			}

			if (!user.equals(newBook.getUser())) {
				return ResponseEntity.badRequest()
				                     .body("You don't own that book");
			}

			newBook.setTitle(book.getTitle());
			newBook.setAuthor(book.getAuthor());
			newBook.setDescription(book.getDescription());
			newBook.setCategory(book.getCategory());
			newBook.setCompleted(book.isCompleted());
			newBook.setCurrentPage(book.getCurrentPage());
			newBook.setStartedAt(book.getStartedAt());

			bookRepository.save(newBook);

			return ResponseEntity.ok("The book was created successfully.");
		} catch (Exception e) {
			return ResponseEntity.badRequest()
			                     .body("An error occurred while updating the book.");
		}
	}

	//Delete a book
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteBook(Authentication authentication, @PathVariable int id) {
		String email = authentication.getName();
		User user = userRepository.findByEmail(email)
		                          .get();
		Book book = bookRepository.findBookById(id)
		                          .get();
		try {
			if (!user.equals(book.getUser())) {
				return ResponseEntity.badRequest()
				                     .body("You don't own that book");
			}

			book.setUser(null);

			bookRepository.deleteById(id);
			return ResponseEntity.ok("Your book deleted successfully !!");
		} catch (Exception e) {
			return ResponseEntity.badRequest()
			                     .body("An error occurred while deleting the book.");
		}
	}
}
