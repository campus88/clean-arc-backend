package com.campus.clean.arc.mongo.author.port.queries;

import com.campus.clean.arc.domain.author.port.filters.AuthorFilters;
import com.rcore.database.mongo.commons.query.AbstractExampleQuery;
import com.rcore.domain.commons.port.dto.SearchFilters;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

public class AuthorFiltersQuery extends AbstractExampleQuery {
    private final AuthorFilters filters;

    public AuthorFiltersQuery(AuthorFilters filters) {
        super(filters);
        this.filters = filters;
    }

    @Override
    public Criteria getCriteria() {
        Criteria finalCondition = new Criteria();

        List<Criteria> currentConditions = new ArrayList<>();

        if (filters.getCredentialId() != null) {
            currentConditions.add(Criteria.where("credentialId").is(filters.getCredentialId()));
        }
        if (filters.getEmail() != null) {
            currentConditions.add(Criteria.where("email").is(filters.getEmail()));
        }

        if (filters.getQuery() != null && filters.getQuery().isBlank() == false) {
            String[] query = filters.getQuery().trim().split(" ");
            List<Criteria> textSearch = new ArrayList<>();
            for (String q : query) {
                textSearch.add(Criteria.where("firstName").regex(q, "i"));
                textSearch.add(Criteria.where("lastName").regex(q, "i"));
            }
            currentConditions.add(
                    new Criteria().orOperator(
                            textSearch.toArray(
                                    new Criteria[textSearch.size()]
                            )));
        }

        if(currentConditions.size() == 0) return finalCondition;
        if(currentConditions.size() == 1) return currentConditions.get(0);

        finalCondition.andOperator(currentConditions.toArray(
                new Criteria[currentConditions.size()]
        ));

        return finalCondition;
    }
}
