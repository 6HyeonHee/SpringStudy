package com.edu.springboot.mybatis;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ISearchRadius {
	// 조건애 맞는 시설물의 개수 카운트
	public int searchCount(int distance, double latTxt, double lngTxt);
	
	// 조건에 맞는 시설물을 페이지 단위로 인출하여 List로 반환
	public ArrayList<MyFacilityDTO> searchRadius(int distance,
			double latTxt, double lngTxt, int start, int end);
}
