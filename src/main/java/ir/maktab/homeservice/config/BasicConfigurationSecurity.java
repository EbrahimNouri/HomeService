//package ir.maktab.homeservice.config;
//
//import ir.maktab.homeservice.repository.UserDetailRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(
//        prePostEnabled = true,
//        jsr250Enabled = true,
//        securedEnabled = true
//)
//public class BasicConfigurationSecurity {
//
//    private final BCryptPasswordEncoder passwordEncoder;
//    private final UserDetailRepository userRepository;
//
//    public BasicConfigurationSecurity(BCryptPasswordEncoder passwordEncoder, UserDetailRepository userRepository) {
//        this.passwordEncoder = passwordEncoder;
//        this.userRepository = userRepository;
//    }
//
//
//
///*    @Bean
//    public InMemoryUserDetailsManager userDetailsServiceInMemory(PasswordEncoder passwordEncoder) {
//        UserDetails user = User.withUsername("expert")
//                .password(passwordEncoder.encode("PAsswOrd"))
//                .roles("EXPERT")
//                .build();
//
//        UserDetails admin = User.withUsername("admin")
//                .password(passwordEncoder.encode("PAsswOrd"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails expert = User.withUsername("user")
//                .password(passwordEncoder.encode("PAsswOrd"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin, expert);
//    }*/
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//
//                .authorizeHttpRequests(
//                        (auth) -> auth.requestMatchers(
//                                "/api/v1/expert/**").hasRole("EXPERT"))
//
//                .authorizeHttpRequests(
//                        (auth) -> auth.requestMatchers(
//                                "/api/v1/user/**").hasRole("USER"))
//
//                .authorizeHttpRequests(
//                        (auth) -> auth.requestMatchers(
//                                "/api/v1/admin/**").hasRole("ADMIN"))
//
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
//                .httpBasic();
//
//        return http.build();
//    }
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//
//        return (web) -> web.ignoring()
//                .requestMatchers(
//                        "/api/v1/admin/add"
//                );
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager
//            (AuthenticationConfiguration authenticationConfiguration) throws Exception {
//
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//
///*        auth.userDetailsService(username -> userRepository
//                        .findByUsername(username)
//                        .orElseThrow(() -> new UsernameNotFoundException(String
//                                .format("this %s not found", username))))
//                .passwordEncoder(passwordEncoder).and()
//
//                .userDetailsService(username -> adminRepository
//                        .findByUsername(username)
//                        .orElseThrow(() -> new UsernameNotFoundException(String
//                                .format("this %s not found", username))))
//                .passwordEncoder(passwordEncoder).and()
//
//                .userDetailsService(username -> expertRepository
//                        .findByUsername(username)
//                        .orElseThrow(() -> new UsernameNotFoundException(String
//                                .format("this %s not found", username))))
//                .passwordEncoder(passwordEncoder);*/
//
//        auth.userDetailsService(username -> userRepository
//                        .findByUsername(username)
//                        .orElseThrow(() -> new UsernameNotFoundException(String
//                                .format("this %s not found", username))))
//                .passwordEncoder(passwordEncoder);
//
//    }
//}


package ir.maktab.homeservice.config;

import ir.maktab.homeservice.repository.UserDetailRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class BasicConfigurationSecurity {
    private final UserDetailRepository userDetailRepository;
    private final PasswordEncoder passwordEncoder;

    public BasicConfigurationSecurity(UserDetailRepository userDetailRepository, PasswordEncoder passwordEncoder) {
        this.userDetailRepository = userDetailRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(request -> request.requestMatchers("/api/v1/admin/**").hasRole("ADMIN"))


                .authorizeHttpRequests(request -> request.requestMatchers("/api/v1/expert/**").hasRole("EXPERT"))

                .authorizeHttpRequests(request -> request.requestMatchers("/api/v1/user/**").hasRole("USER"))
                .authenticationManager(authManager(http))
                .httpBasic();
//                .authorizeHttpRequests().anyRequest().permitAll();
        return http.build();
    }
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(user());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public DaoAuthenticationProvider user() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(username -> userDetailRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("")));
        return provider;
    }
}
