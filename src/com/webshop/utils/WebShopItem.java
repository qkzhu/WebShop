package com.webshop.utils;

import java.math.BigDecimal;

public class WebShopItem {

	private String     __itemID;
	private String     __itemName;
	private String     __itemDescription;
	private BigDecimal __itemPrice;
	private int        __quantity;
	
	private static final int QUANTITY_OPERTION_INCREASE = 1;
	private static final int QUANTITY_OPERTION_DECREASE = 2;
	
	public WebShopItem(String itemID, String itemName, String itemDescription, BigDecimal itemPrice) {
		setItemID(itemID);
		setItemName(itemName);
		setItemDescription(itemDescription);
		setItemPrice(itemPrice);
	}

	
	public int increaseQuantityBy(int quantity) { 
		return quantityUpdate(QUANTITY_OPERTION_INCREASE, quantity);
	}
	
	
	public int reduceQuantityBy(int quantity) { 
		return quantityUpdate(QUANTITY_OPERTION_DECREASE, quantity);
	}
	
	
	private int quantityUpdate(int quantityOperation, int quantity) {
		
		int totalQuantity = getQuantity();
		
		if ( quantityOperation == QUANTITY_OPERTION_INCREASE ) totalQuantity += quantity;
		else if ( quantityOperation == QUANTITY_OPERTION_INCREASE ) totalQuantity += quantity;
		
		// make sure no nagtive quantity
		totalQuantity = totalQuantity < 0 ? 0 : totalQuantity;
		
		setQuantity(totalQuantity);
		
		return totalQuantity;
	}
	
	
	public void setItemID(String itemID) { __itemID = itemID;}
	public String getItemID() { return __itemID; }
	
	public void setItemName(String itemName) { __itemName = itemName; }
	public String getItemName() { return __itemName; }
	
	public void setItemDescription(String itemDescription) { __itemDescription = itemDescription; }
	public String getItemDescription() { return __itemDescription; }
	
	public void setItemPrice(BigDecimal itemPrice) { __itemPrice = itemPrice; }
	public BigDecimal getItemPrice() { return __itemPrice; }
	
	public void setQuantity(int quantity) { __quantity = quantity; }
	public int getQuantity() { return __quantity; }

} // end of class