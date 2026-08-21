package service;

import dto.RoleRequestDTO;
import entity.Role;

import java.util.List;

public interface RoleService {

    public List<Role> getAllRoles();

    public Role getRoleByName(String roleName);

    public Role getRoleById(Integer id);

    public Role AddRole(RoleRequestDTO roleDTO);

    public  void deleteRoleByName(String RoleNAme);
}
