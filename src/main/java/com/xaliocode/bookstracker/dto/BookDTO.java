package com.xaliocode.bookstracker.dto;

import com.xaliocode.bookstracker.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO {
	private int id;
	private String title;
	private String author;
	private Category category;
	private boolean completed;
	private Date startedAt;
	private int currentPage;
}
