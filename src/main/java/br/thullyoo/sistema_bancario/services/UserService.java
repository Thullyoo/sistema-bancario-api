package br.thullyoo.sistema_bancario.services;

import br.thullyoo.sistema_bancario.config.security.JWTService;
import br.thullyoo.sistema_bancario.model.user.LoginRequest;
import br.thullyoo.sistema_bancario.model.user.LoginResponse;
import br.thullyoo.sistema_bancario.model.user.User;
import br.thullyoo.sistema_bancario.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final JWTService jwtService;

    public UserService(UserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Transactional
    public void incrementBalance(BigDecimal value){

        String uuid = JWTService.getUserFromToken();

        User user = userRepository.getUserById(UUID.fromString(uuid));
        user.setBalance(user.getBalance().add(value));
        userRepository.updateUser(user);
    }

    public LoginResponse login(LoginRequest loginRequest){
        return jwtService.getToken(loginRequest);
    }

}
