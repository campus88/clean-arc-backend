package com.campus.clean.arc.api.article.routes;

import com.rcore.rest.api.commons.routes.BaseRoutes;

public class ArticleRoutes {
    public static final String ROOT= BaseRoutes.API + BaseRoutes.V1 + "/article";
    public static final String BY_ID = ROOT + "/{id}";
}
