package com.xaliocode.bookstracker.dto;

import com.xaliocode.bookstracker.entity.Book;
import com.xaliocode.bookstracker.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO {
	private int id;
	private String title;
	private String author;
	private String userName;
	private String description;
	private Category category;
	private boolean completed;
	private Date startedAt;
	private int currentPage;

	public static List<BookDTO> updatedBook(List<Book> books) {

		List<BookDTO> allBooks = new ArrayList<>();
		for (Book book : books) {
			BookDTO updateBook = BookDTO.builder()
			                            .id(book.getId())
			                            .title(book.getTitle())
			                            .author(book.getAuthor())
			                            .userName(book.getUser()
			                                          .getUserName())
			                            .description(book.getDescription())
			                            .category(book.getCategory())
			                            .completed(book.isCompleted())
			                            .startedAt(book.getStartedAt())
			                            .currentPage(book.getCurrentPage())
			                            .build();
			allBooks.add(updateBook);
		}
		return allBooks;
	}
}
