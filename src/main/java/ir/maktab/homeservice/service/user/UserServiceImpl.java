package ir.maktab.homeservice.service.user;


import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.entity.enums.Role;
import ir.maktab.homeservice.exception.CustomExceptionInvalid;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.exception.CustomExceptionSave;
import ir.maktab.homeservice.exception.CustomNotChoosingException;
import ir.maktab.homeservice.repository.order.OrderRepository;
import ir.maktab.homeservice.repository.user.UserRepository;
import ir.maktab.homeservice.service.order.OrderService;
import ir.maktab.homeservice.util.ApplicationContextProvider;
import ir.maktab.homeservice.util.EmailSenderUtil;
import ir.maktab.homeservice.util.SpecificationUtil;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final OrderRepository orderRepository;
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SpecificationUtil specificationUtil;
    private final EmailSenderUtil emailSenderUtil;

    private final ApplicationContextProvider applicationContext;


    @Override
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public void register(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {

        if (repository.existsByEmail(user.getEmail()))
            throw new CustomExceptionSave("this email is exist");

        if (repository.existsByUsername(user.getUsername()))
            throw new CustomExceptionSave("this username is exist");

        user.setCredit(0.0);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);

        int randomCode = (int) (Math.random() * (99999 - 10000 + 1) + 10000);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        repository.save(user);

        emailSenderUtil.sendVerificationEmail(user, siteURL);
    }

    @Override
    public boolean verify(Integer verificationCode) {
        User user = repository.findByVerificationCode(verificationCode)
                .orElseThrow(() -> new CustomNotChoosingException("this code is invalid"));

        if (user.getVerificationCode() == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            repository.save(user);

            return true;
        }
    }

    @Override
    public void registerUser(@Valid User user) {

        if (user.getEmail() == null
                && user.getPassword() == null
                && user.getUsername() == null)
            throw new CustomExceptionSave("user not saved");

        repository.save(user);

        log.debug("debug user service iml {} ", user);

    }

    @Override
    public void changePassword(@Valid User user, String password) {

        if (user.getPassword().equals(passwordEncoder.encode(password)))
            throw new CustomExceptionInvalid("password not changed");


        if (user.getId() == null)
            throw new CustomExceptionInvalid("user id is null");

        user.setPassword(passwordEncoder.encode(password));
        repository.save(user);

    }

    @Override
    public User findById(Long id) {

        return repository.findUserByIdCustom(id).orElseThrow
                (() -> new RuntimeException("user not found"));

    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public List<User> findByFirstname(String firstname) {
        return repository.findByFirstname(firstname);
    }

    @Override
    public List<User> findByLastname(String lastname) {
        return repository.findByLastname(lastname);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() ->
                new CustomExceptionNotFind("user not found"));
    }

    @Override
    public List<User> findBy(Map<String, String> find) {
        return repository.findAll(specificationUtil.mapToSpecification(find));
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

    @Override
    public User userDetail(User user) {
        List<Order> orders = applicationContext
                .getContext()
                .getBean(OrderService.class)
                .findByUserId(user.getId());
        user.setOrders(orders);
        return user;
    }

    @Override
    public List<User> userFindBySpecification(Map<String, String> map) {
        return repository.findAll(specificationUtil.userSpecification(map));
    }

    @Override
    public List<User> userSpecification(Map<String, String> map){
        return repository.findAll(specificationUtil.userSpecification(map));
    }

    @Override
    public boolean existById(Long id) {
        return repository.existsById(id);
    }
}
