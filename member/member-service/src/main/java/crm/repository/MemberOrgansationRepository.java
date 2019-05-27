package crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import crm.entity.MemberOrganisation;

public interface MemberOrgansationRepository extends JpaRepository<MemberOrganisation, Long> {

}
