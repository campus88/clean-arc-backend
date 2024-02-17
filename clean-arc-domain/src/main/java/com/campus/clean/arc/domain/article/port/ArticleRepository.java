package com.campus.clean.arc.domain.article.port;

import com.campus.clean.arc.domain.article.entity.ArticleEntity;
import com.campus.clean.arc.domain.article.port.filters.ArticleFilters;
import com.rcore.domain.commons.port.CRUDRepository;

public interface ArticleRepository extends CRUDRepository<String, ArticleEntity, ArticleFilters> {
}
