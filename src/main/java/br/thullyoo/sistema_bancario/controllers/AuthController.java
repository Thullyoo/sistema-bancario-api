package br.thullyoo.sistema_bancario.controllers;

import br.thullyoo.sistema_bancario.model.user.LoginRequest;
import br.thullyoo.sistema_bancario.model.user.LoginResponse;
import br.thullyoo.sistema_bancario.model.user.UserRequest;
import br.thullyoo.sistema_bancario.repository.UserRepository;
import br.thullyoo.sistema_bancario.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Operation(summary = "User login", description = "Authenticate a user and return a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful, JWT token returned"),
            @ApiResponse(responseCode = "400", description = "Invalid input or request"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
    })
    @PostMapping(value = "/login", produces = "application/json", consumes = "application/json")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok().body(userService.login(loginRequest));
    }

    @Operation(summary = "Register a new user", description = "Create a new user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or request"),
            @ApiResponse(responseCode = "409", description = "User already exists"),
    })
    @PostMapping(value = "/register", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid UserRequest userRequest) {
        userRepository.register(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }
}
