package guru.sfg.brewery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    public RestHeaderFilter restHeaderFilter(AuthenticationManager authenticationManager) {
        RestHeaderFilter filter = new RestHeaderFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //* CUSTOM FILTER *//

        http
                .addFilterBefore(restHeaderFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .csrf()
                .disable();

        http
                .authorizeRequests(authorize ->
                authorize.antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
                        .antMatchers("/beers/find", "beers/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.GET, "/api/v1/beers/**").hasAnyRole("ADMIN","CUSTOMER","USER")
                        .mvcMatchers(HttpMethod.GET, "/api/v1/beers/{beerId}").permitAll())
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();


        // h2-console configuration
        http
                .headers()
                .frameOptions()
                .sameOrigin();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password("{sha256}a68da233e5ea8282b9478ffd716790614ff912ebd68c52f63b1c503b74dfe41021da0eec22a68aa0")
//                .roles("USER")
//                .and()
//                .withUser("admin")
//                .password("{noop}12345")
//                .roles("ADMIN");
//
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
