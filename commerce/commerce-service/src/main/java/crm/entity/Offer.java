package crm.entity;

import com.google.common.collect.Lists;
import crm.model.OfferType;
import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
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

    @Builder.Default
    @OneToMany(mappedBy = "offer", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<OfferPromotion> promotions = new HashSet<>();

    @OneToOne(mappedBy = "offer", cascade = CascadeType.ALL)
    private OfferEntitlement enablement;

    @Builder.Default
    @OneToMany(mappedBy = "offer", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<OfferGlobalisation> offerGlobalisations = new HashSet<>();

    @Builder.Default
    @OrderBy("sequence ASC")
    @OneToMany(mappedBy = "offer", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<OfferFeatures> offerFeatures = Lists.newArrayList();

    @ManyToOne(optional = false)
    private Product product;

    @OrderBy
    private int sequence;

    public void setEnablement(OfferEntitlement enablement) {
        if (this.enablement != null) {
            this.enablement.setOffer(null);
        }
        this.enablement = enablement;
        if (this.enablement != null) {
            this.enablement.setOffer(this);
        }
    }

    public void addPromotions(Set<OfferPromotion> promotions) {
        getPromotions().addAll(promotions);
    }
}
