package com.campus.clean.arc.mongo.author.mapper;

import com.campus.clean.arc.domain.author.entity.AuthorEntity;
import com.campus.clean.arc.mongo.author.documents.AuthorDoc;
import com.rcore.commons.mapper.ExampleDataMapper;
import com.rcore.database.mongo.commons.port.impl.ObjectIdGenerator;

public class AuthorMapper implements ExampleDataMapper<AuthorEntity, AuthorDoc> {
    private final ObjectIdGenerator idGenerator = new ObjectIdGenerator();
    @Override
    public AuthorEntity inverseMap(AuthorDoc item) {
        return AuthorEntity
                .builder()

                .id((item.getId().toString()))
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())

                .credentialId(item.getCredentialId())
                .firstName(item.getFirstName())
                .lastName(item.getLastName())
                .email(item.getEmail())

                .build();

    }

    @Override
    public AuthorDoc map(AuthorEntity item) {
        return AuthorDoc
                .builder()

                .id(idGenerator.parse(item.getId()))
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())

                .credentialId(item.getCredentialId())
                .firstName(item.getFirstName())
                .lastName(item.getLastName())
                .email(item.getEmail())

                .build();
    }
}
