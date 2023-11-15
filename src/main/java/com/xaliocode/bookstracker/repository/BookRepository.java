package com.xaliocode.bookstracker.repository;

import com.xaliocode.bookstracker.entity.Book;
import com.xaliocode.bookstracker.entity.Category;
import com.xaliocode.bookstracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
	List<Book> findBookByUser(User user);

	List<Book> findBookByCategory(Category category);

	Optional<Book> findBookById(int id);

	List<Book> findBookByCategoryAndUser(Category category, User user);
}
