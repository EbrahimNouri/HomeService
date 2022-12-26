package ir.maktab.homeservice.service.captcha;

public interface CaptchaService {
    void processResponse(String response, String action);
}
