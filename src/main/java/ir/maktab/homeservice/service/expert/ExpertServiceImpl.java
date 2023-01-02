package ir.maktab.homeservice.service.expert;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import ir.maktab.homeservice.entity.enums.Role;
import ir.maktab.homeservice.exception.CustomExceptionInvalid;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.exception.CustomExceptionSave;
import ir.maktab.homeservice.exception.CustomExceptionUpdate;
import ir.maktab.homeservice.repository.expert.ExpertRepository;
import ir.maktab.homeservice.service.expertTypeSerice.ExpertTypeServiceService;
import ir.maktab.homeservice.util.FileUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Log4j2
@AllArgsConstructor
public class ExpertServiceImpl implements ExpertService {
    private final ExpertTypeServiceService expertTypeServiceService;
    private ExpertRepository repository;
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void mainRegisterExpert(@Validated Expert expert) {
        if (repository.existsByEmail(expert.getEmail()))
            throw new CustomExceptionSave("this email is exist");

        if (repository.existsByUsername(expert.getUsername()))
            throw new CustomExceptionSave("this username is exist");

        expert.setExpertStatus(ExpertStatus.NEW);
        expert.setPassword(passwordEncoder.encode(expert.getPassword()));
        expert.setRole(Role.ROLE_EXPERT);
        expert.setAverageScore(0.0);
        expert.setCredit(0.0);

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
    public List<Expert> findByBasicService(Long basicId) {
        return findAll().stream().filter
                (expert -> expert.getExpertTypeServices()
                        .get(0).getTypeService().getBasicService()
                        .getId().equals(basicId)).toList();
    }

    @Override
    public void changePassword(@Valid Expert expert, String password) {


        if (expert.getPassword().equals(passwordEncoder.encode(password)))
            throw new CustomExceptionInvalid("password not changed");


        if (expert.getId() == null)
            throw new CustomExceptionInvalid("expert id is null");


        expert.setPassword(passwordEncoder.encode(password));
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
        for (Map.Entry<String, String> ee : find.entrySet()) {
            Specification<Expert> specification =
                    ((root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get(ee.getKey()), ee.getValue()));
            specifications.add(specification);
        }
        return Specification.allOf(specifications);
    }

    @Override
    public void addAvatar(Long expertId, MultipartFile file) throws IOException {

        Expert expert = findById(expertId);
        final int AVATAR_SIZE = 307200;  // 300 * 1024 = 300kb

        if (file.getSize() < AVATAR_SIZE
                && Objects.equals(file.getContentType()
                , "image/jpeg")) {

            byte[] avatar = file.getBytes();
            expert.setAvatar(avatar);
            repository.save(expert);

        } else
            throw new CustomExceptionInvalid("image file is invalid");


    }

    @Override
    public void delete(Long e) {
        repository.deleteById(e);
    }

    @Override
    public Double getScore(Long expertId) {
        return findById(expertId).getAverageScore();
    }
}
