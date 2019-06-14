package crm.entity;


import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import crm.entity.billing.AccountReceivable;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE organisation SET is_deleted = 1 WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted=0")
public class Organisation extends AuditableEntityBase implements Serializable {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(length = 8192)
  private String description;

  @Column(length = 10)
  private String vatId;

  @Column(nullable = false)
  private LifeCycleState state;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Location location;

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private AccountReceivable accountReceivable;

  @OneToMany(mappedBy = "organisation", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
  private Collection<Project> projects;
}
