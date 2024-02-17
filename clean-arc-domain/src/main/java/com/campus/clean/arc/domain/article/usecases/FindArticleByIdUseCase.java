package com.campus.clean.arc.domain.article.usecases;

import com.campus.clean.arc.domain.article.entity.ArticleEntity;
import com.campus.clean.arc.domain.article.port.ArticleRepository;
import com.rcore.domain.commons.usecase.AbstractFindByIdUseCase;

public class FindArticleByIdUseCase extends AbstractFindByIdUseCase<String, ArticleEntity, ArticleRepository> {
    public FindArticleByIdUseCase(ArticleRepository repository) {
        super(repository);
    }
}
