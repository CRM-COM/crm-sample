package crm.model;

import crm.entity.LifeCycleState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganisationDto {

  private String name;
  private String description;
  private String fullAddress;
  private String vatId;
  private LifeCycleState state;
}
