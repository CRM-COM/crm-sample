package crm.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import crm.entity.Organisation;
import crm.entity.UserOrganisation;

public interface UserOrganisationRepository extends JpaRepository<UserOrganisation, Long> {

  List<UserOrganisation> findByUserExternalId(String externalId);

  @Query("Select a.organisation from UserOrganisation a where a.user.externalId=?1")
  Set<Organisation> findAllOrganisationsByUserExternalId(String userExternalId);

  List<UserOrganisation> findByUserExternalIdAndOrganisationExternalId(String externalId,
      String organisationExternalId);
}
