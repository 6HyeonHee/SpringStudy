package com.edu.springboot.auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
public class WebSecurityConfig {
	
	@Autowired
	public MyAuthFailureHandler myAuthFailureHandler;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf((csrf) -> csrf.disable())
			.cors((cors) -> cors.disable())
			.authorizeHttpRequests((request) -> request
					.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
					.requestMatchers("/").permitAll()
					.requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
					.requestMatchers("/guest/**").permitAll()
					.requestMatchers("/member/**").hasAnyRole("USER", "ADMIN")
					.requestMatchers("/admin/**").hasRole("ADMIN")
					.anyRequest().authenticated()
			);
		
		http.formLogin((formLogin) -> formLogin
				.loginPage("/myLogin.do")						// default: /login
				.loginProcessingUrl("/myLoginAction.do")
//				.failureUrl("/myError.do")						// default: /login?error
				.failureHandler(myAuthFailureHandler)
				.usernameParameter("my_id")						// default: username
				.passwordParameter("my_pass")					// default: password
				.permitAll());
		
		http.logout((logout) -> logout
				.logoutUrl("/myLogout.do")						// default: /logout
				.logoutSuccessUrl("/")
				.permitAll());
		
		http.exceptionHandling((expHandling) -> expHandling
				.accessDeniedPage("/denied.do"));
		
		return http.build();
	}
	
	/*
	 2단계(디자인 커스텀)에서 인메모리 방식으로 사용했던 메서드는 이번단계에서는
	 사용하지 않으니 삭제처리한다.
	 */
	
	// DB연결을 위한 데이터소스를 자동주입 받는다.
	@Autowired
	private DataSource dataSource;
	
	/*
	 아래 2개의 쿼리문 실행을 통해 사용자의 인증정보와 권한을 인출한다.
	 첫번째 쿼리는 사용자의 아이디, 비번, 그리고 계정활성화 여부를 확인한다.
	 두번째 쿼리는 사용자의 권한(회원등급)을 확인한다.
	 */
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth)
		throws Exception {
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("select user_id, user_pw, enabled "
					+ " from security_admin where user_id =? ")
			.authoritiesByUsernameQuery("select user_id, authority "
					+ " from security_admin where user_id = ?")
			.passwordEncoder(new BCryptPasswordEncoder());
	}
}
