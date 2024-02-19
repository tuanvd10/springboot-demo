package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.jwtsecure.JwtAuthenticationFilter;

@Configuration
public class WebSecurityConfig {
	@Autowired
	private JwtAuthenticationFilter authFilter;
	@Autowired
	private UserDetailsService userDetailService;

    @Autowired
    @Qualifier("delegatedAuthenticationEntryPoint")
    AuthenticationEntryPoint authEntryPoint; //entry point fot authenticate exception handler

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		// Password encoder, để Spring Security sử dụng mã hóa mật khẩu người dùng
		// return new BCryptPasswordEncoder();
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		System.err.printf("filter chain \n");
		http.csrf(csrf -> {
			csrf.disable();
		}).cors(cors -> cors.disable()).addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
				.authorizeHttpRequests(auth -> {
					auth.requestMatchers("/api/auth/v0/login").permitAll();
					auth.requestMatchers("/api/auth/v0/register").permitAll();
					auth.requestMatchers("/img/**").permitAll();
					auth.requestMatchers(HttpMethod.GET, "/hello").permitAll(); // not match
					auth.requestMatchers(HttpMethod.GET, "/hello.html").permitAll(); // not match
					auth.requestMatchers(HttpMethod.GET, "/about").permitAll();// ok with @ResponBody => not work if
																				// return file...
					auth.requestMatchers(HttpMethod.GET, "/favicon.ico").permitAll();// not match
					auth.requestMatchers("/api/user/v0/all").hasRole("ADMIN");
					auth.requestMatchers(HttpMethod.DELETE, "/api/user/v0/*").hasRole("ADMIN");
					auth.requestMatchers("/api/**").authenticated();
				}).exceptionHandling().authenticationEntryPoint(authEntryPoint);
//				.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		// .httpBasic(Customizer.withDefaults()).authenticationProvider(authenticationProvider());

		// http.addFilterAfter(authFilter, null); filter after handle
		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
}
