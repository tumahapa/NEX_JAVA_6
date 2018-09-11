package com.contract.parse;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Stock implements Serializable
{
	private static final long serialVersionUID = -7856531816649939357L;

	private String contractNoteNo = null;
	
	private String orderNo = null;
	private String orderTime = null;
	private String tradeTime = null;
	private String tradeNo = null;
	private String stockName = null;
	private String transactionType = null;
	private String qty = null;
	private String grossRate = null;
	private String brokeragePerUnit = null;
	private String netRate = null;
	private String netTotalBeforeLevis = null;
	private String remarks = null;
	
	public String TRANSACTION_TYPE_BUY = "Buy";
	public String TRANSACTION_TYPE_SELL = "Sell";
	
	public Stock(){}
	
	public Stock(String lineToParse,String contractNoteNo)
	{
		this.contractNoteNo = contractNoteNo;
		
		String[] subString = lineToParse.split(TRANSACTION_TYPE_BUY + "|" + TRANSACTION_TYPE_SELL);
		for(String str:subString)
		{
			System.out.println(str);
		}
	}
	
	public Stock(String lineToParse)
	{
		init(lineToParse);
	}
	
	private void init(String lineToParse)
	{
		transactionType = lineToParse.contains(TRANSACTION_TYPE_BUY) ? TRANSACTION_TYPE_BUY : TRANSACTION_TYPE_SELL;
		
		String[] subString = lineToParse.split(transactionType);
		
		Matcher matcher = Pattern.compile("^[\\d\\s\\:]+").matcher(subString[0].trim());
		if(matcher.find())
		{
			String[] sub = matcher.group(0).trim().split("\\s");
			setOrderNo(sub[0]);
			setOrderTime(sub[1]);
			setTradeNo(sub[2]);
			setTradeTime(sub[3]);
		}
		
		setStockDescription(parseStockName(subString[0]));
		
		String[] sub2 = subString[1].trim().split("\\s");
		this.setQty(sub2[0]);
		this.setGrossRate(sub2[1]);
		this.setBrokeragePerUnit(sub2[2]);
		this.setNetRate(sub2[3]);
		this.setNetTotalBeforeLevis(sub2[4]);
	}
	
	public String parseStockName(String oneLinerStock)
	{
		Matcher matcher = Pattern.compile("[^\\s0-9:].*").matcher(oneLinerStock.trim());
		
		if(matcher.find())
			return matcher.group(0);
		else
			return null;
	}
	
	public String getContractNoteNo() 
	{
		return contractNoteNo;
	}
	public void setContractNoteNo(String contractNoteNo)
	{
		this.contractNoteNo = contractNoteNo;
	}
	
	public String getOrderNo() 
	{
		return orderNo;
	}
	public void setOrderNo(String orderNo)
	{
		this.orderNo = orderNo;
	}
	
	public String getOrderTime()
	{
		return orderTime;
	}
	public void setOrderTime(String orderTime) 
	{
		this.orderTime = orderTime;
	}

	public String getTradeTime()
	{
		return tradeTime;
	}
	public void setTradeTime(String tradeTime)
	{
		this.tradeTime = tradeTime;
	}
	
	public String getTradeNo()
	{
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) 
	{
		this.tradeNo = tradeNo;
	}
	
	public String getStockDescription()
	{
		return stockName;
	}
	public void setStockDescription(String val) 
	{
		this.stockName = val;
	}
	
	public String getTransactionType()
	{
		return transactionType;
	}
	public void setTransactionType(String transactionType)
	{
		this.transactionType = transactionType;
	}
	
	public String getQty() 
	{
		return qty;
	}
	public void setQty(String qty) 
	{
		this.qty = qty;
	}
	
	public String getGrossRate()
	{
		return grossRate;
	}
	
	public void setGrossRate(String grossRate)
	{
		this.grossRate = grossRate;
	}
	public String getBrokeragePerUnit()
	{
		return brokeragePerUnit;
	}
	
	public void setBrokeragePerUnit(String brokeragePerUnit)
	{
		this.brokeragePerUnit = brokeragePerUnit;
	}
	public String getNetRate()
	{
		return netRate;
	}
	public void setNetRate(String netRate)
	{
		this.netRate = netRate;
	}
	
	public String getNetTotalBeforeLevis() 
	{
		return netTotalBeforeLevis;
	}
	public void setNetTotalBeforeLevis(String netTotalBeforeLevis)
	{
		this.netTotalBeforeLevis = netTotalBeforeLevis;
	}
	
	public String getRemarks() 
	{
		return remarks;
	}
	public void setRemarks(String remarks) 
	{
		this.remarks = remarks;
	}
		
	public static void main(String[] args)
	{
		String lineToParse = "2377400024186614 15:16:36 4827900 15:17:08 Bajaj Finance Ltd (INE296A01024) Buy 2 766.6000 3.8350 770.4350 -1533.20";
		Stock stock = new Stock(lineToParse);
		
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