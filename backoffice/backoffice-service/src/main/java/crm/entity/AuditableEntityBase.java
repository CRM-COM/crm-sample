package crm.entity;

import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@EqualsAndHashCode
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntityBase {

    @Column(nullable = false, unique = true, length = 36)
    @org.hibernate.annotations.Index(name = "external_id_idx")
    private String externalId;

    @EqualsAndHashCode.Exclude
    @CreatedDate
    @Column(nullable = false)
    private Instant createdOn;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    @EqualsAndHashCode.Exclude
    @LastModifiedDate
    @UpdateTimestamp
    private Instant modifiedOn;

    @CreatedBy
    @Column
    private String createdBy;

    @LastModifiedBy
    @Column
    private String modifiedBy;

    @PrePersist
    private void setExternalId() {
        if (externalId == null)
            this.externalId = UUID.randomUUID().toString();
    }


}
