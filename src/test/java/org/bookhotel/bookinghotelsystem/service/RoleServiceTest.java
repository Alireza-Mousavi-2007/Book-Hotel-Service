package org.bookhotel.bookinghotelsystem.service;

import org.assertj.core.api.Assertions;
import org.bookhotel.bookinghotelsystem.dto.AuthorityRequestDTO;
import org.bookhotel.bookinghotelsystem.dto.RoleRequestDTO;
import org.bookhotel.bookinghotelsystem.entity.Authority;
import org.bookhotel.bookinghotelsystem.entity.Role;
import org.bookhotel.bookinghotelsystem.repository.RoleRepository;
import org.bookhotel.bookinghotelsystem.service.impl.RoleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private AuthorityService authorityService;
    @InjectMocks
    private RoleServiceImpl roleService;

    private Role role;
    private RoleRequestDTO roleRequestDTO;
    private Authority authority;

    @BeforeEach
    public void init() {
        authority = Authority.builder().authority("authority").build();
        role = Role.builder().id(1).role("role").authorities(Set.of(authority)).build();
        roleRequestDTO = RoleRequestDTO.builder().role("role").authorities(Set.of(authority.getAuthority())).build();
    }

    @Test
    public void addRole_whenAdded_role() {
        when(roleRepository.save(any(Role.class))).thenReturn(role);

        var tested = roleService.addRole(roleRequestDTO);

        Assertions.assertThat(tested).isNotNull();
        Assertions.assertThat(tested.getId()).isEqualTo(role.getId());
        Assertions.assertThat(tested.getRole()).isEqualTo(role.getRole());
        Assertions.assertThat(tested.getAuthorities()).isEqualTo(role.getAuthorities());
    }

    @Test
    public void deleteRoleByName_whenExists_void(){
        when(roleRepository.findRoleByRole(anyString())).thenReturn(role);

        roleService.deleteRoleByName(role.getRole());
        verify(roleRepository).delete(any(Role.class));
    }
}
