package com.campus.clean.arc.domain.author.usecases;

import com.campus.clean.arc.domain.author.entity.AuthorEntity;
import com.campus.clean.arc.domain.author.exceptions.AuthorAlreadyExistException;
import com.campus.clean.arc.domain.author.port.AuthorIdGenerator;
import com.campus.clean.arc.domain.author.port.AuthorRepository;
import com.rcore.domain.commons.usecase.UseCase;
import com.rcore.domain.commons.usecase.model.SingletonEntityOutputValues;
import lombok.*;
import ru.foodtechlab.lib.auth.integration.core.credential.CredentialServiceFacade;
import ru.foodtechlab.lib.auth.integration.core.role.RoleServiceFacade;
import ru.foodtechlab.lib.auth.integration.core.roleAccess.RoleAccessServiceFacade;
import ru.foodtechlab.lib.auth.service.facade.credential.dto.requests.CreateCredentialRequest;
import ru.foodtechlab.lib.auth.service.facade.credential.dto.responses.CredentialResponse;
import ru.foodtechlab.lib.auth.service.facade.role.dto.requests.CreateRoleRequest;
import ru.foodtechlab.lib.auth.service.facade.role.dto.responses.RoleResponse;
import ru.foodtechlab.lib.auth.service.facade.roleAccess.dto.requests.CreateRoleAccessRequest;
import ru.foodtechlab.lib.auth.service.facade.roleAccess.dto.requests.FindRoleAccessWithFiltersRequest;
import ru.foodtechlab.lib.auth.service.facade.roleAccess.dto.responses.RoleAccessResponse;

import javax.security.auth.login.CredentialNotFoundException;
import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
public class CreateAuthorUserCase extends UseCase<CreateAuthorUserCase.InputValues, SingletonEntityOutputValues<AuthorEntity>> {

    protected final AuthorRepository authorRepository;
    protected final AuthorIdGenerator authorIdGenerator;
    protected final CredentialServiceFacade credentialServiceFacade;
    protected final RoleServiceFacade roleServiceFacade;
    protected final RoleAccessServiceFacade roleAccessServiceFacade;

    private RoleResponse buildRole() {
        String roleCode = "AUTHOR";
        RoleResponse roleResponse = null;

        try {
            roleResponse = roleServiceFacade.findByCode(roleCode).orElse(null);
        } catch (Exception ignore) {
        }

        if (roleResponse == null) {
            FindRoleAccessWithFiltersRequest req = FindRoleAccessWithFiltersRequest.builder().limit(1000l).build();
            List<RoleAccessResponse> accessResponses = roleAccessServiceFacade.find(req).getItems();
            RoleAccessResponse roleAccessResponse = null;

            for (RoleAccessResponse access : accessResponses) {
                if (access.getIsDeleted() == false
                        && access.getMethod().equals(RoleAccessResponse.Method.ANY)
                        && access.getServiceName().equals("*") &&
                        access.getRequestPathPattern().equals("/**")) {
                    roleAccessResponse = access;
                }
            }

            if (roleAccessResponse == null) {
                CreateRoleAccessRequest createRoleAccessRequest = CreateRoleAccessRequest
                        .builder()
                        .serviceName("*")
                        .requestPathPattern("/**")
                        .method(RoleAccessResponse.Method.ANY)
                        .build();
                roleAccessResponse = roleAccessServiceFacade.create(createRoleAccessRequest);
            }

            CreateRoleRequest createRoleRequest = CreateRoleRequest.builder()
                    .name("Author Role")
                    .code(roleCode)
                    .accessIds(List.of(roleAccessResponse.getId()))
                    .isRegistrationAllowed(true)
                    .build();

            roleResponse = roleServiceFacade.create(createRoleRequest);
        }

        return roleResponse;
    }

    @Override
    public SingletonEntityOutputValues<AuthorEntity> execute(InputValues inputValues) {
        CredentialResponse credentialResponse;
        try {
            credentialResponse = credentialServiceFacade.findByEmail(inputValues.email).orElse(null);
            if (credentialResponse != null) throw new AuthorAlreadyExistException();
        } catch (CredentialNotFoundException ignore) {
        }

        RoleResponse roleResponse = buildRole();
        CreateCredentialRequest.Role role = new CreateCredentialRequest.Role(
                roleResponse.getId(),
                roleResponse.getCode(),
                roleResponse.getIsDeleted()
        );

        CreateCredentialRequest createCredentialRequest = CreateCredentialRequest.builder()
                .username(inputValues.getEmail())
                .password(inputValues.getPassword())
                .phoneNumber(null)
                .email(new CredentialResponse.Email(inputValues.getEmail(), false))
                .roles(List.of(role))
                .isBlocked(false)
                .confirmationCodeDestinationType(CreateCredentialRequest.ConfirmationCodeDestinationType.EMAIL)
                .personalConfirmationCode(null)
                .confirmationCodeType(CreateCredentialRequest.Type.ONE_TIME)
                .build();

        credentialResponse = credentialServiceFacade.create(createCredentialRequest);

        AuthorEntity authorEntity = AuthorEntity.builder()
                .id(authorIdGenerator.generate())
                .email(inputValues.email)
                .firstName(inputValues.firstName)
                .lastName(inputValues.lastName)
                .credentialId(credentialResponse.getId())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        authorEntity = authorRepository.save(authorEntity);
        return SingletonEntityOutputValues.of(authorEntity);
    }

    @AllArgsConstructor(staticName = "of")
    @NoArgsConstructor
    @Builder
    @Data
    public static class InputValues implements UseCase.InputValues {
        private String email;
        private String password;
        private String firstName;
        private String lastName;
    }


}
