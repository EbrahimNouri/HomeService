package ir.maktab.homeservice.controller.user;

import cn.apiclub.captcha.Captcha;
import ir.maktab.homeservice.util.CaptchaGenerator;
import ir.maktab.homeservice.util.CaptchaSettings;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
@Data
public class CaptchaController {

    @GetMapping("/verify")
    public String register(Model model) {
        model.addAttribute("captcha", genCaptcha());
        return "verifyCaptcha";
    }

    @PostMapping("/verify")
    public String verify(@ModelAttribute CaptchaSettings captchaSettings, Model model) {
        if (captchaSettings.getCaptcha().equals(captchaSettings.getHiddenCaptcha())
                /*&& cardCheck(card) && userService.existsByEmail(email*/) {
            model.addAttribute("message", "Captcha verified successfully");
        } else {
            model.addAttribute("message", "Invalid Captcha");
            model.addAttribute("captcha", genCaptcha());
        }
        return "verifyCaptcha";
    }


    private CaptchaSettings genCaptcha() {
        CaptchaSettings captchaSettings = new CaptchaSettings();
        Captcha captcha = CaptchaGenerator.generateCaptcha(260, 80);
        captchaSettings.setHiddenCaptcha(captcha.getAnswer());
        captchaSettings.setCaptcha("");
        captchaSettings.setRealCaptcha(CaptchaGenerator.encodeCaptchaToBinary(captcha));
        return captchaSettings;
    }

    private boolean cardCheck(String card) {
        if (card.trim().length() == 16) {
            try {
                Integer.parseInt(card);
                return true;
            } catch (NumberFormatException | NullPointerException e) {
                return false;
            }
        } else return false;
    }
}