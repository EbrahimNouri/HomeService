package ir.maktab.homeservice.service.admin;


import ir.maktab.homeservice.entity.Admin;
import ir.maktab.homeservice.repository.admin.AdminRepository;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    private AdminRepository adminRepository;

    private static final Logger LOGGER = LogManager.getLogger(AdminServiceImpl.class);


    @Override
    public void addAdmin(Admin admin) {

        adminRepository.save(admin);

    }

    @Override
    public void changePassword(Admin admin, String password) {

        if (!admin.getPassword().equals(password)
                && admin.getId() != null) {

            admin.setPassword(password);
            adminRepository.save(admin);

        }
    }
}
