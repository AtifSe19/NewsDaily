package com.orion.newsdaily.user;

import com.orion.newsdaily.basic.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {


    final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PreAuthorize("permitAll()")
    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> findAll()
    {
        return ResponseEntity.ok(ApiResponse.of(userService.findAll()));
    }

//    @PreAuthorize("permitAll()")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Optional<User>>> findById(@PathVariable("id") Long id)
    {
        Optional<User> acc = userService.findById(id);

        if(acc == null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.of(acc));
    }

//    @PreAuthorize("permitAll()")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<User>>> findAllByName(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "100") int size,
            @RequestParam(name = "title", defaultValue = "") String title) {
        List<User> news = userService.findAllByName(page, size, title);
        if (news.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.of(news));
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<User>> insert(@RequestBody User accToInsert)
    {
        User createdAcc = userService.insert(accToInsert);
        if(createdAcc != null)
        {
            return ResponseEntity.ok(ApiResponse.of(createdAcc));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updates(@RequestBody User updatedAccount, @PathVariable("id") Long id)
    {
        User updated = userService.update(updatedAccount,id);   //to save on db as well

        if (updated != null) {
            return ResponseEntity.ok(ApiResponse.of(updated));
        }
        return ResponseEntity.notFound().build();
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<List<User>>> delete(@PathVariable("id") Long id)
    {
        boolean deleted = userService.delete(id);
        if(deleted)
        {
            List<User> list = userService.findAll();
            return ResponseEntity.ok(ApiResponse.of(list));
        }
        else
        {
            List<User> list = userService.findAll();
            return ResponseEntity.ok(ApiResponse.of(list));
        }
    }
}
