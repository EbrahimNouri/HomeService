package ir.maktab.homeservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "google.recaptcha.key")
public class CaptchaSettings {
    // ... other properties
    private float threshold;

    // standard getters and setters
}