package com.ct.usecase.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ct.usecase.demo.util.JwtAuthenticationFilter;
import com.ct.usecase.demo.util.JwtUtil;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtUtil jwtUtil;
	
	public SecurityConfig(JwtUtil jwtUtil) {
		this.jwtUtil=jwtUtil;
	}
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//        http
//            .csrf(csrf -> csrf.disable())
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/auth/**").permitAll()
//                .requestMatchers("/admin/**").hasRole("SYSTEM_ADMIN")
//                .anyRequest().authenticated()
//            )
//            .httpBasic(Customizer.withDefaults());

		http.cors(cors->{})
		.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(auth -> auth
			    .requestMatchers("/auth/**").permitAll()
			    .requestMatchers("/admin/**").hasRole("SYSTEM_ADMIN")
			    .requestMatchers("/payer/**").hasRole("PAYER_USER")
			    .requestMatchers("/provider/**").hasRole("PROVIDER_USER")
			    .anyRequest().authenticated()
			)

				.addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
