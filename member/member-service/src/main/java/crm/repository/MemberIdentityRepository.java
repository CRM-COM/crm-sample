package crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import crm.entity.MemberIdentity;

public interface MemberIdentityRepository extends JpaRepository<MemberIdentity, Long> {

}
