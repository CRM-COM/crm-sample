package crm.repository;

import crm.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByExternalId(String externalId);
  Optional<Member> findByNickname(String nickname);

  @Query("SELECT DISTINCT m from Member m " +
          "LEFT JOIN m.memberIdentities mi " +
          "WHERE (:query IS NOT NULL AND " +
          "(lower(m.forename) LIKE %:query% OR " +
          "lower(m.surname) LIKE %:query% OR " +
          "lower(m.nickname) LIKE %:query% OR " +
          "lower(concat(m.forename, ' ', m.surname)) LIKE %:query% OR " +
          "lower(concat(m.surname, ' ', m.forename)) LIKE %:query%) OR " +
          "lower(mi.identChallenge) LIKE %:query%)")
  Page<Member> search(@Param("query") String query, Pageable pageable);
}
