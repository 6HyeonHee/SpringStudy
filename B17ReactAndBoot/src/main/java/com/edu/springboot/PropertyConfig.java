package com.edu.springboot;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/*
 사용자 정의 프로퍼티 파일을 Spring Container에 등록하기 위해
 정의한 클래스 시작시 자동으로 Scan되어 Bean을 생성하게 된다.
 해당 클래스는 반드시 기본 패키지 하위에 위치해야 한다.
 */
@Configuration
public class PropertyConfig {

	/*
	 name 속성을 지정하였으므로 myprops라는 이름으로 빈이 생성된다.
	 PropertiesFactoryBean 타입의 인스턴스를 생성하여
	 Spring Container에 보관한다.
	 */
	@Bean(name = "myprops")
	public PropertiesFactoryBean propertiesFactoryBean() {
		// 프로퍼티 인스턴스 생성
		PropertiesFactoryBean propertiesFactoryBean =
				new PropertiesFactoryBean();
		// 사용자 정의 프로퍼티 파일의 경로 지정
		ClassPathResource classPathResource =
				new ClassPathResource("board_config.properties");
		// 경로를 등록한 후 인스턴스를 반환한다.
		propertiesFactoryBean.setLocation(classPathResource);
		return propertiesFactoryBean;
	}
}
