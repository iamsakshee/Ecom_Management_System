package com.springboot.ecom;

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

import com.springboot.ecom.service.UserSecurityService;

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
                        .requestMatchers(HttpMethod.GET, "/api/token").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/sign-up").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/hello").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/executive/hello").hasAuthority("EXECUTIVE")
                        .requestMatchers("/customer/**").hasAuthority("CUSTOMER")
                        .requestMatchers("/vendor/**").hasAuthority("VENDOR")

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





























	
/*	this is for http basic security
	@Autowired
	private UserSecurityService userSecurityService;
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
		return passEncoder;
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.csrf((csrf) -> csrf.disable())
		 .authorizeHttpRequests(authorize -> authorize
				 	.requestMatchers(HttpMethod.GET, "/auth/login").authenticated()
				 	.requestMatchers(HttpMethod.POST, "/auth/switch-status").hasAuthority("EXECUTIVE") 
				 	.requestMatchers(HttpMethod.GET, "/api/hello").hasAuthority("CUSTOMER")                                     
					.requestMatchers(HttpMethod.POST, "/auth/sign-up").permitAll() 
					.requestMatchers(HttpMethod.POST, "/customer/add/").hasAnyAuthority("CUSTOMER")
				.anyRequest().permitAll()
			) 
			.httpBasic(Customizer.withDefaults());
		return http.build();
	}
	
	@Bean
	AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userSecurityService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		
		ProviderManager providerManager = new ProviderManager(authenticationProvider);
		return providerManager;
	}

}
*/