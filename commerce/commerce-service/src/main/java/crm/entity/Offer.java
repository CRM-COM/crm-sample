package crm.entity;

import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@SQLDelete(sql = "UPDATE offer SET is_deleted = 1 WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted=0")
public class Offer extends AuditBase {

    @GeneratedValue
    @Id
    private Long id;

    @Column(length = 512, nullable = false)
    private String name;

    @Column(length = 64, nullable = false)
    private String offerCode;

    private String description;

    private boolean allowGroupAccess;

    private boolean eligibleForPromotion;

    @Enumerated(EnumType.STRING)
    private OfferType type;

    @Builder.Default
    @OneToMany(mappedBy = "offer", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<OfferPrice> prices = new HashSet<>();

    @ManyToOne(optional = false)
    private Product product;

    @OrderBy
    private int sequence;

}
