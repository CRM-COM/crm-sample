package crm.model;

public interface ExternalIdIdentifiable {
    String getExternalId();

    static String toExternalId(ExternalIdIdentifiable externalIdIdentifiable) {
        return externalIdIdentifiable.getExternalId();
    }
}
