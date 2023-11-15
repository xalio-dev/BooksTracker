package com.xaliocode.bookstracker.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(cascade = CascadeType.ALL)
	private User user;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String description;

	private String author;

	@Enumerated(EnumType.STRING)
	private Category category;

	@Column(nullable = false)
	private boolean completed;

	private Date startedAt;

	private int currentPage;
}
