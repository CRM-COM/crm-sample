package crm.repository;

import java.util.Optional;

import crm.model.IdentityProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import crm.entity.MemberIdentity;

public interface MemberIdentityRepository extends JpaRepository<MemberIdentity, Long> {

  Optional<MemberIdentity> findByIdentValueAndIdentityProvider(String cardNumber, IdentityProvider provider);
  Optional<MemberIdentity> findByIdentChallengeAndIdentityProvider(String email, IdentityProvider provider);
}
