package ru.kuranov.pull.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.kuranov.pull.service.UserService;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final DataSource dataSource;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(
                (requests) -> {
                    requests.antMatchers(HttpMethod.PUT, "/api/v1/pulls/{id}").hasAuthority("ADMIN");
                    requests.antMatchers(HttpMethod.POST, "/**").hasAuthority("ADMIN");
                    requests.antMatchers("/**").permitAll();
//                    requests.antMatchers("/login").permitAll();
//                    requests.antMatchers("/logout").permitAll();
//                    requests.antMatchers("/app/products/addtocart").hasAnyAuthority("ADMIN", "USER", "MANAGER");
//                    requests.antMatchers("/").hasRole("ADMIN");
//                    requests.antMatchers("/").permitAll();
//                    requests.antMatchers("/app/products/**").permitAll();
//                    requests.antMatchers("/app/products/cart").permitAll();
                }
        );

        http.authorizeRequests((requests) -> ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) requests.anyRequest()).authenticated());
        http
                .formLogin();
//                .loginPage("/login")
//                .loginProcessingUrl("/user-login")
//                .usernameParameter("username")
//                .passwordParameter("password");
//                .defaultSuccessUrl("/");
//
        http
                .logout();
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                .logoutSuccessUrl("/app/products");

        http.httpBasic();
        http.csrf().disable();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/").permitAll()
//                .antMatchers("/images/**").permitAll()
//                .antMatchers("/catalog/**").permitAll()
//                .antMatchers("/registration").permitAll()
//                .antMatchers("/info").permitAll()
//                .antMatchers("/service").permitAll()
//                .antMatchers("/payment").permitAll()
//                .antMatchers("/contacts").permitAll()
//                .antMatchers("/activate/*").permitAll()
//                .antMatchers("/booking").permitAll()
//                .anyRequest().authenticated()
//                .antMatchers("/api/v1/**").hasRole("ADMIN");
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutSuccessUrl("/")
//                .permitAll();
//    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);

    }
}