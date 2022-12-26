package ir.maktab.homeservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@EnableWebMvc
@RequestMapping("/text")
public class Viewer {

    @RequestMapping()
    public ModelAndView index (/*@RequestParam ("g-recaptcha-response") String captcha*/ ){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");




/*
        https://www.google.com/recaptcha/api/siteverify?secret=6LdIt6gjAAAAAFqVRLrryJ4CzFMoPWKw3GAUzDhn
*/
        return modelAndView;
    }

/*    @PostMapping("")
    @RequestParam("g-recaptcha-response")*/

    public class RegistrationController {

        @Autowired
        private ICaptchaService captchaService;

    ...

        @RequestMapping(value = "/user/registration", method = RequestMethod.POST)
        @ResponseBody
        public GenericResponse registerUserAccount(@Valid UserDto accountDto, HttpServletRequest request) {
            String response = request.getParameter("g-recaptcha-response");
            captchaService.processResponse(response);

            // Rest of implementation
        }

    ...
    }

}
