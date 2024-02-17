package com.campus.clean.arc.api.article.controller;

import com.campus.clean.arc.api.article.dto.request.ArticleCreateRequest;
import com.campus.clean.arc.api.article.dto.request.ArticleUpdateRequest;
import com.campus.clean.arc.api.article.dto.response.ArticleResponse;
import com.campus.clean.arc.api.article.mapper.ArticleMapper;
import com.campus.clean.arc.api.article.routes.ArticleRoutes;
import com.campus.clean.arc.domain.article.config.ArticleConfig;
import com.campus.clean.arc.domain.article.entity.ArticleEntity;
import com.campus.clean.arc.domain.article.exceptions.ArticleNotFoundException;
import com.rcore.domain.commons.usecase.UseCaseExecutor;
import com.rcore.domain.commons.usecase.model.FiltersInputValues;
import com.rcore.domain.commons.usecase.model.IdInputValues;
import com.rcore.domain.commons.usecase.model.SingletonOptionalEntityOutputValues;
import com.rcore.rest.api.commons.request.SearchApiRequest;
import com.rcore.rest.api.commons.response.SearchApiResponse;
import com.rcore.rest.api.commons.response.SuccessApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "Article API")
public class ArticleApiController {
    private final UseCaseExecutor executor;
    private final ArticleConfig config;

    @Operation(summary = "Search articles")
    @GetMapping(value = ArticleRoutes.ROOT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessApiResponse<SearchApiResponse<ArticleResponse>> search(@ModelAttribute SearchApiRequest request) {
        return executor.execute(
                config.findArticleWithFiltersUseCase(),
                FiltersInputValues.of(ArticleMapper.map(request)),
                o -> SuccessApiResponse.of(SearchApiResponse.withItemsAndCount(
                        o.getResult().getItems()
                                .stream()
                                .map(ArticleMapper::map)
                                .collect(Collectors.toList()),
                        o.getResult().getCount()
                )));
    }

    @Operation(summary = "Get Article by id")
    @GetMapping(value = ArticleRoutes.BY_ID, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessApiResponse<ArticleResponse> byId(@PathVariable String id) {
        Optional<ArticleEntity> article = executor.execute(
                config.findArticleByIdUseCase(),
                IdInputValues.of(id),
                SingletonOptionalEntityOutputValues::getEntity
        );
        return SuccessApiResponse.of(ArticleMapper.map(article.orElseThrow(ArticleNotFoundException::new)));
    }


    @Operation(summary = "Create Article")
    @PostMapping(value = ArticleRoutes.ROOT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessApiResponse<ArticleResponse> create(@RequestBody ArticleCreateRequest request) {
        return executor.execute(
                config.createArticleUseCase(),
                ArticleMapper.map(request),
                o -> SuccessApiResponse.of(ArticleMapper.map(o.getEntity()))
        );
    }

    @Operation(summary = "Update Article")
    @PutMapping(value = ArticleRoutes.BY_ID, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessApiResponse<ArticleResponse> update(@PathVariable String id, @RequestBody ArticleUpdateRequest request) {
        return executor.execute(
                config.updateArticleUseCase(),
                ArticleMapper.map(request),
                o -> SuccessApiResponse.of(ArticleMapper.map(o.getEntity()))
        );
    }

    @Operation(summary = "Delete Article by id")
    @DeleteMapping(value = ArticleRoutes.BY_ID, produces = {MediaType.APPLICATION_JSON_VALUE})
    public SuccessApiResponse<String> delete(@PathVariable String id) {
        return executor.execute(
                config.deleteArticleUseCase(),
                IdInputValues.of(id),
                o -> SuccessApiResponse.of(HttpStatus.OK.name()));
    }
}
