package org.abraham.kanbantaskmanager.Exceptions;

public class DuplicateEntityException extends RuntimeException {
    private static final String TEMPLATE = "%s with %s %s already exists";

    public DuplicateEntityException(String entity,String field,String id) {
        super(String.format(TEMPLATE, entity,field, id));
    }
}
