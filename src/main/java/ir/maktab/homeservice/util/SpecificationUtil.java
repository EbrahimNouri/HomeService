package ir.maktab.homeservice.util;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

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
}
