package crm.model;

import org.apache.commons.lang3.BooleanUtils;

public interface SoftDeletable {

    Boolean getIsDeleted();

    void setIsDeleted(Boolean isDeleted);

    default boolean isDeleted() {
        return BooleanUtils.isTrue(getIsDeleted());
    }
}
