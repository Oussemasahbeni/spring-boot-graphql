package com.graphql.demo.config;

import graphql.schema.GraphQLScalarType;

public class InstantScalar {

    public static final GraphQLScalarType INSTANCE = GraphQLScalarType.newScalar()
            .name("Instant")
            .description("A scalar type representing an instant in time (UTC)")
            .coercing(new GraphqlInstantCoercing())
            .build();
}
