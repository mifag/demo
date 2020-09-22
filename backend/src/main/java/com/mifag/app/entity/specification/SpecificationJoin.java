package com.mifag.app.entity.specification;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;

/**
 * @author <a href='mailto:mifag92@rambler.ru'>mifag</a>
 * @version 22.09.2020
 */
public class SpecificationJoin {

    /**
     * Get join.
     *
     * @param from        - from.
     * @param joinOnField - join on field.
     * @return join.
     */
    public static Join<?, ?> getJoin(From<?, ?> from, String joinOnField) {
        for (Join<?, ?> join : from.getJoins()) {
            if (join.getAttribute().getName().equals(joinOnField)) {
                return join;
            }
        }
        return from.join(joinOnField);
    }
}