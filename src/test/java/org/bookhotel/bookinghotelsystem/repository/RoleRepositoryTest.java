package org.bookhotel.bookinghotelsystem.repository;

import org.assertj.core.api.Assertions;
import org.bookhotel.bookinghotelsystem.entity.Authority;
import org.bookhotel.bookinghotelsystem.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    private Authority authority;
    private Role role;

    @BeforeEach
    public void init() {
        authority = Authority.builder()
                .authority("authority")
                .build();
        var savedAuthority=authorityRepository.save(authority);
        role = Role.builder().role("role").authorities(Set.of(authority)).build();
        var savedRole = roleRepository.save(role);
    }

    @Test
    public void findRoleByRole_whenFound_role() {


        var tested = roleRepository.findRoleByRole(role.getRole());

        Assertions.assertThat(tested).isNotNull();
        Assertions.assertThat(tested.getId()).isEqualTo(role.getId());
        Assertions.assertThat(tested.getRole()).isEqualTo(role.getRole());
        Assertions.assertThat(tested.getAuthorities()).isEqualTo(role.getAuthorities());


    }
}
