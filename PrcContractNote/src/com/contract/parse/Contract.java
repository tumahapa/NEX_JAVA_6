package com.contract.parse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Contract implements Serializable
{
	private static final long serialVersionUID = 7073544345772741015L;
	
	private String contractNoteNo = null;
	private String tradeDate = null;
	private String settlementDate = null;
	private String rolling = null;
	private List<Stock> stockList = new ArrayList<Stock>();
	
	public String getContractNoteNo()
	{
		return contractNoteNo;
	}
	public void setContractNoteNo(String contractNoteNo) 
	{
		this.contractNoteNo = contractNoteNo;
	}
	
	public String getTradeDate() 
	{
		return tradeDate;
	}
	public void setTradeDate(String tradeDate)
	{
		this.tradeDate = tradeDate;
	}
	
	public String getSettlementDate()
	{
		return settlementDate;
	}
	public void setSettlementDate(String settlementDate) 
	{
		this.settlementDate = settlementDate;
	}
	
	public List<Stock> getStockList() 
	{
		return stockList;
	}
	public void setStockList(List<Stock> stockList)
	{
		this.stockList = stockList;
	}
	
	public String getRolling()
	{
		return rolling;
	}
	public void setRolling(String rolling)
	{
		this.rolling = rolling;
	}
}