package ir.maktab.homeservice.util;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.apiclub.captcha.Captcha;

@Controller
@RequestMapping("/app")
public class CaptchaController {
  @GetMapping("/verify")
  public String register(Model model) {
	  model.addAttribute("captcha", genCaptcha());
	  return "verifyCaptcha";
  }
  @PostMapping("/verify")
  public String verify(@ModelAttribute CaptchaSettings captchaSettings, Model model) {
	  if(captchaSettings.getCaptcha().equals(captchaSettings.getHiddenCaptcha())){
		  model.addAttribute("message","Captcha verified successfully");
		  return "success";
	  }
	  else {
		  model.addAttribute("message","Invalid Captcha");
		  model.addAttribute("captcha",genCaptcha());
	  }
	  return "verifyCaptcha";
  }
  private CaptchaSettings genCaptcha() {
	  CaptchaSettings captchaSettings = new CaptchaSettings();
	  Captcha captcha = CaptchaGenerator.generateCaptcha(260, 80);
	  captchaSettings.setHiddenCaptcha(captcha.getAnswer());
	  captchaSettings.setCaptcha("");
	  captchaSettings.setRealCaptcha(CaptchaGenerator.encodeCaptchatoBinary(captcha));
	  return captchaSettings;
  }
}