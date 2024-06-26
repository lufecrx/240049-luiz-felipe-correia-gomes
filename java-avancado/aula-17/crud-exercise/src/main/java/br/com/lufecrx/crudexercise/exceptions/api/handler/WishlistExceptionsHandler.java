package br.com.lufecrx.crudexercise.exceptions.api.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.lufecrx.crudexercise.exceptions.api.domain.product.ProductsEmptyException;
import br.com.lufecrx.crudexercise.exceptions.api.domain.wishlist.WishlistAlreadyExistsException;
import br.com.lufecrx.crudexercise.exceptions.api.domain.wishlist.WishlistNotFoundException;
import br.com.lufecrx.crudexercise.exceptions.global.message.RestErrorMessage;

@ControllerAdvice
public class WishlistExceptionsHandler {

    @ExceptionHandler(WishlistNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestErrorMessage> handleWishlistNotFoundException(WishlistNotFoundException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(ProductsEmptyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<RestErrorMessage> handleProductsEmptyException(ProductsEmptyException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(WishlistAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<RestErrorMessage> handleWishlistAlreadyExistsException(WishlistAlreadyExistsException ex) {
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }
}
