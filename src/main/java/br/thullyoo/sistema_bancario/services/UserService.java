package br.thullyoo.sistema_bancario.services;

import br.thullyoo.sistema_bancario.model.user.User;
import br.thullyoo.sistema_bancario.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void incrementBalance(BigDecimal value, String document){
        User user = userRepository.getUserByDocument(document);
        System.out.println(user.toString());
        user.setBalance(user.getBalance().add(value));
        userRepository.updateUser(user);
    }

}
