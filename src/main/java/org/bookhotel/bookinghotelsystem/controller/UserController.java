package org.bookhotel.bookinghotelsystem.controller;

import jakarta.validation.Valid;
import org.bookhotel.bookinghotelsystem.dto.AdminUserRequestDTO;
import org.bookhotel.bookinghotelsystem.entity.User;
import org.bookhotel.bookinghotelsystem.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    private List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    private User getUserById(@Valid @PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN') OR authentication.name==#username")
    private User getUserByUsername(@Valid @PathVariable String username) {
        return userService.getByUsername(username);
    }

    @GetMapping("/by-email/{email:.+}")
    @PreAuthorize("hasRole('ADMIN') OR @userServiceImpl.AreTheseForSameUser(authentication.name,#Email)")
    private User getUserByEmail(@Valid @PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    private User AddUSerByAdmin(AdminUserRequestDTO user){
        return userService.addUser(user);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN') OR authentication.name==#username")
    public void deleteUserByName(String username){
        userService.deleteUserByName(username);
    }

    

}
