package com.xaliocode.bookstracker.auth;

import com.xaliocode.bookstracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationService authenticationService;
	private final UserRepository userRepository;

	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
		if (userRepository.findByEmail(request.getEmail())
		                  .isPresent()) {
			return ResponseEntity
					.badRequest()
					.body(AuthenticationResponse.builder()
					                            .message("Email already in use. Please choose a different email.")
					                            .build());
		}
		return ResponseEntity.ok(authenticationService.register(request));
	}

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody RegisterRequest request) {
		return ResponseEntity.ok(authenticationService.login(request));
	}
}
