package com.edu.springboot.jdbc;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IAddressService {
	public List<AddressDTO> selectSido();
	public List<AddressDTO> selectGugun(AddressDTO addressDTO);
}
