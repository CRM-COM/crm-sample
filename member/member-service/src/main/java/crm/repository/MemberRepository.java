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
          "WHERE " +
          "(:forename IS NOT NULL AND lower(m.forename) LIKE %lower(:forename)%) OR " +
          "(:surname IS NOT NULL AND lower(m.surname) LIKE %lower(:surname)%) OR " +
          "(:nickname IS NOT NULL AND lower(m.nickname) LIKE %lower(:nickname)%) OR " +
          "(:email IS NOT NULL AND lower(mi.identChallenge) LIKE %lower(:email)%)")
  Page<Member> search(@Param("forename") String forename,
                      @Param("surname") String surname,
                      @Param("nickname") String nickname,
                      @Param("email") String email,
                      Pageable pageable);
}
