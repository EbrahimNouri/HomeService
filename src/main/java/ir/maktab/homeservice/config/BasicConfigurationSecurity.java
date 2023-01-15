package ir.maktab.homeservice.config;

import ir.maktab.homeservice.repository.admin.AdminRepository;
import ir.maktab.homeservice.repository.expert.ExpertRepository;
import ir.maktab.homeservice.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        jsr250Enabled = true,
        securedEnabled = true
)
public class BasicConfigurationSecurity {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ExpertRepository expertRepository;
    private final AdminRepository adminRepository;

    public BasicConfigurationSecurity(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
                                      ExpertRepository expertRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.expertRepository = expertRepository;
        this.adminRepository = adminRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
//
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/v1/register/**", "/app/**")
//                        .permitAll())

                .authorizeHttpRequests(
                        (auth) -> auth.requestMatchers(
                                        "/api/v1/expert/**").hasRole("EXPERT")

                                .requestMatchers(
                                        "/api/v1/user/**").hasRole("USER")

                                .requestMatchers(
                                        "/api/v1/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )

                .httpBasic(withDefaults());

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (web) -> web.ignoring()
                .requestMatchers(
                        "/api/v1/register/**"
                        ,"/app/**"
                );
    }

    @Bean
    public AuthenticationManager authenticationManager
            (AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(username -> userRepository
                        .findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(String
                                .format("this %s not found", username))))
                .passwordEncoder(passwordEncoder).and()

                .userDetailsService(username -> adminRepository
                        .findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(String
                                .format("this %s not found", username))))
                .passwordEncoder(passwordEncoder).and()

                .userDetailsService(username -> expertRepository
                        .findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(String
                                .format("this %s not found", username))))
                .passwordEncoder(passwordEncoder);

    }
}
