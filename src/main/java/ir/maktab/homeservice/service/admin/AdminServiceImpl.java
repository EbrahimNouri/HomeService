package ir.maktab.homeservice.service.admin;


import ir.maktab.homeservice.entity.Admin;
import ir.maktab.homeservice.repository.admin.AdminRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;

@Transactional
    @Override
    public void addAdmin(Admin admin) {
        try {

            adminRepository.save(admin);

            log.debug("admin created {} ", admin);

        } catch (Exception e) {
            log.error("error admin create {} ", admin, e);
        }
    }
@Transactional
    @Override
    public void changePassword(Admin admin, String password) {
        try {


            if (!admin.getPassword().equals(password)
                    && admin.getId() != null) {

                admin.setPassword(password);
                adminRepository.save(admin);

                log.debug("admin created {} ", admin);
            }else {
                log.warn("warn new password same old password");
            }
        }catch (Exception e){
            log.error("error admin change password {} ", admin, e);
        }
    }

    @Override
    public Optional<Admin> findById(Long adminId){
        Optional<Admin> admin = Optional.empty();
        try {
            admin = adminRepository.findById(adminId);

            log.debug("debug found admin by id {} ", adminId);
        }catch (Exception e){
            log.debug("debug found admin by id {} ", adminId);
        }
        return admin;
    }
}
