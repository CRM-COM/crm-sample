package crm.entity;

import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@SQLDelete(sql = "UPDATE promotion SET is_deleted = 1 WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted=0")
public class Promotion extends AuditBase {

    @GeneratedValue
    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "NAME", length = 128, nullable = false)
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "PERCENTAGE")
    private Double percentage;
    @Column(name = "FIXED_PRICE")
    private Double fixedPrice;
    @Column(name = "START_DATE")
    private Date startDate;
    @Column(name = "END_DATE")
    private Date endDate;
    @Column(name = "EXTRA_INSTANCES")
    private Integer extraInstances;
    @Column(name = "COUPON")
    private String coupon;
    @Column(name = "ISO4127CURRENCY_CODE")
    private String iso4127CurrencyCode;

    @Builder.Default
    @OneToMany(mappedBy = "promotion", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<PromotionSegment> segmentation = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "promotion", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<PromotionGlobalisation> promotionGlobalisations = new HashSet<>();

}
