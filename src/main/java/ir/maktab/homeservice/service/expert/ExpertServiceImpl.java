package ir.maktab.homeservice.service.expert;


import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import ir.maktab.homeservice.repository.expert.ExpertRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
@AllArgsConstructor
public class ExpertServiceImpl implements ExpertService {
    private ExpertRepository repository;

    @Transactional
    @Override
    public void registerExpert(Expert expert, File file) {
        if (expert.getId() == null) {
            byte[] avatar = imageConverter(file);
            expert.setAvatar(avatar);
            expert.setExpertTypeServices(null);
            expert.setExpertStatus(ExpertStatus.NEW);
            repository.save(expert);
        }// TODO: 12/9/2022 AD

    }

    @Override
    public void acceptExpert(Expert expert) {

        if (expert.getId() != null) {
            expert.setExpertStatus(ExpertStatus.CONFIRMED);
            repository.save(expert);
        }// TODO: 12/9/2022 AD
    }

    @Override
    public void changePassword(Expert expert, String password) {
        if (!expert.getPassword().equals(password)
                && expert.getId() != null) {
            repository.save(expert);
        }
    }

    private byte[] imageConverter(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] imageByte = fileInputStream.readAllBytes();
            fileInputStream.close();
            return imageByte;
        } catch (IOException e) {
            return null;
        }
    }
}
