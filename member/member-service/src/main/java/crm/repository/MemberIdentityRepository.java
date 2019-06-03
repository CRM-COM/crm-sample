package crm.repository;

import java.util.Optional;

import crm.model.IdentityProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import crm.entity.MemberIdentity;

public interface MemberIdentityRepository extends JpaRepository<MemberIdentity, Long> {

  Optional<MemberIdentity> findByIdentValueAndIdentProvider(String cardNumber, IdentityProvider provider);
}
