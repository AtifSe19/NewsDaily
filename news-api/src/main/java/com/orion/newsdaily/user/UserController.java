package com.orion.newsdaily.user;


import com.orion.newsdaily.basic.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> findAll()
    {
        return ResponseEntity.ok(ApiResponse.of(userService.findAll()));
    }
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
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<User>>> findAllByName(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "100") int size,
            @RequestParam(name = "title", defaultValue = "") String title) {
        List<User> news = userService.findAllByName(page, size, "%"+title+"%");
        if (news.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ApiResponse.of(news));
    }
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','EDITOR')")
    public ResponseEntity<ApiResponse<User>> create(@RequestBody User accToInsert)
    {
        User createdAcc = userService.create(accToInsert);
        if(createdAcc != null)
        {
            return ResponseEntity.ok(ApiResponse.of(createdAcc));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','EDITOR')")
    public ResponseEntity<ApiResponse<User>> update(@RequestBody User updatedAccount, @PathVariable("id") Long id)
    {
        User updated = userService.update(updatedAccount,id);   //to save on db as well

        if (updated != null) {
            return ResponseEntity.ok(ApiResponse.of(updated));
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','EDITOR')")
    public ResponseEntity<ApiResponse<List<User>>> delete(@PathVariable("userId") Long id)
    {
        userService.delete(id);
        List<User> list = userService.findAll();
        return ResponseEntity.ok(ApiResponse.of(list));
    }
    @GetMapping("/getRole")
    public String getRole(Authentication auth){
        return userService.getRoleByUsername(auth.getName());
    }
    @GetMapping("/getUsername")
    public String getUsername(Authentication auth){
        return auth.getName();
    }
}
