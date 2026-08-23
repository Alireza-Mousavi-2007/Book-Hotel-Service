package service;

import dto.AdminUserRequestDTO;
import dto.UserRequestDTO;
import entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    public List<User> getAllUsers();

    public User getUSerById(Integer id);

    public User getByUsername(String username);

    public User getUserByEmail(String email);

    // for signup
    public User createUser(UserRequestDTO userRequestDTO);

    //for Admin
    public User addUser(AdminUserRequestDTO adminUserRequestDTO);

    public void deleteUserByName(String username);


}
