package com.campus.clean.arc.domain.author.config;


import com.campus.clean.arc.domain.author.port.AuthorIdGenerator;
import com.campus.clean.arc.domain.author.port.AuthorRepository;
import com.campus.clean.arc.domain.author.usecases.*;
import lombok.Getter;
import lombok.experimental.Accessors;
import ru.foodtechlab.lib.auth.integration.core.credential.CredentialServiceFacade;
import ru.foodtechlab.lib.auth.integration.core.role.RoleServiceFacade;
import ru.foodtechlab.lib.auth.integration.core.roleAccess.RoleAccessServiceFacade;

@Accessors(fluent = true)
@Getter
public class AuthorConfig {
    private final ChangePasswordUseCase changePasswordUseCase;
    private final CreateAuthorUserCase createAuthorUserCase;
    private final DeleteAuthorUseCase deleteAuthorUseCase;
    private final FindAuthorByCredentialIdUserCase findAuthorByCredentialIdUserCase;
    private final FindAuthorByIdUseCase findAuthorByIdUseCase;
    private final FindAuthorWithFiltersUseCase findAuthorWithFiltersUseCase;
    private final UpdateAuthorUseCase updateAuthorUseCase;

    public AuthorConfig(
            AuthorRepository authorRepository,
            AuthorIdGenerator authorIdGenerator,
            CredentialServiceFacade credentialServiceFacade,
            RoleServiceFacade roleServiceFacade,
            RoleAccessServiceFacade roleAccessServiceFacade
    ){
        this.changePasswordUseCase = new ChangePasswordUseCase(authorRepository, credentialServiceFacade);
        this.createAuthorUserCase = new CreateAuthorUserCase(
                authorRepository,
                authorIdGenerator,
                credentialServiceFacade,
                roleServiceFacade,
                roleAccessServiceFacade
                );
        this.deleteAuthorUseCase = new DeleteAuthorUseCase(authorRepository);
        this.findAuthorByCredentialIdUserCase = new FindAuthorByCredentialIdUserCase(authorRepository);
        this.findAuthorByIdUseCase = new FindAuthorByIdUseCase(authorRepository);
        this.findAuthorWithFiltersUseCase = new FindAuthorWithFiltersUseCase(authorRepository);
        this.updateAuthorUseCase = new UpdateAuthorUseCase(authorRepository);
    }
}
