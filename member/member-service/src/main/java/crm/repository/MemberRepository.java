package crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import crm.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
