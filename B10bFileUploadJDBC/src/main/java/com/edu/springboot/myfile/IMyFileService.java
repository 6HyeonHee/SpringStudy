package com.edu.springboot.myfile;

import java.util.List;

public interface IMyFileService {
	public int insertFile(MyFileDTO myfileDTO);
	public List<MyFileDTO> selectFile();
}
