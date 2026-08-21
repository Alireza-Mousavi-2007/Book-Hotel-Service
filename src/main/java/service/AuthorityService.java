package service;

import dto.AuthorityRequestDTO;
import entity.Authority;

import java.util.List;

public interface AuthorityService {

    public List<Authority> getAllAuthorities();

    public Authority getAuthorityByName(String authorityNAme);

    public Authority getAuthorityById(Integer id);

    public Authority addAuthority(AuthorityRequestDTO authorityDTO);

    public void deleteAuthorityByName(String authorityNAme);
}
