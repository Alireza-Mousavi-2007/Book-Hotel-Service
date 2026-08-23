package org.bookhotel.bookinghotelsystem.service;

import org.bookhotel.bookinghotelsystem.dto.RoleRequestDTO;
import org.bookhotel.bookinghotelsystem.entity.Role;

import java.util.List;

public interface RoleService {

    public List<Role> getAllRoles();

    public Role getRoleByName(String roleName);

    public Role getRoleById(Integer id);

    public Role addRole(RoleRequestDTO roleDTO);

    public  void deleteRoleByName(String roleName);
}
