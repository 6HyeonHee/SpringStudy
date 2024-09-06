package com.edu.springboot.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
public class WebSecurityConfig {
	
	/*
	 인증 핸들러를 제작했다면 사용을 위해 빈을 자동주입 받는다.
	 그리고 시큐리티 설정 부분의 failureHandler() 메서드에 추가한다.
	 */
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
		
		/* 로그인 페이지에 대한 커스터마이징 설정
		- loginPage: 로그인 페이지의 요청명
		- loginProcessingUrl: 폼값을 전송하여 로그인 처리할 요청명
		- failureUrl: 로그인에 실패한 경우 이동할 요청명
		- failureHandler: 별도의 핸들러 인스턴스를 등록 후 에러 처리
		- usernameParameter: 아이디 입력을 위한 <input>의 name 속성값
		- passwordParameter: 패스워드 입력 상자의 name 속성값 */
		http.formLogin((formLogin) -> formLogin
				.loginPage("/myLogin.do")						// default: /login
				.loginProcessingUrl("/myLoginAction.do")
//				.failureUrl("/myError.do")						// default: /login?error
				.failureHandler(myAuthFailureHandler)
				.usernameParameter("my_id")						// default: username
				.passwordParameter("my_pass")					// default: password
				.permitAll());
		
		/* 로그아웃 페이지에 대한 커스터마이징 설정
		- logoutUrl: 로그아웃을 위한 요청명
		- logoutSuccessUrl: 로그아웃 이후 이동할 페이지 */
		http.logout((logout) -> logout
				.logoutUrl("/myLogout.do")						// default: /logout
				.logoutSuccessUrl("/")
				.permitAll());
		
		/* 권한이 부족한 경우 이동할 위치 지정 
		(ex. user로 로그인했는데 admin 권한이 필요한 페이지에 접근하는 경우) */
		http.exceptionHandling((expHandling) -> expHandling
				.accessDeniedPage("/denied.do"));
		
		return http.build();
	}
	
	/* 로그인 후 획득할 수 있는 권한에 대한 설정을 한다.
	USER 권한과 ADMIN 권한을 획득하기 위한 아이디, 비밀번호를 메모리에 저장한다.
	DB에 저장하기 위해서는 별도의 커스터마이징이 필요하다. */
	@Bean
	public UserDetailsService users() {
		UserDetails user = User.builder()
				.username("user")
				.password(passwordEncoder().encode("1234"))
				.roles("USER")
				.build();
		UserDetails admin = User.builder()
				.username("admin")
				.password(passwordEncoder().encode("1234"))
				.roles("USER", "ADMIN")
				.build();
		
		// 메모리에 사용자정보를 담는다.
		return new InMemoryUserDetailsManager(user, admin);
	}
	// 패스워드 인코더 (암호화): 패스워드를 저장하기 전 암호화한다.
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
