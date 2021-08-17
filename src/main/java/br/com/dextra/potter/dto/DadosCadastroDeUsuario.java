package br.com.dextra.potter.dto;

public interface DadosCadastroDeUsuario {

    String getEmail();
    void setEmail(String email);

    String getPassword();
    void setPassword(String password);

    String getName();
    void setName(String name);
}