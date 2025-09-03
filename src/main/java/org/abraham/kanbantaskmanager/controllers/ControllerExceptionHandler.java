package org.abraham.kanbantaskmanager.controllers;


import graphql.GraphQLError;
import org.abraham.kanbantaskmanager.Exceptions.EntityNotFoundException;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ControllerExceptionHandler {

    @GraphQlExceptionHandler
    public GraphQLError handleEntityNotFoundException (EntityNotFoundException ex) {
        return GraphQLError.newError()
                .errorType(ErrorType.BAD_REQUEST)
                .message(ex.getMessage()).build();
    }

}
