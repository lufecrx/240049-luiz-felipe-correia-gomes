package br.com.lufecrx.crudexercise.controller.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.lufecrx.crudexercise.model.User;
import br.com.lufecrx.crudexercise.services.domain.user.UserServicePaginable;

@RestController
@RequestMapping("/paginable/users")
public class UserControllerPaginable {
    
    private UserServicePaginable userService;

    @Autowired
    public UserControllerPaginable(@Qualifier("paginable") UserServicePaginable userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Iterable<User>> findAll(
            @PathVariable int page,
            @RequestParam(value = "sort", defaultValue = "name,asc") String[] sort) {
        // Default pagination size is 10
        Iterable<User> entities = userService.getWithPagination(page, 10, sort);
        return ResponseEntity.ok(entities);
    }

    @GetMapping("/page/{page}/size=5")
    public ResponseEntity<Iterable<User>> findAllWithPaginationAndSizeFive(
            @PathVariable int page,
            @RequestParam(value = "sort", defaultValue = "name,asc") String[] sort) {
        Iterable<User> entities = userService.getWithPagination(page, 5, sort);
        return ResponseEntity.ok(entities);
    }

    @GetMapping("/page/{page}/size=10")
    public ResponseEntity<Iterable<User>> findAllWithPaginationAndSizeTen(
            @PathVariable int page,
            @RequestParam(value = "sort", defaultValue = "name,asc") String[] sort) {
        Iterable<User> entities = userService.getWithPagination(page, 10, sort);
        return ResponseEntity.ok(entities);
    }

    @GetMapping("/page/{page}/size=20")
    public ResponseEntity<Iterable<User>> findAllWithPaginationAndSizeTwenty(
            @PathVariable int page,
            @RequestParam(value = "sort", defaultValue = "name,asc") String[] sort) {
        Iterable<User> entities = userService.getWithPagination(page, 20, sort);
        return ResponseEntity.ok(entities);
    }

}
