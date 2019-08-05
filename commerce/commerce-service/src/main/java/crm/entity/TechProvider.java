package crm.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE tech_provider SET is_deleted = 1 WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted=0")
public class TechProvider extends AuditBase {

    @Id
    @GeneratedValue
    private Long id;


    @Column(length = 256)
    private String name;

    @Column(length = 512)
    private String description;

    @Column(length = 64)
    private String providerCode;

}
