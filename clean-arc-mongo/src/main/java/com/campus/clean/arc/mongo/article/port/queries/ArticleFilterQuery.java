package com.campus.clean.arc.mongo.article.port.queries;

import com.campus.clean.arc.domain.article.port.filters.ArticleFilters;
import com.rcore.database.mongo.commons.query.AbstractExampleQuery;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.List;

public class ArticleFilterQuery extends AbstractExampleQuery {
    private final ArticleFilters filters;

    public ArticleFilterQuery(ArticleFilters filters) {
        super(filters);
        this.filters = filters;
    }

    @Override
    public Criteria getCriteria() {
        Criteria finalCondition = new Criteria();
        List<Criteria> current = new ArrayList<>();

        if(filters.getQuery() != null && filters.getQuery().isBlank() == false){
            String[] query = filters.getQuery().trim().split(" ");
            List<Criteria> textSearch = new ArrayList<>();

            for(String q: query){
                textSearch.add(Criteria.where("title").regex(q,"i"));
            }

            current.add(new Criteria().orOperator(textSearch.toArray(new Criteria[textSearch.size()])));
        }

        if(current.size() == 0) return finalCondition;
        if(current.size() == 1) return current.get(0);

        finalCondition.andOperator(current.toArray(new Criteria[current.size()]));
        return finalCondition;
    }
}
