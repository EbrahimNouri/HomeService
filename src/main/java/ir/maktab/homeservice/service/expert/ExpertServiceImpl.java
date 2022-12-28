package ir.maktab.homeservice.service.expert;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import ir.maktab.homeservice.exception.CustomExceptionInvalid;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.exception.CustomExceptionUpdate;
import ir.maktab.homeservice.repository.expert.ExpertRepository;
import ir.maktab.homeservice.service.expertTypeSerice.ExpertTypeServiceService;
import ir.maktab.homeservice.util.FileUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@AllArgsConstructor
public class ExpertServiceImpl implements ExpertService {
    private final ExpertTypeServiceService expertTypeServiceService;
    private ExpertRepository repository;

    @Override
    public void mainRegisterExpert(@Valid Expert expert) {

        expert.setExpertStatus(ExpertStatus.NEW);
        repository.save(expert);

    }


    @Transactional
    @Override
    public void registerExpert(@Valid Expert expert, File file) {
        if (expert.getId() == null
                && file.length() / 1024 < 300
                && file.getName().endsWith(".jpg")) {

            byte[] avatar = FileUtil.imageConverter(file);
            expert.setAvatar(avatar);

            expert.setExpertTypeServices(null);
            expert.setExpertStatus(ExpertStatus.NEW);
            repository.save(expert);

            log.debug("debug register expert {} ", expert);
        } else {
            log.warn("warn register avatar larger than 300kb or not .jpg");
            throw new CustomExceptionInvalid("this email is invalid");
        }
    }

    @Override
    public void acceptExpert(Expert expert) {

        if (expert.getId() != null) {

            expert.setExpertStatus(ExpertStatus.CONFIRMED);
            repository.save(expert);

            log.debug("debug accept expert {} ", expert);
        } else {
            log.error("warn expert is null {} ", expert);
            throw new CustomExceptionUpdate("expert not accepted");
        }

    }

    @Override
    public Expert findById(Long id) {
        return repository.findByIdCustom(id).orElseThrow(()
                -> new CustomExceptionNotFind("expert not found"));
    }

    @Override
    public void changePassword(@Valid Expert expert, String password) {


        if (expert.getPassword().equals(password))
            throw new CustomExceptionInvalid("password not changed");


        if (expert.getId() == null)
            throw new CustomExceptionInvalid("expert id is null");


        expert.setPassword(password);
        repository.save(expert);

        log.debug("debug change password expert {} to {} ", expert, password);


    }

    @Override
    public Expert findById(Long id, Path path) throws IOException {

        Expert expert;

        expert = repository.findByIdCustom(id).orElseThrow(() -> new CustomExceptionNotFind("expert not found"));
        FileUtil.fileWriter(path, expert.getAvatar());

        return expert;

    }

    @Transactional
    @Override
    public void SetAveragePoint(Double point, Long expertId) {


        repository.setAveragePont(point, expertId);


    }

    @Override
    public void deactivate(Expert expert) {
        repository.deactivate(expert.getId(), ExpertStatus.DEACTIVATE);
    }

    @Override
    public void save(Expert expert) {
        repository.save(expert);
    }

    @Override
    public List<Expert> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Expert> findByFirstName(String firstname) {
        return repository.findByFirstname(firstname);
    }

    @Override
    public List<Expert> findByLastName(String lastname) {
        return repository.findByLastname(lastname);
    }


    @Override
    public Expert findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() ->
                new CustomExceptionNotFind("expert not found")
        );
    }

    @Override
    public List<Expert> findBy(Map<String, String> find) {
        return repository.findAll(mapToSpecification(find));
    }

    private Specification<Expert> mapToSpecification(Map<String, String> find) {

        List<Specification<Expert>> specifications = new ArrayList<>();
        for (Map.Entry<String, String> ee
                : find.entrySet()) {
            Specification<Expert> specification =
                    ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(ee.getKey()), ee.getValue()));
            specifications.add(specification);
        }
        return Specification.allOf(specifications);
    }

    @Override
    public void addAvatar(Long expertId, File file) {
        Expert expert = repository.findByIdCustom(expertId).orElseThrow
                ((() -> new CustomExceptionNotFind("expert not found")));

        if (file.length() / 1024 < 300
                && !file.getName().endsWith(".jpg")) {
            throw new CustomExceptionInvalid("image file is invalid");
        }
        byte[] avatar = FileUtil.imageConverter(file);
        expert.setAvatar(avatar);
        repository.save(expert);
    }

    @Override
    public void delete(Long e) {
        repository.deleteById(e);
    }
}
