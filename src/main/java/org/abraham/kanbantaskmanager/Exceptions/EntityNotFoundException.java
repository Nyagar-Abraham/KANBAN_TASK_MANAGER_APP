package org.abraham.kanbantaskmanager.Exceptions;

public class EntityNotFoundException extends RuntimeException {
    private static final String TEMPLATE = "%s with %s %s Not Found!";

    public EntityNotFoundException(String entityName,String field, String entityId) {
        super(String.format(TEMPLATE, entityName, field , entityId));
    }
}
