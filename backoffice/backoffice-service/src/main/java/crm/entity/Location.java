package crm.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Location {

  @Id
  @GeneratedValue
  private Long id;

  @Column(length = 2048, nullable = false)
  private String fullAddress;

  @Column(length = 10)
  private String areaCode;

  @Column(length = 10, nullable = false)
  private String zipPostcode;

  @Column(length = 2, nullable = false)
  private String countryCode;

  @Column
  private BigDecimal latitude;

  @Column
  private BigDecimal longitude;
}
