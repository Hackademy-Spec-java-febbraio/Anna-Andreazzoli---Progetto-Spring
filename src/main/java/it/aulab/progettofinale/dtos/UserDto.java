package it.aulab.progettofinale.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto { //utilizzeremo questa classe come struttura per i dati che riceveremo da form di register e login
    private Long id;
    @NotEmpty //affinché non venga mai memorizzato un campo vuoto all'interno del database.
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty(message = "Email should not be empty") // messaggio in caso di validazione non rispettata
    @Email //verifica che venga inserita una mail valida
    private String email;
    @NotEmpty(message = "Password should not be empty")
    private String password;
}
