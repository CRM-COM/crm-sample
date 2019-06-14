package crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import crm.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  User findByExternalId(String externalId);
}
