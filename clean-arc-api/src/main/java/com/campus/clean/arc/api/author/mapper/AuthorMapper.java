package com.campus.clean.arc.api.author.mapper;

import com.campus.clean.arc.api.author.dto.request.AuthorEditRequest;
import com.campus.clean.arc.api.author.dto.request.ChangePasswordRequest;
import com.campus.clean.arc.api.author.dto.request.RegistrationRequest;
import com.campus.clean.arc.api.author.dto.response.AuthorResponse;
import com.campus.clean.arc.domain.author.entity.AuthorEntity;
import com.campus.clean.arc.domain.author.port.filters.AuthorFilters;
import com.campus.clean.arc.domain.author.usecases.ChangePasswordUseCase;
import com.campus.clean.arc.domain.author.usecases.CreateAuthorUserCase;
import com.campus.clean.arc.domain.author.usecases.UpdateAuthorUseCase;
import com.rcore.rest.api.commons.request.SearchApiRequest;

public class AuthorMapper {
    public static UpdateAuthorUseCase.InputValues map(AuthorEditRequest item) {
        return UpdateAuthorUseCase.InputValues.of(item.getId(), item.getFirstName(), item.getLastName());
    }

    public static ChangePasswordUseCase.InputValues map(String authorId, ChangePasswordRequest item) {
        return ChangePasswordUseCase.InputValues.of(authorId, item.getPassword());
    }

    public static AuthorFilters map(SearchApiRequest request) {
        return AuthorFilters.builder()
                .limit(request.getLimit())
                .offset(request.getOffset())
                .query(request.getQuery())
                .sortDirection(request.getSortDirection())
                .sortName(request.getSortName())
                .build();
    }

    public static CreateAuthorUserCase.InputValues map(RegistrationRequest item) {
        return CreateAuthorUserCase.InputValues.of(
                item.getEmail(),
                item.getPassword(),
                item.getFirstName(),
                item.getLastName());
    }

    public static AuthorResponse map(AuthorEntity item){
        return AuthorResponse.builder()
                .id(item.getId())
                .firstName(item.getFirstName())
                .lastName(item.getLastName())
                .email(item.getEmail())
                .build();
    }
}
