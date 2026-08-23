package org.bookhotel.bookinghotelsystem.service.impl;

import org.bookhotel.bookinghotelsystem.dto.AdminUserRequestDTO;
import org.bookhotel.bookinghotelsystem.dto.UserRequestDTO;
import org.bookhotel.bookinghotelsystem.entity.Role;
import org.bookhotel.bookinghotelsystem.entity.User;
import org.bookhotel.bookinghotelsystem.exception.UserNotFoundException;
import org.bookhotel.bookinghotelsystem.repository.UserRepository;
import org.bookhotel.bookinghotelsystem.service.RoleService;
import org.bookhotel.bookinghotelsystem.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
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
    public User getUserById(Integer id) {
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
        if (user == null) throw new UserNotFoundException("there's no user with = " + email);
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

        var userRoles = Set.of(roleService.getRoleByName("USER"));
        user.setRoles(userRoles);
        return repo.save(user);
    }

    // for admin
    @Override
    public User addUser(AdminUserRequestDTO user) {
        var newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        //TODO: Check This
        Set<Role> roleSet = user.getRoles().stream()
                .map(a -> roleService.getRoleByName(a))
                .collect(Collectors.toSet());

        newUser.setRoles(roleSet);

        return repo.save(newUser);
    }

    @Override
    public void deleteUserByName(String username) {

        var user = repo.findUserByUsername(username);
        if (user == null) throw new UserNotFoundException("there's no user with = " + username);
        else
            repo.delete(user);

    }

    @Override
    public boolean AreTheseForSameUser(String username, String email) {
        var user = getUserByEmail(email);
        if (user == null) throw new UserNotFoundException("There's no user with email = " + email);
        return user.getUsername() == username;
    }

    // for login : should be able to sign in with username and email
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        var user = repo.findUserByUsername(usernameOrEmail);
        if (user == null) user = repo.findUserByEmail(usernameOrEmail);
        if (user == null) throw new UserNotFoundException("there's no user with = " + usernameOrEmail);
        else

            return user;
    }
}
