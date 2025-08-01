package org.abraham.kanbantaskmanager.Exceptions;

public class DuplicateNameException extends RuntimeException {
    private static final String TEMPLATE = "%s with name %s already exists";

    public DuplicateNameException(String entity, String name) {
        super(String.format(TEMPLATE, entity, name));
    }
}
