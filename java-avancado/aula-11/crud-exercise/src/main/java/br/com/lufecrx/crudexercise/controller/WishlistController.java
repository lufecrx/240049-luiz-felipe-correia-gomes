package br.com.lufecrx.crudexercise.controller;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufecrx.crudexercise.model.Wishlist;
import br.com.lufecrx.crudexercise.services.WishlistService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {
    
    private final WishlistService wishlistService;

    private final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    @GetMapping
    public ResponseEntity<Iterable<Wishlist>> findAll() {
        Iterable<Wishlist> entities = wishlistService.getAllWishlists();
        return ResponseEntity.ok(entities);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Wishlist>> findById(@PathVariable Long id) {
        Optional<Wishlist> entity = wishlistService.getWishlistById(id);
        return ResponseEntity.ok(entity);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody @Valid Wishlist dto) {
        wishlistService.createWishlist(dto);
        return ResponseEntity.ok(bundle.getString("wishlist.successfully_created"));
    }

    @PostMapping("/add-product/{id}")
    public ResponseEntity<String> addProduct(@RequestBody @Valid Long productId, @PathVariable Long wistlistId) {
        wishlistService.addProductToWishlist(wistlistId, productId);
        return ResponseEntity.ok(bundle.getString("wishlist.successfully_added_product"));
    }

    @PutMapping ("/{id}")
    public ResponseEntity<String> update(@RequestBody @Valid Wishlist dto, @PathVariable Long id) {
        wishlistService.updateWishlist(id, dto);
        return ResponseEntity.ok(bundle.getString("wishlist.successfully_updated"));
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        wishlistService.deleteWishlist(id);
        return ResponseEntity.ok(bundle.getString("wishlist.successfully_deleted"));
    }
}
