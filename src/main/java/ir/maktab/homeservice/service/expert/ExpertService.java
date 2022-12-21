package ir.maktab.homeservice.service.expert;

import ir.maktab.homeservice.entity.Expert;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;

public interface ExpertService {

    void mainRegisterExpert(Expert expert);

    void registerExpert(Expert expert, File file);

    void acceptExpert(Expert expert);

    Optional<Expert> findById(Long id);

    void changePassword(Expert expert, String password);

    Optional<Expert> findById(Long id, Path path);

    void SetAveragePoint(Double point, Long expertId);

    void deactivate(Expert expert);

    void save(Expert expert);
}
