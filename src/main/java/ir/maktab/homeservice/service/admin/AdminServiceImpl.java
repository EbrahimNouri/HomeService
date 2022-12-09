package ir.maktab.homeservice.service.admin;


import ir.maktab.homeservice.entity.Admin;
import ir.maktab.homeservice.repository.admin.AdminRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;


    @Override
    public void addAdmin(Admin admin) {
        try {

            adminRepository.save(admin);

            log.debug("admin created {} ", admin);

        } catch (Exception e) {
            log.error("error admin create {} ", admin, e);
        }
    }

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
}
