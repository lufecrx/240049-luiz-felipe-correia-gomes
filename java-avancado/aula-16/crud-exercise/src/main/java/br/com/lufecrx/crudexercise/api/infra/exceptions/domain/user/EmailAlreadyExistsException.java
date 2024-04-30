package br.com.lufecrx.crudexercise.api.infra.exceptions.domain.user;

import java.util.Locale;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailAlreadyExistsException extends RuntimeException {

    private final static ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    public EmailAlreadyExistsException(String email) {
        super(bundle.getString("user.already_exists").replace("{email}", email));
        log.error("User with email {} already exists", email);
    }
}
