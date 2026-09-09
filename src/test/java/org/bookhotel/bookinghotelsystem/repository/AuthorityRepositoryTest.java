package org.bookhotel.bookinghotelsystem.repository;

import org.assertj.core.api.Assertions;
import org.bookhotel.bookinghotelsystem.entity.Authority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AuthorityRepositoryTest {

    @Autowired
    private AuthorityRepository authorityRepository;

    private Authority authority;
    @BeforeEach
    public void init(){
        authority = Authority.builder()
                .authority("authority")
                .build();
    }

    @Test
    public void findAuthorityByAuthority_whenFound_authority() {

       var saved= authorityRepository.save(authority);

        var tested = authorityRepository.findAuthorityByAuthority(authority.getAuthority());

        Assertions.assertThat(tested).isNotNull();
        Assertions.assertThat(tested.getId()).isEqualTo(authority.getId());
        Assertions.assertThat(tested.getAuthority()).isEqualTo(authority.getAuthority());
    }
}
