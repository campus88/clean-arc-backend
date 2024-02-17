package com.campus.clean.arc.api.article.mapper;

import com.campus.clean.arc.api.article.dto.request.ArticleCreateRequest;
import com.campus.clean.arc.api.article.dto.request.ArticleUpdateRequest;
import com.campus.clean.arc.api.article.dto.response.ArticleResponse;
import com.campus.clean.arc.domain.article.entity.ArticleEntity;
import com.campus.clean.arc.domain.article.port.filters.ArticleFilters;
import com.campus.clean.arc.domain.article.usecases.CreateArticleUseCase;
import com.campus.clean.arc.domain.article.usecases.UpdateArticleUseCase;
import com.rcore.rest.api.commons.request.SearchApiRequest;

public class ArticleMapper {
    public static ArticleFilters map(SearchApiRequest request) {
        return ArticleFilters.builder()
                .limit(request.getLimit())
                .offset(request.getOffset())
                .query(request.getQuery())
                .sortDirection(request.getSortDirection())
                .sortName(request.getSortName())
                .build();
    }

    public static ArticleResponse map(ArticleEntity item) {
        return ArticleResponse.builder()
                .id(item.getId())
                .title(item.getTitle())
                .description(item.getDescription())
                .createdAt(item.getCreatedAt())
                .build();
    }

    public static CreateArticleUseCase.InputValues map(ArticleCreateRequest item) {
        return CreateArticleUseCase.InputValues.of(item.getTitle(), item.getDescription());
    }

    public static UpdateArticleUseCase.InputValues map(ArticleUpdateRequest item) {
        return UpdateArticleUseCase.InputValues.of(item.getId(), item.getTitle(), item.getDescription());
    }
}
