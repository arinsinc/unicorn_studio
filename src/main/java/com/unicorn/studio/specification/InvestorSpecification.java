package com.unicorn.studio.specification;

import com.unicorn.studio.entity.Investor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InvestorSpecification implements Specification<Investor> {
    private List<SearchCriteria> searchCriteriaList;

    public InvestorSpecification() {
        this.searchCriteriaList = new ArrayList<>();
    }

    public void add(SearchCriteria criteria) {
        searchCriteriaList.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<Investor> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        List<Predicate> attributes = new ArrayList<>();

        for(SearchCriteria criteria : searchCriteriaList) {
            if (criteria.getKey().equals("location")) {
                predicates.add(builder.like(
                        builder.lower(root.join("city").get("name")),
                        "%" + criteria.getValue().toString().toLowerCase(Locale.ROOT) + "%"
                ));
            } else if (criteria.getKey().equals("fullName") || criteria.getKey().equals("title") || criteria.getKey().equals("profile")) {
                attributes.add(builder.like(
                        builder.lower(root.get(criteria.getKey())),
                        "%" + criteria.getValue().toString().toLowerCase(Locale.ROOT) + "%"
                ));
            }
            else {
                if (criteria.getOperation().equals(SearchOperation.GREATER_THAN)) {
                    predicates.add(builder.greaterThan(
                            root.get(criteria.getKey()), criteria.getValue().toString()
                    ));
                } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN)) {
                    predicates.add(builder.lessThan(
                            root.get(criteria.getKey()), criteria.getValue().toString()
                    ));
                } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
                    predicates.add(builder.greaterThanOrEqualTo(
                            root.get(criteria.getKey()), criteria.getValue().toString()
                    ));
                } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
                    predicates.add(builder.lessThanOrEqualTo(
                            root.get(criteria.getKey()), criteria.getValue().toString()
                    ));
                } else if (criteria.getOperation().equals(SearchOperation.NOT_EQUAL)) {
                    predicates.add(builder.notEqual(
                            root.get(criteria.getKey()), criteria.getValue().toString()
                    ));
                } else if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                    predicates.add(builder.equal(
                            root.get(criteria.getKey()), criteria.getValue().toString()
                    ));
                } else if (criteria.getOperation().equals(SearchOperation.MATCH)) {
                    predicates.add(builder.like(
                            builder.lower(root.get(criteria.getKey())),
                            "%" + criteria.getValue().toString().toLowerCase(Locale.ROOT) + "%"
                    ));
                } else if (criteria.getOperation().equals(SearchOperation.MATCH_END)) {
                    predicates.add(builder.like(
                            builder.lower(root.get(criteria.getKey())),
                            "%" + criteria.getValue().toString().toLowerCase(Locale.ROOT)
                    ));
                } else if (criteria.getOperation().equals(SearchOperation.MATCH_START)) {
                    predicates.add(builder.like(
                            builder.lower(root.get(criteria.getKey())),
                            criteria.getValue().toString().toLowerCase(Locale.ROOT) + "%"
                    ));
                } else if (criteria.getOperation().equals(SearchOperation.IN)) {
                    predicates.add(builder.in(
                            root.get(criteria.getKey())).value(criteria.getValue()
                    ));
                } else if (criteria.getOperation().equals(SearchOperation.NOT_IN)) {
                    predicates.add(builder.not(
                            root.get(criteria.getKey())).in(criteria.getValue()
                    ));
                }
            }
        }
        Predicate attributeResult = builder.or(attributes.toArray(new Predicate[0]));
        Predicate filterResult = builder.and(predicates.toArray(new Predicate[0]));
        if (filterResult.getExpressions().isEmpty()) {
            return attributeResult;
        }
        return builder.and(attributeResult,filterResult);
    }
}
