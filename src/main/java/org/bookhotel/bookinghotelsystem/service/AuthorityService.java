package org.bookhotel.bookinghotelsystem.service;

import org.bookhotel.bookinghotelsystem.dto.AuthorityRequestDTO;
import org.bookhotel.bookinghotelsystem.entity.Authority;

import java.util.List;

public interface AuthorityService {

    public List<Authority> getAllAuthorities();

    public Authority getAuthorityByName(String authorityName);

    public Authority getAuthorityById(Integer id);

    public Authority addAuthority(AuthorityRequestDTO authorityDTO);

    public void deleteAuthorityByName(String authorityName);
}
