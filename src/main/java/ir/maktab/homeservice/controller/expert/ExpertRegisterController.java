package ir.maktab.homeservice.controller.expert;

import ir.maktab.homeservice.dto.PersonRegisterDto;
import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.service.expert.ExpertService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;

@Controller
@AllArgsConstructor
@RequestMapping("api/v1/reg-expert")
public class ExpertRegisterController {

    private final ExpertService expertService;

/*    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Expert());

        return "signup_form";
    }*/

    @PostMapping("/process_register")
    public String registerExpert(@RequestBody @Validated PersonRegisterDto personRegisterDto, HttpServletRequest request)
            throws MessagingException, UnsupportedEncodingException {

        Expert temp = PersonRegisterDto.personDtoExpertMapping(personRegisterDto);

        expertService.register(temp, getSiteURL(request));

        return "register_success";
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    @GetMapping("verify")
    public String verifyUser(@Param("code") String code) {
        return expertService.getString(code);
    }


}
