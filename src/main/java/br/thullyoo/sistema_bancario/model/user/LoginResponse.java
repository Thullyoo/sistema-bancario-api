package br.thullyoo.sistema_bancario.model.user;

public record LoginResponse(String token, Long expiresAt) {
}
