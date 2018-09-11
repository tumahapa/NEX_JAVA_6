package com.contract.parse;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

public class XtractContractFrmFile 
{
	private PDDocument pdDoc = null;
		
	public String xtractContent(String passwd,String absPDFFilePath)
	{
		File pdfFile = new File(absPDFFilePath);
		if(!pdfFile.exists())
			return null;		

		try
		{
			pdDoc = PDDocument.load(pdfFile);
		} 
		catch (InvalidPasswordException e) 
		{
			try
			{
				pdDoc = PDDocument.load(pdfFile,passwd);
				return getContent();				
			} 
			catch (InvalidPasswordException e1)
			{
				e1.printStackTrace();
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
		} 
		catch (IOException e) 
		{
			
		}
		finally
		{
		    
		}

		return null;
	}
	
	private String getContent()
	{
		try
        {
			PDFTextStripper pdfStripper = new PDFTextStripper();
			String parsedText = pdfStripper.getText(pdDoc);
	        pdDoc.close();
	        //System.out.println(parsedText);
	        
	        return parsedText;
        }
		catch(Exception e)
		{
			return null;
		}
	}
	
	private static class XtractContractFrmFileInstance
	{
		private static final XtractContractFrmFile instance = new XtractContractFrmFile();
	}
	public static final XtractContractFrmFile getInstance()
	{
		return XtractContractFrmFileInstance.instance;
	}
	
	public static void main(String[] args) 
	{
		XtractContractFrmFile xtractContent = XtractContractFrmFile.getInstance();
		xtractContent.xtractContent("ANCPM1012F"
				                  ,"C:\\UNT0114_25042017_2223\\ALLCN_UNT0114__1231_02_01082016.PDF");
	}
}