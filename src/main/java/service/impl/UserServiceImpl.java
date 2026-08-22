package service.impl;

import dto.UserRequestDTO;
import entity.Role;
import entity.User;
import exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import repository.RoleRepository;
import repository.UserRepository;
import service.RoleService;
import service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {

    private final RoleService roleService;
    private final UserRepository repo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(RoleService roleService, UserRepository repo, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public User getUSerById(Integer id) {
        var user = repo.findById(id);
        if (user.isEmpty()) throw new UserNotFoundException("there's no user with = " + id);
        else
            return user.get();
    }

    @Override
    public User getByUsername(String username) {
        var user = repo.findUserByUsername(username);
        if (user == null) throw new UserNotFoundException("there's no user with = " + username);
        else
            return user;
    }

    @Override
    public User getUserByEmail(String email) {
        var user = repo.findUserByEmail(email);
        if(user==null)throw new UserNotFoundException("there's no user with = " + email);
        else
            return user;
    }

    // for signup
    @Override
    public User createUser(UserRequestDTO userRequestDTO) {
        var user = new User();
        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        var userRoles = roleService.getRoleByName("USER");
        return repo.save(user);
    }

    // for admin
    @Override
    public User addUser(User user) {
        var newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        //TODO: Check This
        Set<Role> roleSet = user.getRoles().stream()
                .map(a -> roleService.getRoleByName(a.getRole()))
                .collect(Collectors.toSet());
        newUser.setRoles(roleSet);


        return repo.save(user);
    }

    @Override
    public void deleteUserByName(String username) {

        var user = repo.findUserByUsername(username);
        if (user == null) throw new UserNotFoundException("there's no user with = " + username);
        else
            repo.delete(user);

    }

    // for login : should be able to sign in with username and email
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        var user= repo.findUserByUsername(usernameOrEmail);
        if (user == null) user=repo.findUserByEmail(usernameOrEmail);
        if (user==null) throw new UserNotFoundException("there's no user with = " + usernameOrEmail);
        else

        return user;
    }
}
