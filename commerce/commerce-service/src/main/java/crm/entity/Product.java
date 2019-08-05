package crm.entity;

import com.google.common.collect.Lists;
import crm.model.ProductType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE product SET is_deleted = 1 WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted=0")
public class Product extends AuditBase {

    private String name;

    @GeneratedValue
    @Id
    private long id;

    private String description;

    @Enumerated(EnumType.STRING)
    private ProductType type;

    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("sequence ASC")
    private List<Offer> offers = Lists.newArrayList();

    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<ProductGlobalisation> productGlobalisations = new HashSet<>();

    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("sequence ASC")
    private List<ProductPlatform> productPlatforms = Lists.newArrayList();

}
