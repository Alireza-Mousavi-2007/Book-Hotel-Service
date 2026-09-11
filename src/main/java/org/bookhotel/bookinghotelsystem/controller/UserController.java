package org.bookhotel.bookinghotelsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.bookhotel.bookinghotelsystem.dto.AdminUserRequestDTO;
import org.bookhotel.bookinghotelsystem.dto.UserRequestDTO;
import org.bookhotel.bookinghotelsystem.entity.User;
import org.bookhotel.bookinghotelsystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User")
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
    public ResponseEntity<List<User>> getAllUsers() {
        var rooms = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.FOUND).body(rooms);
    }

    @Operation(summary = "getById")
    @GetMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> getUserById(@Valid @PathVariable Integer id) {
        var user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(user);
    }

    @Operation(summary = "getByUsername")
    @GetMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN') OR authentication.name==#username")
    public ResponseEntity<User> getUserByUsername(@Valid @PathVariable String username) {
        var user = userService.getByUsername(username);
        return ResponseEntity.status(HttpStatus.FOUND).body(user);
    }

    @Operation(summary = "getByEmail")
    @GetMapping("/by-email/{email:.+}")
    @PreAuthorize("hasRole('ADMIN') OR @userServiceImpl.AreTheseForSameUser(authentication.name,#email)")
    public ResponseEntity<User> getUserByEmail(@Valid @PathVariable String email) {
        var user = userService.getUserByEmail(email);
        return ResponseEntity.status(HttpStatus.FOUND).body(user);
    }

    @Operation(summary = "addByAdmin")
    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> AddUSerByAdmin(@Valid @RequestBody AdminUserRequestDTO user) {
        var addedUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedUser);
    }

    @Operation(summary = "deleteByName")
    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN') OR authentication.name==#username")
    public ResponseEntity<String> deleteUserByName(@Valid @PathVariable String username) {
        userService.deleteUserByName(username);
        return ResponseEntity.status(HttpStatus.OK).body("user " + username + " deleted");
    }

    @Operation(summary = "updateWithUsername")
    @PutMapping("/{username}")
    @PreAuthorize("authentication.name==#username")
    public ResponseEntity<UserRequestDTO> updateUserWithUsername(@Valid @PathVariable String username, @Valid @RequestBody UserRequestDTO userDTO) {
        var user = userService.updateUserWithUsername(username, userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @Operation(summary = "updateWithEmail")
    @PutMapping("/by-email/{email:.+}")
    @PreAuthorize("@userServiceImpl.AreTheseForSameUser(authentication.name,#email)")
    public ResponseEntity<UserRequestDTO> updateUserWithEmail(@Valid @PathVariable String email,
                                                              @Valid @RequestBody UserRequestDTO userDTO) {
        var user = userService.updateUserWithEmail(email, userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @Operation(summary = "UpdateWithUserNameByAdmin")
    @PutMapping("/admin/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUserWithUsernameByAdmin(@Valid @PathVariable String username,
                                                              @Valid @RequestBody AdminUserRequestDTO userDTO) {
        var user = userService.updateUserWithUsernameByAdmin(username, userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
