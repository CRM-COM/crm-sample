package crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import crm.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByExternalId(String externalId);

  Role findByName(String admin);
}
