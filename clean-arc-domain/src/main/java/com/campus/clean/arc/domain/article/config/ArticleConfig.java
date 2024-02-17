package com.campus.clean.arc.domain.article.config;

import com.campus.clean.arc.domain.article.port.ArticleIdGenerator;
import com.campus.clean.arc.domain.article.port.ArticleRepository;
import com.campus.clean.arc.domain.article.usecases.*;
import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
public class ArticleConfig {
    private final CreateArticleUseCase createArticleUseCase;
    private final DeleteArticleUseCase deleteArticleUseCase;
    private final FindArticleByIdUseCase findArticleByIdUseCase;
    private final FindArticleWithFiltersUseCase findArticleWithFiltersUseCase;
    private final UpdateArticleUseCase updateArticleUseCase;

    public ArticleConfig(
            ArticleIdGenerator articleIdGenerator,
            ArticleRepository articleRepository) {
        this.createArticleUseCase = new CreateArticleUseCase(articleIdGenerator,articleRepository);
        this.deleteArticleUseCase = new DeleteArticleUseCase(articleRepository);
        this.findArticleByIdUseCase = new FindArticleByIdUseCase(articleRepository);
        this.findArticleWithFiltersUseCase = new FindArticleWithFiltersUseCase(articleRepository);
        this.updateArticleUseCase = new UpdateArticleUseCase(articleRepository);
    }
}
