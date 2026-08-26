package org.bookhotel.bookinghotelsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.bookhotel.bookinghotelsystem.dto.AdminUserRequestDTO;
import org.bookhotel.bookinghotelsystem.dto.UserRequestDTO;
import org.bookhotel.bookinghotelsystem.entity.User;
import org.bookhotel.bookinghotelsystem.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="User")
@RestController
@RequestMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "getAll")
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "getById")
    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getUserById(@Valid @PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "getByUsername")
    @GetMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN') OR authentication.name==#username")
    public User getUserByUsername(@Valid @PathVariable String username) {
        return userService.getByUsername(username);
    }

    @Operation(summary = "getByEmail")
    @GetMapping("/by-email/{email:.+}")
    @PreAuthorize("hasRole('ADMIN') OR @userServiceImpl.AreTheseForSameUser(authentication.name,#email)")
    public User getUserByEmail(@Valid @PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @Operation(summary = "addByAdmin")
    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public User AddUSerByAdmin(@Valid @RequestBody AdminUserRequestDTO user) {
        return userService.addUser(user);
    }

    @Operation(summary = "deleteByName")
    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN') OR authentication.name==#username")
    public void deleteUserByName(@Valid @PathVariable String username) {
        userService.deleteUserByName(username);
    }

    @Operation(summary = "updateWithUsername")
    @PutMapping("/{username}")
    @PreAuthorize("authentication.name==#username")
    public UserRequestDTO updateUserWithUsername(@Valid @PathVariable String username, @Valid @RequestBody UserRequestDTO userDTO) {
        return userService.updateUserWithUsername(username, userDTO);
    }

    @Operation(summary = "updateWithEmail")
    @PutMapping("/by-email/{email:.+}")
    @PreAuthorize("@userServiceImpl.AreTheseForSameUser(authentication.name,#email)")
    public UserRequestDTO updateUserWithEmail(@Valid @PathVariable String email, @Valid @RequestBody UserRequestDTO userDTO) {
        return userService.updateUserWithEmail(email, userDTO);
    }

    @Operation(summary = "UpdateWithUserNameByAdmin")
    @PutMapping("/admin/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public User updateUserWithUsernameByAdmin(@Valid @PathVariable String username, @Valid @RequestBody AdminUserRequestDTO userDTO) {
        return userService.updateUserWithUsernameByAdmin(username, userDTO);
    }

}
