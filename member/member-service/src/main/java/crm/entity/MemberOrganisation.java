package crm.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
public class MemberOrganisation {

  @Id
  @GeneratedValue
  private long id;

  @ManyToOne
  @JoinColumn
  private Member member;

  private String externalId;

  private String name;

}
