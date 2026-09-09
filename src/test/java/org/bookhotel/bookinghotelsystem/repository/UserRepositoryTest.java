package org.bookhotel.bookinghotelsystem.repository;

import org.assertj.core.api.Assertions;
import org.bookhotel.bookinghotelsystem.entity.Authority;
import org.bookhotel.bookinghotelsystem.entity.Role;
import org.bookhotel.bookinghotelsystem.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;


    private Authority authority;
    private Role role;
    private User user;

    @BeforeEach
    public void init() {
        authority = Authority.builder()
                .authority("authority")
                .build();
        authority = authorityRepository.save(authority);

        role = Role.builder().role("role").authorities(Set.of(authority)).build();
        role = roleRepository.save(role);

        user = User.builder().username("username")
                .email("example@email.com")
                .password("password")
                .roles(Set.of(role)).build();
        user = userRepository.save(user);
    }

    @Test
    public void findUserByUsername_whenFound_user() {
        var tested = userRepository.findUserByUsername(user.getUsername());

        Assertions.assertThat(tested).isNotNull();
        Assertions.assertThat(tested.getId()).isEqualTo(user.getId());
        Assertions.assertThat(tested.getUsername()).isEqualTo(user.getUsername());
        Assertions.assertThat(tested.getEmail()).isEqualTo(user.getEmail());
        Assertions.assertThat(tested.getPassword()).isEqualTo(user.getPassword());
        Assertions.assertThat(tested.getRoles().size()).isEqualTo(1);

    }

    @Test
    public void findUserByEmail_whenFound_user() {
        var tested = userRepository.findUserByEmail(user.getEmail());

        Assertions.assertThat(tested).isNotNull();
        Assertions.assertThat(tested.getId()).isEqualTo(user.getId());
        Assertions.assertThat(tested.getUsername()).isEqualTo(user.getUsername());
        Assertions.assertThat(tested.getEmail()).isEqualTo(user.getEmail());
        Assertions.assertThat(tested.getPassword()).isEqualTo(user.getPassword());
        Assertions.assertThat(tested.getRoles().size()).isEqualTo(1);

    }

    @Test
    public void existsByUsername_whenExists_boolean() {
        var tested = userRepository.existsByUsername(user.getUsername());

        Assertions.assertThat(tested).isTrue();
    }
}
