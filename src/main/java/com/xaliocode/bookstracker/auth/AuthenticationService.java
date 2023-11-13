package com.xaliocode.bookstracker.auth;

import com.xaliocode.bookstracker.entity.Role;
import com.xaliocode.bookstracker.entity.User;
import com.xaliocode.bookstracker.jwt.JwtService;
import com.xaliocode.bookstracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthenticationResponse register(RegisterRequest request) {
		var user = User.builder()
		               .firstName(request.getFirstName())
		               .lastName(request.getLastName())
		               .email(request.getEmail())
		               .password(passwordEncoder.encode(request.getPassword()))
		               .role(Role.USER)
		               .build();
		userRepository.save(user);

		var jwtToken = jwtService.generateToken(user);

		return AuthenticationResponse.builder()
		                             .token(jwtToken)
		                             .message("Registration successful. Welcome!")
		                             .build();
	}

	public AuthenticationResponse login(RegisterRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
		);

		var user = userRepository.findByEmail(request.getEmail())
		                         .orElseThrow();

		var jwtToken = jwtService.generateToken(user);

		return AuthenticationResponse.builder()
		                             .token(jwtToken)
		                             .message("Login successful. Welcome back!")
		                             .build();
	}
}
