package crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import crm.entity.Organisation;

public interface OrganisationRepository extends JpaRepository<Organisation, Long> {

  Optional<Organisation> findByExternalId(String organisationExternalId);

  boolean existsOrganisationByVatId(String vatId);
}
