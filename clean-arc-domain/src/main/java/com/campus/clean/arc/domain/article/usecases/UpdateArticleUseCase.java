package com.campus.clean.arc.domain.article.usecases;

import com.campus.clean.arc.domain.article.entity.ArticleEntity;
import com.campus.clean.arc.domain.article.exceptions.ArticleNotFoundException;
import com.campus.clean.arc.domain.article.port.ArticleRepository;
import com.rcore.domain.commons.usecase.UseCase;
import com.rcore.domain.commons.usecase.model.SingletonEntityOutputValues;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Optional;

@RequiredArgsConstructor
public class UpdateArticleUseCase extends UseCase<UpdateArticleUseCase.InputValues, SingletonEntityOutputValues<ArticleEntity>> {

    private final ArticleRepository articleRepository;

    @Override
    public SingletonEntityOutputValues<ArticleEntity> execute(InputValues inputValues) {
        Optional<ArticleEntity> articleEntityOptional = articleRepository.findById(inputValues.getId());
        if(articleEntityOptional.isEmpty()) throw new ArticleNotFoundException();

        ArticleEntity article = articleEntityOptional.get();
        article.setTitle(inputValues.getTitle());
        article.setDescription(inputValues.getDescription());
        article.setUpdatedAt(Instant.now());

        article = articleRepository.save(article);
        return SingletonEntityOutputValues.of(article);
    }

    @AllArgsConstructor(staticName = "of")
    @Getter
    @Setter
    public static class  InputValues implements UseCase.InputValues{
        private String id;
        private String title;
        private String description;
    }
}
