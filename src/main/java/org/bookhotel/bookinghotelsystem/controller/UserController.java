package org.bookhotel.bookinghotelsystem.controller;

import jakarta.validation.Valid;
import org.bookhotel.bookinghotelsystem.dto.AdminUserRequestDTO;
import org.bookhotel.bookinghotelsystem.dto.UserRequestDTO;
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

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    private List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/admin/{id}")
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
    @PreAuthorize("hasRole('ADMIN') OR @userServiceImpl.AreTheseForSameUser(authentication.name,#email)")
    private User getUserByEmail(@Valid @PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    private User AddUSerByAdmin(@Valid @RequestBody AdminUserRequestDTO user) {
        return userService.addUser(user);
    }

    @DeleteMapping("/{userName}")
    @PreAuthorize("hasRole('ADMIN') OR authentication.name==#username")
    public void deleteUserByName(@Valid @PathVariable String username) {
        userService.deleteUserByName(username);
    }

    @PutMapping("/{username}")
    @PreAuthorize("authentication.name==#username")
    public UserRequestDTO updateUserWithUsername(@Valid @PathVariable String username, @Valid @RequestBody UserRequestDTO userDTO) {
        return userService.updateUserWithUsername(username, userDTO);
    }

    @PutMapping("/by-email/{email:.+}")
    @PreAuthorize("@userServiceImpl.AreTheseForSameUser(authentication.name,#email)")
    public UserRequestDTO updateUserWithEmail(@Valid @PathVariable String email, @Valid @RequestBody UserRequestDTO userDTO) {
        return userService.updateUserWithEmail(email, userDTO);
    }

    @PutMapping("/admin/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public User updateUserWithUsernameByAdmin(@Valid @PathVariable String username, @Valid @RequestBody AdminUserRequestDTO userDTO) {
        return userService.updateUserWithUsernameByAdmin(username, userDTO);
    }

}
