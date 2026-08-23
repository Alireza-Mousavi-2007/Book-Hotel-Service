package org.bookhotel.bookinghotelsystem.repository;

import org.bookhotel.bookinghotelsystem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    public Role findRoleByRole(String role);
}
