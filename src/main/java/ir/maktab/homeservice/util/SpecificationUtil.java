package ir.maktab.homeservice.util;

import ir.maktab.homeservice.entity.*;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SpecificationUtil<E> {

    public Specification<E> mapToSpecification(Map<String, String> find) {

        List<Specification<E>> specifications = new ArrayList<>();
        for (Map.Entry<String, String> ee
                : find.entrySet()) {
            Specification<E> specification =
                    ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(ee.getKey()), ee.getValue()));
            specifications.add(specification);
        }
        return Specification.allOf(specifications);
    }

    static Specification<Order> hasDate(LocalDateTime start, LocalDateTime end) {
        return (orderRoot, criteriaQuery, criteriaBuilder) -> {
            Join<Order, Offer> join = orderRoot.join(Order_.OFFERS);
            return criteriaBuilder.and(criteriaBuilder.greaterThanOrEqualTo
                    (join.get(Offer_.START_DATE), start), criteriaBuilder
                    .lessThanOrEqualTo(join.get(Offer_.END_DATE), end));
        };
    }

    static Specification<Order> hasTypeService(Long id) {
        return (orderRoot, criteriaQuery, criteriaBuilder) ->

                criteriaBuilder.equal(orderRoot.get(TypeService_.id), id);

    }


    public Specification<Order> OrderSpecification(Map<String, String> map) {

        Specification<Order> orderSpecification = Specification.where(null);

        if (map.containsKey("from")
        && map.containsKey("to")){

            orderSpecification = orderSpecification.and(hasDate
                    (LocalDateTime.parse(map.get("from")),
                            LocalDateTime.parse(map.get("to"))));

            map.remove("from");
            map.remove("to");

        }

        if (map.containsKey("from")) {

            orderSpecification = orderSpecification.and((root, query, criteriaBuilder)
                    -> criteriaBuilder.greaterThanOrEqualTo(root.get(Order_.startOfWork)
                    , LocalDate.parse(map.get("from"))));

            map.remove("from");

        }

        if (map.containsKey("to")){

            orderSpecification = orderSpecification.and((root, query, criteriaBuilder)
                    -> criteriaBuilder.lessThanOrEqualTo(root.get(Order_.startOfWork)
                    , LocalDate.parse(map.get("to"))));

            map.remove("to");

        }

        if (map.containsKey("orderid")){

            orderSpecification =
                    orderSpecification.and(hasTypeService(Long.valueOf(map.get("orderid"))));

            map.remove("orderid");

        }


        return (Specification<Order>) mapToSpecification(map);
    }

// TODO: 1/7/2023 AD  

}
