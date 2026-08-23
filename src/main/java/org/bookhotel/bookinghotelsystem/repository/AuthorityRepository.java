package org.bookhotel.bookinghotelsystem.repository;

import org.bookhotel.bookinghotelsystem.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority,Integer> {

    public Authority findAuthorityByAuthority(String AuthorityName);
}
