package fr.univ.angers.quizz.api.error;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

public class UserAlreadyExistsException extends RuntimeException implements GraphQLError {

    public UserAlreadyExistsException(String message) {
        super(message);

    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorClassification getErrorType() {
        return ErrorType.ValidationError;
    }
}
