package com.graphql.demo.config;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class GraphqlInstantCoercing implements Coercing<Instant, String> {

    @Override
    public String serialize(Object input, GraphQLContext graphQLContext, Locale locale) {
        if (input instanceof Instant) {
            return ((Instant) input).toString(); // Serialize as ISO-8601 string
        }
        throw new IllegalArgumentException("Expected Instant but got " + input.getClass());
    }

    @Override
    public Instant parseValue(Object input, GraphQLContext graphQLContext, Locale locale) {
        if (input instanceof String) {
            try {
                return Instant.parse((String) input); // Parse from ISO-8601 string
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid Instant format: " + input);
            }
        }
        throw new IllegalArgumentException("Expected String but got " + input.getClass());
    }

    @Override
    public Instant parseLiteral(Value<?> input, CoercedVariables variables, GraphQLContext graphQLContext, Locale locale) {
        if (input instanceof StringValue) {
            try {
                return Instant.parse(((StringValue) input).getValue());
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Invalid Instant format: " + input);
            }
        }
        throw new IllegalArgumentException("Expected StringValue but got " + input.getClass());
    }
}
