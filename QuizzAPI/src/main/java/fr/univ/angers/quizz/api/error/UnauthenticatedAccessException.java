package fr.univ.angers.quizz.api.error;

import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

public class UnauthenticatedAccessException extends RuntimeException implements GraphQLError {

    public UnauthenticatedAccessException(String msg) {
        super(msg);
    }

    @Override
    public List<SourceLocation> getLocations() { return null; }

    @Override
    public ErrorClassification getErrorType() { return null; }
}
