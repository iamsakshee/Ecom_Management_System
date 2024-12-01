package com.springboot.ecom.config;


import com.springboot.ecom.JwtFilter;
import com.springboot.ecom.service.UserSecurityService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/token").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/sign-up").permitAll()
                        .requestMatchers(HttpMethod.GET, "/auth/user").authenticated()
                        .requestMatchers(HttpMethod.POST,"/admin/auth/switch-status/{id}").hasAuthority("EXECUTIVE")
                        .requestMatchers(HttpMethod.POST,"/admin/getAllUsers").hasAuthority("EXECUTIVE")
                        .requestMatchers(HttpMethod.GET,"/admin/getAllProducts").hasAuthority("EXECUTIVE")
                        .requestMatchers(HttpMethod.GET,"/admin/getFeaturedProducts").hasAuthority("EXECUTIVE")
                        .requestMatchers(HttpMethod.PUT,"/admin/approve/product/{productId}").hasAuthority("EXECUTIVE")
                        .requestMatchers(HttpMethod.DELETE,"/vendor/delete/{id}").hasAuthority("EXECUTIVE")
//                        .requestMatchers(HttpMethod.PUT,"/admin/auth/switch-status/{id}").hasAuthority("EXECUTIVE")

//                        .requestMatchers(HttpMethod.POST, "/vendor/add").hasAuthority("VENDOR")
                        .requestMatchers(HttpMethod.PUT, "/vendor/update/{id").hasAuthority("VENDOR")
                        .requestMatchers(HttpMethod.GET,"/products-with-images/{vendorId}").hasAuthority("VENDOR")
                        .requestMatchers(HttpMethod.GET,"/products/all/{userId}").hasAuthority("VENDOR")
                        .requestMatchers(HttpMethod.POST,"/product/add/{userId}/{categoryId}").hasAuthority("VENDOR")
                        .requestMatchers(HttpMethod.POST,"/api/product/image/upload/{pid}").hasAuthority("VENDOR")
                        .requestMatchers(HttpMethod.PUT,"/product/update/status/{productId}").hasAuthority("VENDOR")
                        .requestMatchers(HttpMethod.PUT,"/product/update/{productId}").hasAuthority("VENDOR")
                        .requestMatchers(HttpMethod.DELETE,"product/delete/{id}").hasAuthority("VENDOR")
                        .requestMatchers(HttpMethod.GET,"/orders/{vendorId}/{orderStatus}").hasAuthority("VENDOR")
                        .requestMatchers(HttpMethod.GET,"/orders/{vendorId}").hasAuthority("VENDOR")
//                        .requestMatchers("/vendor/**", "/products/**").hasAuthority("VENDOR")
//                        .requestMatchers("/customer/**").hasAuthority("CUSTOMER")

                        .anyRequest().permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    BCryptPasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userSecurityService);
        authenticationProvider.setPasswordEncoder(getEncoder());
        return authenticationProvider;
    }
}