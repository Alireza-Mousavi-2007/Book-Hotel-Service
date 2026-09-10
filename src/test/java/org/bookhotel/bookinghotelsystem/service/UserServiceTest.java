package org.bookhotel.bookinghotelsystem.service;

import org.assertj.core.api.Assertions;
import org.bookhotel.bookinghotelsystem.dto.AdminUserRequestDTO;
import org.bookhotel.bookinghotelsystem.dto.UserRequestDTO;
import org.bookhotel.bookinghotelsystem.entity.Authority;
import org.bookhotel.bookinghotelsystem.entity.Role;
import org.bookhotel.bookinghotelsystem.entity.User;
import org.bookhotel.bookinghotelsystem.repository.UserRepository;
import org.bookhotel.bookinghotelsystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleService roleService;

    private Authority authority;
    private Role role;
    private User user;
    private UserRequestDTO userDTO;
    private AdminUserRequestDTO adminUserRequestDTO;


    @BeforeEach
    public void init() {


        authority = Authority.builder().authority("authority").build();
        role = Role.builder().id(1).role("role").authorities(Set.of(authority)).build();
        user = User.builder().id(1).username("username").password("password")
                .roles(Set.of(role)).email("example@email.com").build();
        userDTO = UserRequestDTO.builder().email("example@email.com")
                .username("username").password("password").build();
        adminUserRequestDTO = AdminUserRequestDTO.builder().username("username").password("password")
                .roles(Set.of(role.getRole())).email("example@email.com").build();

    }

    @Test
    public void createUser_whenCreated_user() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(roleService.getRoleByName(anyString())).thenReturn(role);

        var test = userService.createUser(userDTO);

        Assertions.assertThat(test).isNotNull();
        Assertions.assertThat(test.getId()).isEqualTo(user.getId());
        Assertions.assertThat(test.getUsername()).isEqualTo(user.getUsername());
        Assertions.assertThat(test.getEmail()).isEqualTo(user.getEmail());
        Assertions.assertThat(test.getPassword()).isEqualTo(user.getPassword());
        Assertions.assertThat(test.getRoles()).isEqualTo(user.getRoles());
        Assertions.assertThat(test.getAuthorities()).isEqualTo(user.getAuthorities());
    }

    @Test
    public void addUser_whenAdded_user() {
        when(roleService.getRoleByName(anyString())).thenReturn(role);
        when(userRepository.save(any(User.class))).thenReturn(user);

        var test = userService.addUser(adminUserRequestDTO);

        Assertions.assertThat(test).isNotNull();
        Assertions.assertThat(test.getId()).isEqualTo(user.getId());
        Assertions.assertThat(test.getUsername()).isEqualTo(user.getUsername());
        Assertions.assertThat(test.getEmail()).isEqualTo(user.getEmail());
        Assertions.assertThat(test.getPassword()).isEqualTo(user.getPassword());
        Assertions.assertThat(test.getRoles()).isEqualTo(user.getRoles());
        Assertions.assertThat(test.getAuthorities()).isEqualTo(user.getAuthorities());
    }

    @Test
    public void deleteUserByName_whenExists_void() {
        when(userRepository.findUserByUsername(anyString())).thenReturn(user);

        userService.deleteUserByName(user.getUsername());

        verify(userRepository).delete(any(User.class));

    }

    @Test
    public void AreTheseForSameUser_whenSame_boolean() {

        when(userRepository.findUserByEmail(anyString())).thenReturn(user);
        var test = userService.AreTheseForSameUser(user.getUsername(), user.getEmail());

        Assertions.assertThat(test).isTrue();
    }

    @Test
    public void AreTheseForSameUser_whenNotSame_boolean() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(user);

        var test = userService.AreTheseForSameUser("wrong", user.getEmail());

        Assertions.assertThat(test).isFalse();
    }

    @Test
    public void updateUserWithUsername_whenUpdated_userRequestDto() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findUserByUsername(anyString())).thenReturn(user);

        var test = userService.updateUserWithUsername(user.getUsername(), userDTO);

        Assertions.assertThat(test).isNotNull();
        Assertions.assertThat(test.getUsername()).isEqualTo(userDTO.getUsername());
        Assertions.assertThat(test.getEmail()).isEqualTo(userDTO.getEmail());

    }

    @Test
    public void updateUserWithEmail_whenUpdated_userRequestDto() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findUserByEmail(anyString())).thenReturn(user);

        var test = userService.updateUserWithEmail(user.getEmail(), userDTO);

        Assertions.assertThat(test).isNotNull();
        Assertions.assertThat(test.getUsername()).isEqualTo(userDTO.getUsername());
        Assertions.assertThat(test.getEmail()).isEqualTo(userDTO.getEmail());
    }

    @Test
    public void updateUserWithUsernameByAdmin_whenUpdated_user() {
        when(userRepository.findUserByUsername(anyString())).thenReturn(user);
        when(roleService.getRoleByName(anyString())).thenReturn(role);
        when(userRepository.save(any(User.class))).thenReturn(user);

        var test = userService.updateUserWithUsernameByAdmin(user.getUsername(), adminUserRequestDTO);

        Assertions.assertThat(test).isNotNull();
        Assertions.assertThat(test.getUsername()).isEqualTo(user.getUsername());
        Assertions.assertThat(test.getEmail()).isEqualTo(user.getEmail());
        Assertions.assertThat(test.getPassword()).isEqualTo(user.getPassword());
        Assertions.assertThat(test.getRoles()).isEqualTo(user.getRoles());
    }



}
