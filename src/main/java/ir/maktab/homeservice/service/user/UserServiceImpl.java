package ir.maktab.homeservice.service.user;


import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.entity.enums.Role;
import ir.maktab.homeservice.exception.CustomExceptionInvalid;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.exception.CustomExceptionSave;
import ir.maktab.homeservice.repository.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import net.bytebuddy.utility.RandomString;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;


    @Override
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public void register(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        repository.save(user);

        sendVerificationEmail(user, siteURL);
    }

    private void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "homeservice.springboot@hotmail.com";
        String senderName = "home service";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstname() + " " + user.getLastname());
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }

    @Override
    public boolean verify(String verificationCode) {
        User user = repository.findByVerificationCode(verificationCode).orElse(null);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            repository.save(user);

            return true;
        }
    }

    @Override
    public void mainRegisterUser(@Validated User user) {
        if (repository.existsByEmail(user.getEmail()))
            throw new CustomExceptionSave("this email is exist");

        if (repository.existsByUsername(user.getUsername()))
            throw new CustomExceptionSave("this username is exist");

        user.setCredit(0.0);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        repository.save(user);
    }

    @Override
    public void registerUser(@Valid User user) {

        if (user.getEmail() == null && user.getPassword() == null)
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

        log.debug("debug change password user {} to {} ", user, password);

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
        return repository.findAll(mapToSpecification(find));
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }

    private Specification<User> mapToSpecification(Map<String, String> find) {

        List<Specification<User>> specifications = new ArrayList<>();
        for (Map.Entry<String, String> ee
                : find.entrySet()) {
            Specification<User> specification =
                    ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(ee.getKey()), ee.getValue()));
            specifications.add(specification);
        }
        return Specification.allOf(specifications);
    }
}
