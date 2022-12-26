package ir.maktab.homeservice.service.captcha;

import ir.maktab.homeservice.exception.ReCaptchaInvalidException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CaptchaServiceImpl implements CaptchaService {

    private ReCaptchaAttemptService reCaptchaAttemptService;
    private RestTemplate restTemplate;
    public static final String REGISTER_ACTION = "register";

    @Override
    public void processResponse(String response, String action) {
        GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);
        if(!googleResponse.isSuccess() || !googleResponse.getAction().equals(action)
                || googleResponse.getScore() < captchaSettings.getThreshold()) {
            throw new ReCaptchaInvalidException("reCaptcha was not successfully validated");
        }
        reCaptchaAttemptService.reCaptchaSucceeded(getClientIP());
    }
}