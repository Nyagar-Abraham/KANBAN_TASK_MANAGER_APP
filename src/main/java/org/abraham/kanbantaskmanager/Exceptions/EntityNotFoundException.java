package org.abraham.kanbantaskmanager.Exceptions;

public class EntityNotFoundException extends RuntimeException {
    private static final String TEMPLATE = "%s with ID %s Not Found!";

    public EntityNotFoundException(String entityName, String entityId) {
        super(String.format(TEMPLATE, entityName, entityId));
    }
}
