package com.edu.springboot.auth;

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

/*
 스프링 컨테이너가 시작될 때 빈이 생성되어야 하므로 기본패키지 하위에
 정의한 후 어노테이션을 부착한다.
 스프링 시큐리티 사용을 위한 설정 클래스로 정의한다.
 */
@Configuration
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)
		throws Exception {
		/*
		 스프링 시큐리티는 특정 페이지에 로그인 확인을 위한 코드를 삽입하는게 아닌
		 아래와 같이 요청명의 패턴을 통해 설정한다.
		 permitAll() : 인증없이 누구나 접근할 수 있는 페이지를 지정
		 hasAnyRole() : 인증 후 권한을 획득해야 접근할 수 있는 페이지를 지정
		 	단, 여러개의 권한 중 하나만 획득하면 접근할 수 있다.
		 hasRole() : hasAnyRole()과 비슷하지만 한가지의 권한을 획득해야 접근할 수 있다.
		 
		 정적리소스가 있는 css, js, images폴더와 guest는 권한 없이 누구나 접근할 수 있다.
		 member하위는 USER 혹은 ADMIN 권한을 획득한 후 접근할 수 있다.
		 admin하위는 ADMIN 권한만 접근할 수 있다.
		 */
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
		
		/*
		 로그인 폼과 로그아웃은 별도의 설정없이 디폴트로 제공되어 페이지와 요청명을
		 사용한다. 추가적인 설정은 두번째 예제에서 진행한다.
		 */
		http.formLogin((formLogin) ->
				formLogin.permitAll());
		http.logout((logout) ->
				logout.permitAll());
		
		return http.build();
	}
	
	/*
	 로그인 후 획득할 수 있는 권한에 대한 설정을 한다. USER 권한과 ADMIN권한을 획득하기
	 위한 아이디/비번을 메모리에 저장한다. DB에 저장하기 위해서는 별도의 커스터마이징이 필요하다.
	*/
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
		
		return new InMemoryUserDetailsManager(user, admin);
	}
	
	// 패스워드 인코더 : 패스워드를 저장하기 전 암호화한다.
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
