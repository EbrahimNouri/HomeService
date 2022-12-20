package ir.maktab.homeservice.service.expert;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import ir.maktab.homeservice.exception.CustomExceptionUpdate;
import ir.maktab.homeservice.exception.CustomPatternInvalidException;
import ir.maktab.homeservice.repository.expert.ExpertRepository;
import ir.maktab.homeservice.service.expertTypeSerice.ExpertTypeServiceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

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
        if (expert.getId() == null) {
            if (file != null) {

                byte[] avatar = imageConverter(file);
                expert.setAvatar(avatar);

            }
            expert.setExpertTypeServices(null);
            expert.setExpertStatus(ExpertStatus.NEW);
            repository.save(expert);
            log.debug("debug register expert {} ", expert);
        } else {
            log.warn("warn register avatar larger than 300kb or not .jpg");
            throw new CustomPatternInvalidException("this email is invalid");
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
    public Optional<Expert> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void changePassword(@Valid Expert expert, String password) {

        if (!expert.getPassword().equals(password)
                && expert.getId() != null
                && expert.equals(findById(expert.getId()).orElse(null))) {

            expert.setPassword(password);
            repository.save(expert);

            log.debug("debug change password expert {} to {} ", expert, password);
        } else {
            log.warn("old password and new password is same");

            throw new CustomPatternInvalidException("password not changed");
        }

    }

    @Override
    public Optional<Expert> findById(Long id, Path path) {
        Optional<Expert> expert;

        expert = repository.findById(id);

        expert.ifPresent(value -> fileWriter(path, value.getAvatar()));

        return expert;

    }

    @Transactional
    @Override
    public void SetAveragePoint(Double point, Long expertId) {
        repository.setAveragePont(point, expertId);
    }

    private byte[] imageConverter(File file) {
        if (file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] imageByte = fileInputStream.readAllBytes();
                fileInputStream.close();
                return imageByte;
            } catch (IOException e) {
                return null;
            }
        } else
            return null;
    }

    private void fileWriter(Path path, byte[] bytes) {
        try (FileOutputStream fos = new FileOutputStream(path.toFile())) {
            fos.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deactivate(Expert expert){
        repository.deactivate(expert.getId(), ExpertStatus.DEACTIVATE);
    }

    @Override
    public void save(Expert expert){
        repository.save(expert);
    }
}
