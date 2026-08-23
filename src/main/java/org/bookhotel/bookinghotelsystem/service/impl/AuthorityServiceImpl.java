package org.bookhotel.bookinghotelsystem.service.impl;

import org.bookhotel.bookinghotelsystem.dto.AuthorityRequestDTO;
import org.bookhotel.bookinghotelsystem.entity.Authority;
import org.bookhotel.bookinghotelsystem.exception.AuthorityNotFoundException;
import org.bookhotel.bookinghotelsystem.service.AuthorityService;
import org.springframework.stereotype.Service;
import org.bookhotel.bookinghotelsystem.repository.AuthorityRepository;

import java.util.List;
@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository repo;

    public AuthorityServiceImpl(AuthorityRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Authority> getAllAuthorities() {
        return repo.findAll();
    }

    @Override
    public Authority getAuthorityByName(String authorityName) {
        var authority = repo.findAuthorityByAuthority(authorityName);
        if (authority == null)
            throw new AuthorityNotFoundException("there's no authority with name = " + authorityName);
        else
            return authority;
    }

    @Override
    public Authority getAuthorityById(Integer id) {
        var authority = repo.findById(id);
        if (authority.isEmpty()) throw new AuthorityNotFoundException("there's no authority with id = " + id);
        else
            return authority.get();
    }

    @Override
    public Authority addAuthority(AuthorityRequestDTO authorityDTO) {
        var authority = new Authority();
        authority.setAuthority(authorityDTO.getAuthority());
        repo.save(authority);

        return authority;
    }

    @Override
    public void deleteAuthorityByName(String authorityName) {
        var authority = repo.findAuthorityByAuthority(authorityName);

        if (authority == null)
            throw new AuthorityNotFoundException("there's no authority with name = " + authorityName);
        else
            repo.delete(authority);

    }
}
