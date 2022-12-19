package ir.maktab.homeservice.service.expert;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.exception.CustomExceptionSave;
import ir.maktab.homeservice.exception.CustomPatternInvalidException;
import ir.maktab.homeservice.repository.expert.ExpertRepository;
import ir.maktab.homeservice.repository.expertTypeService.ExpertTypeServiceRepository;
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
    private final ExpertTypeServiceRepository expertTypeServiceRepository;
    private ExpertRepository repository;

    @Override
    public void mainRegisterExpert(Expert expert) {

        expert.setExpertStatus(ExpertStatus.NEW);
        repository.save(expert);

    }


    @Transactional
    @Override
    public void registerExpert(Expert expert, File file) {
        try {
            if (expert.getId() == null) {
                byte[] avatar = imageConverter(file);
                expert.setAvatar(avatar);
                expert.setExpertTypeServices(null);
                expert.setExpertStatus(ExpertStatus.NEW);
                repository.save(expert);
                log.debug("debug register expert {} ", expert);
            } else
                log.warn("warn register avatar larger than 300kb or not .jpg");
        } catch (Exception e) {
            log.error("error register expert {} ", expert, e);
            throw new CustomPatternInvalidException("this email is invalid");
        }
    }

    @Override
    public void acceptExpert(Expert expert) {

        try {
            if (expert.getId() != null) {
                expert.setExpertStatus(ExpertStatus.CONFIRMED);
                repository.save(expert);
                log.debug("debug accept expert {} ", expert);
            } else
                log.error("warn expert is null {} ", expert);
        } catch (Exception e) {
            log.error("error accept expert {} ", expert, e);
        }
    }

    @Override
    public Optional<Expert> findById(Long id) {
        Optional<Expert> expert = Optional.empty();
        try {
            expert = repository.findById(id);
        } catch (Exception e) {

            throw e;
            // TODO: 12/11/2022 AD  
        }
        return expert;
    }

    @Override
    public void changePassword(Expert expert, String password) {
        try {
            if (!expert.getPassword().equals(password)
                    && expert.getId() != null
                    && expert.equals(findById(expert.getId()).orElse(null))) {

                expert.setPassword(password);
                repository.save(expert);

                log.debug("debug change password expert {} to {} ", expert, password);
            } else {
                log.warn("old password and new password is same");

                throw new CustomExceptionSave("password not changed");
            }
        } catch (Exception e) {
            log.error("error change password expert {} to {}", expert, password, e);
//            throw e;
            throw new CustomPatternInvalidException("invalid pattern");
        }
    }

    @Override
    public Optional<Expert> findById(Long id, Path path) {
        Optional<Expert> expert = null;
        try {
            expert = repository.findById(id);

            fileWriter(path, expert.orElseThrow(
                    () -> new CustomExceptionNotFind("expert not have image"))
                    .getAvatar());
        } catch (Exception e) {
          throw e;
        }
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

}
