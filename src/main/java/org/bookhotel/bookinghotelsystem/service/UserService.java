package org.bookhotel.bookinghotelsystem.service;

import org.bookhotel.bookinghotelsystem.dto.AdminUserRequestDTO;
import org.bookhotel.bookinghotelsystem.dto.UserRequestDTO;
import org.bookhotel.bookinghotelsystem.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    public List<User> getAllUsers();

    public User getUserById(Integer id);

    public User getByUsername(String username);

    public User getUserByEmail(String email);

    // for signup
    public User createUser(UserRequestDTO userRequestDTO);

    //for Admin
    public User addUser(AdminUserRequestDTO adminUserRequestDTO);

    public void deleteUserByName(String username);

    public boolean AreTheseForSameUser(String username, String email);

    public UserRequestDTO updateUserWithUsername(String username, UserRequestDTO userDTO);

    public UserRequestDTO updateUserWithEmail(String email, UserRequestDTO userDTO);

    public User updateUserWithUsernameByAdmin(String username, AdminUserRequestDTO userDTO);


}
