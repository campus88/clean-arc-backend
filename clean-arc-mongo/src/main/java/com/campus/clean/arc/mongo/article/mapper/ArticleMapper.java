package com.campus.clean.arc.mongo.article.mapper;

import com.campus.clean.arc.domain.article.entity.ArticleEntity;
import com.campus.clean.arc.mongo.article.documents.ArticleDoc;
import com.rcore.commons.mapper.ExampleDataMapper;
import com.rcore.database.mongo.commons.port.impl.ObjectIdGenerator;

public class ArticleMapper implements ExampleDataMapper<ArticleEntity, ArticleDoc> {
    private final ObjectIdGenerator objectIdGenerator = new ObjectIdGenerator();
    @Override
    public ArticleEntity inverseMap(ArticleDoc item) {
        return ArticleEntity.builder()
                .id(item.getId().toString())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())

                .title(item.getTitle())
                .description(item.getDescription())
                .build();
    }

    @Override
    public ArticleDoc map(ArticleEntity item) {
        return ArticleDoc.builder()
                .id(objectIdGenerator.parse(item.getId()))
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())

                .title(item.getTitle())
                .description(item.getDescription())
                .build();
    }
}
