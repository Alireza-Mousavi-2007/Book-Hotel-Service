package service.impl;

import dto.RoleRequestDTO;
import entity.Authority;
import entity.Role;
import exception.AuthorityNotFoundException;
import exception.RoleNotFoundException;
import org.springframework.stereotype.Service;
import repository.RoleRepository;
import service.AuthorityService;
import service.RoleService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repo;
    private final AuthorityService authorityService;

    public RoleServiceImpl(RoleRepository repo, AuthorityService authorityService) {
        this.repo = repo;
        this.authorityService = authorityService;
    }

    @Override
    public List<Role> getAllRoles() {
        return repo.findAll();
    }

    @Override
    public Role getRoleByName(String roleName) {

        var role = repo.findRoleByRole(roleName);
        if (role == null)
            throw new RoleNotFoundException("there's no Role with name = " + roleName);
        else
            return role;
    }

    @Override
    public Role getRoleById(Integer id) {

        var role = repo.findById(id);
        if (role.isEmpty())
            throw new RoleNotFoundException("there's no Role with id = " + id);
        else
            return role.get();
    }

    @Override
    public Role addRole(RoleRequestDTO roleDTO) {
        var role = new Role();
        //TODO: check this later
        role.setRole(roleDTO.getRole());
        Set<Authority> roleAuthorities = roleDTO.getAuthorities().stream()
                .map(a -> authorityService.getAuthorityByName(a))
                .collect(Collectors.toSet());
        role.setAuthorities(roleAuthorities);

        return repo.save(role);
    }

    @Override
    public void deleteRoleByName(String roleName) {
        var role = repo.findRoleByRole(roleName);
        if (role == null)
            throw new RoleNotFoundException("there's no role with = " + roleName);
        else
            repo.delete(role);
    }
}
