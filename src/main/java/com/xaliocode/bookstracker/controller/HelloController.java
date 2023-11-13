package com.xaliocode.bookstracker.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloController {


	@GetMapping
	public ResponseEntity<String> helloController() {
		return ResponseEntity.ok("Hey from the secured Controller");
	}
}
