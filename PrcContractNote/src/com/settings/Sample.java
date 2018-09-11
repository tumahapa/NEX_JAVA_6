package com.settings;

import java.io.File;

public class Sample 
{
	public static void main(String[] args) 
	{
		String absPDFFilePath = "C:" + File.separator + "UNT0114_25042017_2223" + File.separator + "ALLCN_UNT0114__1231_01_22122016.PDF";
		
		System.out.println(absPDFFilePath.substring(absPDFFilePath.lastIndexOf(File.separator) + 1,absPDFFilePath.lastIndexOf(".")));
	}
}