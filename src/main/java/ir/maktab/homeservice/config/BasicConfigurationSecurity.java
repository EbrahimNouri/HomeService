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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

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


/*    @Bean
    protected void userConfigure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username))
        ).passwordEncoder(passwordEncoder);
    }

    @Bean
    protected void adminConfigure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> adminRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username))
        ).passwordEncoder(passwordEncoder);
    }

    @Bean
    protected void expertConfigure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> expertRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username))
        ).passwordEncoder(passwordEncoder);
    }*/

    @Bean
    public InMemoryUserDetailsManager userDetailsServiceInMemory(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("expert")
                .password(passwordEncoder.encode("PAsswOrd"))
                .roles("EXPERT")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("PAsswOrd"))
                .roles("ADMIN")
                .build();

        UserDetails expert = User.withUsername("user")
                .password(passwordEncoder.encode("PAsswOrd"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user, admin, expert);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(
                        (auth) -> auth.requestMatchers(
                                "api/v1/expert/**").hasRole("EXPERT"))

                .authorizeHttpRequests(
                        (auth) -> auth.requestMatchers(
                                "api/v1/user/**").hasRole("USER"))

                .authorizeHttpRequests(
                        (auth) -> auth.requestMatchers(
                                "api/v1/admin/**").hasRole("ADMIN"))

                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())

                .httpBasic();

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (web) -> web.ignoring()
                .requestMatchers(
                        "/api/v1/user/register"
                        , "/api/v1/admin/add"
                        , "/api/v1/expert/verify"
                        , "/api/v1/expert/process_register"
                        , "/api/v1/expert/register");
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
                .passwordEncoder(passwordEncoder);

        auth.userDetailsService(username -> adminRepository
                        .findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(String
                                .format("this %s not found", username))))
                .passwordEncoder(passwordEncoder);

        auth.userDetailsService(username -> expertRepository
                        .findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(String
                                .format("this %s not found", username))))
                .passwordEncoder(passwordEncoder);

    }
}
