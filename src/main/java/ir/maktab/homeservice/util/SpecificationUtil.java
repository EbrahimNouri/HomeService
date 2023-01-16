package ir.maktab.homeservice.util;

import ir.maktab.homeservice.entity.*;
import ir.maktab.homeservice.entity.enums.OrderType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SpecificationUtil {

    public <E> Specification<E> mapToSpecification(Map<String, String> find) {

        List<Specification<E>> specifications = new ArrayList<>();
        for (Map.Entry<String, String> ee
                : find.entrySet()) {
            Specification<E> specification =
                    ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(ee.getKey()), ee.getValue()));
            specifications.add(specification);
        }
        return Specification.allOf(specifications);
    }

    public Specification<Order> OrderSpecification(Map<String, String> map) {

        Specification<Order> orderSpecification = Specification.where(null);



        for (Map.Entry<String, String> entry : map.entrySet()) {

            Specification<Order> orderSpecificationTemp
                    = (root, query, criteriaBuilder) -> switch (entry.getKey().toLowerCase()) {

                case "id", "orderid" -> criteriaBuilder.equal(root.get(Order_.id), Long.valueOf(entry.getValue()));

                case "from" -> criteriaBuilder.greaterThanOrEqualTo(root.get(Order_.startOfWork)
                        , LocalDate.parse(entry.getValue()));

                case "to" -> criteriaBuilder.lessThanOrEqualTo(root.get(Order_.startOfWork)
                        , LocalDate.parse(entry.getValue()));

                case "pricegte" -> criteriaBuilder.greaterThanOrEqualTo(root.join(Order_.offers)
                        .get(Offer_.suggestedPrice), Double.valueOf(entry.getValue()));

                case "pricelte" -> criteriaBuilder.lessThanOrEqualTo(root.join(Order_.offers)
                        .get(Offer_.suggestedPrice), Double.valueOf(entry.getValue()));

                case "typeservice" -> criteriaBuilder.equal(root.get(TypeService_.id), entry.getValue().toLowerCase());

                case "basicservice" ->
                        criteriaBuilder.equal(root.join(Order_.typeService).join(TypeService_.basicService)
                                .get(BasicService_.id), Long.valueOf(entry.getValue()));

                case "user" -> criteriaBuilder.equal(root.join(Order_.user).get(User_.id),
                        Long.valueOf(entry.getValue()));

                case "description" -> criteriaBuilder.like(root.get(Order_.description), entry.getValue());

                case "ordertype" -> criteriaBuilder.equal(root.get(Order_.orderType),
                        OrderType.valueOf(entry.getValue()));

                case "delay" -> criteriaBuilder.isTrue(root.get(Order_.delayEndWorkHours).isNotNull());

                default -> criteriaBuilder.and();

            };

            orderSpecification = orderSpecification.and(orderSpecificationTemp);
        }
        return orderSpecification;
    }

    public Specification<Offer> expertSpecification(Map<String, String> map) {

        Specification<Offer> expertSpecification = Specification.where(null);

        for (Map.Entry<String, String> entry : map.entrySet()) {

            Specification<Offer> specification
                    = (root, query, criteriaBuilder) -> switch (entry.getKey().toLowerCase()) {

                case "id" -> criteriaBuilder.equal(root.get(Offer_.id), Long.valueOf(entry.getValue()));

                case "from" -> criteriaBuilder.greaterThanOrEqualTo(root.join(Offer_.expert).get(Expert_.signupDateTime)
                        , LocalDateTime.parse(entry.getValue()));

                case "to" -> criteriaBuilder.lessThanOrEqualTo(root.join(Offer_.expert).get(Expert_.signupDateTime)
                        , LocalDateTime.parse(entry.getValue()));

                case "offer" -> criteriaBuilder.equal(root.join(Offer_.expert).get(Expert_.id),
                        Long.valueOf(entry.getValue()));


                case "countOrderDone" -> criteriaBuilder.equal(root.get(Offer_.choose),
                        Boolean.valueOf(entry.getValue()));

                case "ordertype" ->
                        criteriaBuilder.equal(root.join(Offer_.order).get(Order_.orderType)
                                , OrderType.valueOf(entry.getValue()));

                default -> criteriaBuilder.and();

            };

            expertSpecification = specification.and(expertSpecification);
        }
        return expertSpecification;
    }

    public Specification<User> userSpecification(Map<String, String> map) {

        Specification<User> userSpecification = Specification.where(null);

        for (Map.Entry<String, String> entry : map.entrySet()) {

            Specification<User> specification
                    = (root, query, criteriaBuilder) -> switch (entry.getKey().toLowerCase()) {

                case "id" -> criteriaBuilder.equal(root.get(Offer_.id), Long.valueOf(entry.getValue()));

                case "from" -> criteriaBuilder.greaterThanOrEqualTo(root.get(User_.signupDateTime)
                        , LocalDateTime.parse(entry.getValue()));

                case "to" -> criteriaBuilder.lessThanOrEqualTo(root.get(User_.signupDateTime)
                        , LocalDateTime.parse(entry.getValue()));


                case "countOrderDone" -> criteriaBuilder.equal(root.join(User_.orders)
                                .join(Order_.offers).get(Offer_.choose),
                        Boolean.valueOf(entry.getValue()));

                case "ordertype" ->
                        criteriaBuilder.equal(root.join(User_.orders).get(Order_.orderType)
                                , OrderType.valueOf(entry.getValue()));

                default -> criteriaBuilder.and();

            };

            userSpecification = specification.and(userSpecification);
        }
        return userSpecification;
    }
}
