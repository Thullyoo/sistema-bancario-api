package br.thullyoo.sistema_bancario.config.security;

import br.thullyoo.sistema_bancario.model.user.LoginRequest;
import br.thullyoo.sistema_bancario.model.user.LoginResponse;
import br.thullyoo.sistema_bancario.model.user.User;
import br.thullyoo.sistema_bancario.repository.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String getUserFromToken(){
        JwtAuthenticationToken jwtAuthenticationToken =
                (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        if (jwtAuthenticationToken == null) {
            throw new IllegalStateException("User is not authenticated.");
        }

        Jwt jwt = jwtAuthenticationToken.getToken();
        String subject = jwt.getClaim("sub");

        Pattern pattern = Pattern.compile("uuid=([a-f0-9\\-]+)");
        Matcher matcher = pattern.matcher(subject);

        if (matcher.find()){
            return matcher.group(1);
        }

        throw new IllegalStateException("Id not found");
    }
}
