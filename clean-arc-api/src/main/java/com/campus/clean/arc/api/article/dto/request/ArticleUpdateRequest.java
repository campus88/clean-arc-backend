package com.campus.clean.arc.api.article.dto.request;

import lombok.Getter;

@Getter
public class ArticleUpdateRequest extends ArticleCreateRequest{
    protected String id;
}
