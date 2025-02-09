package br.thullyoo.sistema_bancario.controllers;

import br.thullyoo.sistema_bancario.model.user.IncrementRequest;
import br.thullyoo.sistema_bancario.model.user.User;
import br.thullyoo.sistema_bancario.model.user.UserRequest;
import br.thullyoo.sistema_bancario.repository.UserRepository;
import br.thullyoo.sistema_bancario.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody @Valid UserRequest userRequest){
        try{
            userRepository.register(userRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") UUID id){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.getUserById(id));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/increment")
    public ResponseEntity<Void> incrementBalance(@RequestParam String document, @RequestBody IncrementRequest request){
        try{
            userService.incrementBalance(request.value(), document);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


}
