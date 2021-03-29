package com.zeekling.util;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FileUtilsTest {
	
	private String context = "test";
	
	@Test
	public void writeFile() {
		FileUtils.saveDataToFile("/tmp/README.md", context);
	}
	
	@Test
	public void readFile() {
		String fileContext = FileUtils.readFile("/tmp/README.md");
		System.out.println(fileContext);
	}

}
