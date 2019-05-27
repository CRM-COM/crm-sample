package crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import crm.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByExternalId(String externalId);
  Optional<Member> findByEmail(String name);
}
