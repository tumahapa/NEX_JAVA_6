package com.contract.parse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.settings.Settings;

public class ReadContractNote
{
	public static Contract getContractNoteDetail(String absFilePath)
	{
		Contract contract = null;
		
		try 
		{
			FileInputStream fileIn = new FileInputStream(absFilePath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			contract = (Contract) in.readObject();
			in.close();
			fileIn.close();
			
			return contract;
		}
		catch(IOException i) 
		{
			i.printStackTrace();
			return null;
		}
		catch (ClassNotFoundException c) 
		{
			System.out.println("Contract class not found");
			c.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args)
	{
		Contract contract = ReadContractNote.getContractNoteDetail(Settings.BACKUP_DIR + File.separator + "ALLCN_UNT0114__1231_01_22122016.ser");
		System.out.println(contract.getContractNoteNo() + " No Of Transactions:" + contract.getStockList().size());
		
		for(Stock stock:contract.getStockList())
		{
			System.out.println("-----------------------------------------------------------------");
			System.out.println("Order No:" + stock.getOrderNo());
			System.out.println("Order Time:" + stock.getOrderTime());
			System.out.println("Trade No:" + stock.getTradeNo());
			System.out.println("Trade Time:" + stock.getTradeTime());
			System.out.println("Security/Contract Description:" + stock.getStockDescription());
			System.out.println("Transaction Type:" + stock.getTransactionType());
			System.out.println("Qty:"+ stock.getQty());
			System.out.println("Gross Rate:" + stock.getGrossRate());
			System.out.println("Brokerage Per Unit:" + stock.getBrokeragePerUnit());
			System.out.println("Net Rate:" + stock.getNetRate());
			System.out.println("NetTotal Before Levis:" + stock.getNetTotalBeforeLevis());
			System.out.println("-----------------------------------------------------------------");
		}
	}
}
