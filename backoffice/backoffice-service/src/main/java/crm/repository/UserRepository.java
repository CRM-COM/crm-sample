package crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import crm.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByEmail(String email);

  User findByExternalId(String externalId);
}
