package br.thullyoo.sistema_bancario.controllers;

import br.thullyoo.sistema_bancario.model.user.*;
import br.thullyoo.sistema_bancario.repository.UserRepository;
import br.thullyoo.sistema_bancario.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@Tag(name = "Users", description = "Users activities end point")
@SecurityRequirement(name = "BearerAuth")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Operation(summary = "Get user by ID", description = "Retrieve user details by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found and returned successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized to access this resource"),
    })
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<User> getUserById(@PathVariable("id") UUID id) {
        User user = userRepository.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @Operation(summary = "Increment user balance", description = "Increment the balance of a user by a specified value")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Balance incremented successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized to access this resource"),
    })
    @PostMapping(value = "/increment", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Void> incrementBalance(@RequestBody @Valid IncrementRequest request) {
        userService.incrementBalance(request.value());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}