package com.xaliocode.bookstracker.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum Category {
	FICTION,
	NON_FICTION,
	MYSTERY_THRILLER,
	SCIENCE_FICTION,
	FANTASY,
	ROMANCE,
	HISTORICAL_FICTION,
	BIOGRAPHY_MEMOIR,
	SELF_HELP,
	HISTORY,
	SCIENCE,
	TECHNOLOGY,
	TRAVEL,
	HEALTH_WELLNESS,
	BUSINESS_ECONOMICS,
	CHILDRENS_BOOKS,
	POETRY,
	GRAPHIC_NOVELS_COMICS,
	RELIGION_SPIRITUALITY,
	ART_PHOTOGRAPHY,
	COOKBOOKS,
	EDUCATION_REFERENCE;

	public static List<String> categoryList() {
		return Arrays.stream(Category.values())
		             .map(category -> category.name())
		             .collect(Collectors.toList());
	}

}
