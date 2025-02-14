package br.thullyoo.sistema_bancario.services;

import br.thullyoo.sistema_bancario.model.user.LoginRequest;
import br.thullyoo.sistema_bancario.model.user.LoginResponse;
import br.thullyoo.sistema_bancario.model.user.User;
import br.thullyoo.sistema_bancario.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JWTService {

    @Autowired
    private JwtEncoder jwtEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public LoginResponse getToken(LoginRequest loginRequest){

        User user = userRepository.getUserByDocument(loginRequest.document());

        if (user == null){
            throw new BadCredentialsException("User not registered");
        }

        if (!user.isPasswordCorrect(loginRequest.password(), passwordEncoder )){
            throw new BadCredentialsException("Document or password incorrect");
        }

        Instant now = Instant.now();
        long expiresAt = 400L;

        var scopes = "";

        var claims = JwtClaimsSet.builder()
                .issuer("JwtService")
                .subject(user.toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresAt))
                .build();

        var jwtValue =  jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(jwtValue, expiresAt);
    }

}
