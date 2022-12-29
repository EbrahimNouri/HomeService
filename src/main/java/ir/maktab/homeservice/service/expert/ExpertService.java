package ir.maktab.homeservice.service.expert;

import ir.maktab.homeservice.entity.Expert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface ExpertService {

    void mainRegisterExpert(Expert expert);

    void registerExpert(Expert expert, File file);

    void acceptExpert(Expert expert);

    Expert findById(Long id);

    List<Expert> findByBasicService(Long basicId);

    void changePassword(Expert expert, String password);

    Expert findById(Long id, Path path) throws IOException;

    void SetAveragePoint(Double point, Long expertId);

    void deactivate(Expert expert);

    void save(Expert expert);

    List<Expert> findAll();

    List<Expert> findByFirstName(String firstname);

    List<Expert> findByLastName(String firstname);

    Expert findByEmail(String email);

    List<Expert> findBy(Map<String, String> find);

    void addAvatar(Long expertId, MultipartFile file) throws IOException;

    void delete(Long e);

    Double getScore(Long id);
}
