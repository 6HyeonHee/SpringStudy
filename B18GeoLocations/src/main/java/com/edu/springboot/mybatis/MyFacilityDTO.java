package com.edu.springboot.mybatis;

import lombok.Data;

@Data
public class MyFacilityDTO {
	private String hp_name;			// 병원명
	private String hp_sido;			// 시도
	private String hp_gugun;		// 구군
	private String hp_addr;			// 주소
	private String hp_url;			// 참조 URL
	private String hp_latitude;		// 위도
	private String hp_longitude;	// 경도
	private String disKM;			// 거리(반경)
	private String rNum;
}
