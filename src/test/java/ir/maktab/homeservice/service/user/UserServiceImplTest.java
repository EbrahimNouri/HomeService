package ir.maktab.homeservice.service.user;

import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.repository.user.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
//@EnableConfigurationProperties
class UserServiceImplTest {

    private static User user;

    @Autowired
    UserService service;
    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    public static void initialize() {
        user = User.builder().firstname("testname").lastname("testlname").email("test@mail.com")
                .password("qwER1234").credit(0.0).build();
    }

    @BeforeEach
    void addToDatabase(){
        service.registerUser(user);
    }

    @AfterEach
    public void purgeDatabase(){
        userRepository.delete(user);
        user.setId(null);
    }
    @AfterAll
    static void purgeObj(){
        user = null;
    }

    @Test
    void registerUser() {
        assertNotNull(user.getId());
    }

    @Test
    void changePassword() {
        String newPass = "newPassw0rd";
        service.changePassword(user, newPass );
        assertEquals(newPass, Objects
                .requireNonNull(service.findById(user.getId())).getPassword());
    }



//    @Test
//    void passwordPatternTest() {
//        String newPass = "1234QWer";
//        assertThrows(Exception.class, () -> service.changePassword(user, "123"));
//        user.setPassword(newPass);
//        assertEquals(newPass, Objects.requireNonNull(service.findById(user
//                .getId()).orElse(null)).getPassword());
//    }

/*    @Test
    void passwordUnchangedTest() {
        assertThrows(CustomPatternInvalidException.class, () -> service.changePassword(user
                , user.getPassword()), "password not changed");
    }*/

//    @Test
//    void emailPatternTest() {
//        Expert expert1 = Expert.builder().firstname("testName")
//                .lastname("testLastname").email("tesst1emailcom")
//                .password("1234QWer").build();
//        ;
//        assertThrows(ConstraintViolationException.class, () -> service.save(user));
///*        assertThrows(CustomExceptionSave.class
//                , () -> service.registerExpert(expert1, null)
//                ,"expert not saved");*/
//    }

}