package com.xaliocode.bookstracker.repository;

import com.xaliocode.bookstracker.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
	List<Book> findBookByUser_Email(String email);
}
