package ir.maktab.homeservice.service.admin;


import ir.maktab.homeservice.entity.Admin;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.exception.CustomExceptionUpdate;
import ir.maktab.homeservice.repository.admin.AdminRepository;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.user.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final ExpertService expertService;
    private final UserService userService;

    @Transactional
    @Override
    public void addAdmin(Admin admin) {

        adminRepository.save(admin);

        log.debug("admin created {} ", admin);
    }

    @Transactional
    @Override
    public void changePassword(@Valid Admin admin, String password) {

        if (!admin.getPassword().equals(password)
                && admin.getId() != null) {

            admin.setPassword(password);
            adminRepository.save(admin);

            log.debug("admin created {} ", admin);
        } else {
            log.warn("warn new password same old password");
            throw new CustomExceptionUpdate("admin password not changed");
        }
    }

    @Override
    public Admin findById(Long adminId) {

        return adminRepository.findById(adminId).orElseThrow
                (() -> new CustomExceptionNotFind("admin not found"));

    }


}