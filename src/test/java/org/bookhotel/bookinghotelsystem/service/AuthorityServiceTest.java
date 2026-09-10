package org.bookhotel.bookinghotelsystem.service;

import org.assertj.core.api.Assertions;
import org.bookhotel.bookinghotelsystem.dto.AuthorityRequestDTO;
import org.bookhotel.bookinghotelsystem.entity.Authority;
import org.bookhotel.bookinghotelsystem.repository.AuthorityRepository;
import org.bookhotel.bookinghotelsystem.service.impl.AuthorityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorityServiceTest {

    @Mock
    private AuthorityRepository authorityRepository;
    @InjectMocks
    private AuthorityServiceImpl authorityService;

    private Authority authority;
    private AuthorityRequestDTO authorityDTO;

    @BeforeEach
    public void init() {
        authorityDTO = AuthorityRequestDTO.builder().authority("authority").build();
        authority = Authority.builder().authority("authority").build();
    }

    @Test
    public void addAuthority_whenAdded_authority() {
        when(authorityRepository.save(any(Authority.class))).thenReturn(authority);

        var tested = authorityService.addAuthority(authorityDTO);

        Assertions.assertThat(tested).isNotNull();
        Assertions.assertThat(tested.getAuthority()).isEqualTo(authority.getAuthority());
        Assertions.assertThat(tested.getId()).isEqualTo(authority.getId());

        Mockito.verify(authorityRepository).save(any(Authority.class));
    }

    @Test
    public void deleteAuthorityByName_whenExists_void() {
        when(authorityRepository.findAuthorityByAuthority(anyString())).thenReturn(authority);
        authorityService.deleteAuthorityByName(authority.getAuthority());

        Mockito.verify(authorityRepository).delete(any(Authority.class));
    }
}
