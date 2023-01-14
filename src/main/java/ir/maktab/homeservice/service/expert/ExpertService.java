package ir.maktab.homeservice.service.expert;

import ir.maktab.homeservice.entity.base.Person;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface ExpertService {

    void register(Person expert, String siteURL)
            throws MessagingException, UnsupportedEncodingException;

    boolean verify(Integer verificationCode);

    // TODO: 1/8/2023 AD controller
    Person expertDetail(Long expertId);

    void registerExpert(Person expert, File file);

    void acceptExpert(Person expert);

    Person findById(Long id);

    List<Person> findByBasicService(Long basicId);

    void changePassword(Person expert, String password);

    Person findById(Long id, Path path) throws IOException;

    void SetAveragePoint(Double point, Long expertId);

    void deactivate(Person expert);

    void save(Person expert);

    List<Person> findAll();

    List<Person> findByFirstName(String firstname);

    List<Person> findByLastName(String firstname);

    Person findByEmail(String email);

    List<Person> findBy(Map<String, String> find);

    void addAvatar(Long expertId, MultipartFile file) throws IOException;

    void delete(Long e);

    Double getScore(Long id);
}
