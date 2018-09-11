package com.contract.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.settings.Settings;

public class CreateContractNote
{
	private XtractContractFrmFile xtractContent = XtractContractFrmFile.getInstance();
	private String passwd = null;
	private String rawContent = null;
	private List<String> rawStockList = new ArrayList<String>();
	private List<String> tokenList = new ArrayList<String>();
	private String fileNameWOExtn = null;
	
	private CreateContractNote()
	{
		init();
	}
	
	private void init()
	{
		try
		{
			InputStream in = CreateContractNote.class.getClassLoader().getResourceAsStream("com/settings/token.txt"); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = "";
			while((line = reader.readLine()) != null)
				tokenList.add(line.trim());
			
			File backupDir = new File(Settings.BACKUP_DIR);
			if(!backupDir.exists())
				backupDir.mkdirs();
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void setDocumentPassword(String passwd)
	{
		this.passwd = passwd;
	}
	
	public void parse(String absPDFFilePath)
	{
		try
		{
			fileNameWOExtn = absPDFFilePath.substring(absPDFFilePath.lastIndexOf(File.separator) + 1
					                                 ,absPDFFilePath.lastIndexOf("."));
			
			rawContent = xtractContent.xtractContent(passwd,absPDFFilePath).trim();
						
			parseStockList();
			parseHeaderData();
		}			
		catch(Exception e)
		{
			System.out.println("ERROR:" + e.getMessage());
		}
	}
	
	private void parseStockList()
	{
		try
		{
			String xtractedLine = "";
			boolean DATA_COMPLETE = true;
			
			for(String line:rawContent.split("\n"))
			{
				line = line.trim().replaceAll("\n", "");
				
				if(DATA_COMPLETE)
				{
					Matcher match = Pattern.compile("^(\\d+){3}.*").matcher(line);
					if(match.find())
					{
						xtractedLine = match.group(0);
												
						if(endsWithInteger(xtractedLine))
							addToStockList(xtractedLine);
						else
							DATA_COMPLETE = false;
					}
				}
				else
				{
					if(line.startsWith("("))
						xtractedLine = xtractedLine + " " + line;
					else
					{
						if(line.startsWith("Buy") || line.startsWith("Sell"))
						{
							xtractedLine = xtractedLine + " " + line;
							addToStockList(xtractedLine);
							DATA_COMPLETE = true;
						}
					}
				}
			}
		}			
		catch(Exception e)
		{
			System.out.println("ERROR:" + e.getMessage());
		}
	}
	
	private void addToStockList(String line)
	{
		rawStockList.add(line);
		System.out.println(line);
	}
	
	public boolean endsWithInteger(String line)
	{
		return Pattern.compile("(\\d)$").matcher(line).find();
	}
	
	public void parseHeaderData()
	{
		Contract contract = new Contract();
		
		/* Contract Note No */
		Matcher match = Pattern.compile("(\\bContract Note No).*([a-zA-Z]+)([0-9]+)",Pattern.CASE_INSENSITIVE).matcher(rawContent);
		if(match.find())
			contract.setContractNoteNo(match.group(0).split("\\.")[1].trim());
		
		/* Trade Date && Rolling*/
		match = Pattern.compile("(\\bTrade Date).*",Pattern.CASE_INSENSITIVE).matcher(rawContent);
		if(match.find())
		{
			//System.out.println(match.group(0));
			contract.setTradeDate(match.group(0).split("\\s")[2].trim());
			contract.setRolling(match.group(0).split("\\s")[4].trim());
		}
		
		/* Settlement Date */
		match = Pattern.compile("(\\bSettlement Date).*",Pattern.CASE_INSENSITIVE).matcher(rawContent);
		if(match.find())
			contract.setSettlementDate(match.group(0).split("\\s")[2]);
				
		/*System.out.println(contract.getContractNoteNo());
		System.out.println(contract.getTradeDate());
		System.out.println(contract.getRolling());
		System.out.println(contract.getSettlementDate()); */
		
		List<Stock> stockList = contract.getStockList();
		for(String rawStock:rawStockList)
			stockList.add(new Stock(rawStock));
		
		createFile(fileNameWOExtn,contract);
	}
	
	private void createFile(String fileName,Contract contract)
	{
		try 
		{
			File file = new File(Settings.BACKUP_DIR + File.separator + fileName + ".ser");
			if(file.exists())
				file.delete();
			
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(contract);
			out.close();
			fileOut.close();
		}
		catch (IOException i)
		{
			i.printStackTrace();
		}
	}
	
	private static class CreateContractNoteInstance
	{
		private static final CreateContractNote instance = new CreateContractNote();
	}
	public static final CreateContractNote getInstance()
	{
		return CreateContractNoteInstance.instance;
	}
	
	public static void main(String[] args)
	{
		CreateContractNote parseContent = CreateContractNote.getInstance();
		parseContent.setDocumentPassword("ANCPM1012F");
		parseContent.parse("C:\\UNT0114_25042017_2223\\ALLCN_UNT0114__1231_01_22122016.PDF");
		//String oneLinerStock = "2377400024186614 15:16:36 4827900 15:17:08 Bajaj Finance Ltd (INE296A01024) Buy 2 766.6000 3.8350 770.4350 -1533.20  ";
		//System.out.println(parseContent.endsWithInteger(oneLinerStock.trim()));		
	}
}
