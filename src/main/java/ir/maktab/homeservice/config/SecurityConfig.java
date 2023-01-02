//package ir.maktab.homeservice.config;
//
//import ir.maktab.homeservice.repository.user.UserRepository;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableGlobalMethodSecurity(
//        prePostEnabled = true,
//        securedEnabled = true,
//        jsr250Enabled = true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final UserRepository userRepository;
//
//    public SecurityConfig(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//@Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("MiladAmery")
//                .password("$2a$10$n2V/t1S4BhGO6.3sLl29m.3xXq.lnLbDnaw0am7UvM/9v9qIGnEqi")
//                .roles("OSTAD")
//                .and()
//                .withUser("FarzadAfshar")
//                .password("$2a$10$uvummNb4kui4nuj5OfeHC.nDMipeu4.NY66naXrNO1rzGAX1SHvl6")
//                .roles("OSTAD");
//    }
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(username -> userRepository
//                        .findByUsername(username)
//                        .orElseThrow(() -> new UsernameNotFoundException(username))
//                );
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/api/v1/user").permitAll()
//                .antMatchers("/api/v1/wallet/all").hasAnyRole("ROLE_ADMIN")
//                .antMatchers("/api/v1/**").authenticated()
//                .and()
//                .httpBasic(); // httpBasic(), formLogin()
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
