package org.bookhotel.bookinghotelsystem.controller;

import org.bookhotel.bookinghotelsystem.dto.AdminUserRequestDTO;
import org.bookhotel.bookinghotelsystem.dto.UserRequestDTO;
import org.bookhotel.bookinghotelsystem.entity.Authority;
import org.bookhotel.bookinghotelsystem.entity.Role;
import org.bookhotel.bookinghotelsystem.entity.User;
import org.bookhotel.bookinghotelsystem.security.jwt.JwtToken;
import org.bookhotel.bookinghotelsystem.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private UserService userService;
    @MockitoBean
    private JwtToken jwtToken;
//    @MockitoBean
//    private UserDetailsService userDetailsService;


    private AdminUserRequestDTO adminUserRequestDTO;
    private UserRequestDTO userRequestDTO;
    private User user;
    private Authority authority;
    private Role role;

    @BeforeEach
    public void init() {
        authority = Authority.builder().authority("authority").build();
        role = Role.builder().id(1).role("role").authorities(Set.of(authority)).build();
        user = User.builder().id(1).username("username").password("password")
                .roles(Set.of(role)).email("example@email.com").build();
        userRequestDTO = UserRequestDTO.builder().email("example@email.com")
                .username("username").password("password").build();
        adminUserRequestDTO = AdminUserRequestDTO.builder().username("username").password("password")
                .roles(Set.of(role.getRole())).email("example@email.com").build();

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getAllUsers_whenFound_listOfUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(user));

        mockMvc.perform(get("/api/users/admin"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$[0].username").value(user.getUsername()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getUserById_whenFound_user() throws Exception {
        when(userService.getUserById(anyInt())).thenReturn(user);

        mockMvc.perform(get("/api/users/admin/{id}", user.getId()))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.username").value(user.getUsername()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getUserByUsername_whenFound_user() throws Exception {
        when(userService.getByUsername(anyString())).thenReturn(user);

        mockMvc.perform(get("/api/users/{username}", user.getUsername()))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.username").value(user.getUsername()));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void getUserByEmail_whenFound_user() throws Exception {
        when(userService.getUserByEmail(anyString())).thenReturn(user);

        mockMvc.perform(get("/api/users/by-email/{email:.+}", user.getEmail()))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.username").value(user.getUsername()));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void addUSerByAdmin_whenAdded_user() throws Exception {
        when(userService.addUser(any(AdminUserRequestDTO.class))).thenReturn(user);

        mockMvc.perform(post("/api/users/admin")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adminUserRequestDTO))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(adminUserRequestDTO.getUsername()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteUserByName_whenDeleted_responseEntity() throws Exception {

        mockMvc.perform(delete("/api/users/{username}", user.getUsername())
                .with(csrf())
        ).andExpect(status().isOk());

        verify(userService).deleteUserByName(anyString());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateUserWithUsername_whenAdded_responseEntityOfUserRequestDTO() throws Exception {
        when(userService.updateUserWithUsername(anyString(), any(UserRequestDTO.class))).thenReturn(userRequestDTO);

        mockMvc.perform(put("/api/users/{username}",user.getUsername())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(userRequestDTO.getUsername()))
                .andExpect(jsonPath("$.email").value(userRequestDTO.getEmail()))
                .andExpect(jsonPath("$.password").value(userRequestDTO.getPassword()));

    }

    @Test
    @WithMockUser(username ="alireza" ,roles = "ADMIN")
    public void updateUserWithEmail_whenAdded_responseEntityOfUserRequestDTO() throws Exception {
        when(userService.updateUserWithEmail(anyString(), any(UserRequestDTO.class))).thenReturn(userRequestDTO);

        mockMvc.perform(put("/api/users/by-email/{email:.+}",user.getEmail())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDTO))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(userRequestDTO.getUsername()))
                .andExpect(jsonPath("$.email").value(userRequestDTO.getEmail()))
                .andExpect(jsonPath("$.password").value(userRequestDTO.getPassword()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateUserWithUsernameByAdmin_whenAdded_responseEntityOfUserRequestDTO() throws Exception {
        when(userService.updateUserWithUsernameByAdmin(anyString(), any(AdminUserRequestDTO.class))).thenReturn(user);

        mockMvc.perform(put("/api/users/admin/{username}",user.getUsername())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(adminUserRequestDTO))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(adminUserRequestDTO.getUsername()))
                .andExpect(jsonPath("$.email").value(adminUserRequestDTO.getEmail()));
    }




}
