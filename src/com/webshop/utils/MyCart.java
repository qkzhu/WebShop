package com.webshop.utils;


import java.util.List;
import java.util.ArrayList;

public class MyCart {
	
	private List<WebShopItem> myOrder;
	
	

	/**
	 * Add item itemID with quantity quantityAdd to order list
	 * @param itemID
	 * @param quantityAdd
	 */
	public void addItem(WebShopItem item, int quantityToAdd) {
		
		initOrderListIfNull();
		
		if (item == null || quantityToAdd <= 0 )
			return;
		
		boolean itemExistInMyCart = false;
		for (WebShopItem itemInMyCart : myOrder) {
			if ( itemIDsMatch(itemInMyCart.getItemID(), item.getItemID()) ) {
				itemExistInMyCart = true;
				itemInMyCart.increaseQuantityBy(quantityToAdd);
				break;
			}
		}
		
		if (!itemExistInMyCart) {
			item.setQuantity(quantityToAdd);
			myOrder.add(item);
		}
	} // end addItem
	
	
	
	/**
	 * Remove item from shop cart with given quantity. 
	 * 
	 * If given quantity is -1 or given quantity is more than the quantity in the shopping card, 
	 * this item will be removed from order list; 
	 * 
	 * @param itemID
	 * @param quantityToRemove
	 */
	public void removeItemByQuantity(WebShopItem item, int quantityToRemove) {
		
		if (quantityToRemove == 0 || myOrder == null )
			return;
	
		for (WebShopItem itemInMyCart : myOrder) {
			if ( itemIDsMatch(itemInMyCart.getItemID(), item.getItemID()) ) {
				itemInMyCart.reduceQuantityBy(quantityToRemove);
				if ( itemInMyCart.getQuantity() == 0 ) myOrder.remove(itemInMyCart);
				break;
			}
		}
	} // end removeItem
	
	
	
	/**
	 * Updating item for itemID by replacing the number of quantity.
	 * 
	 * @param itemID
	 * @param quantity
	 */
	public void updateOrderList(WebShopItem item, int quantity) {
		
		initOrderListIfNull();
		
		if (item == null || quantity <= 0 )
			return;
		
		for (WebShopItem itemInMyCart : myOrder) {
			if ( itemInMyCart.getItemID().equals(item.getItemID()) ) {
				itemInMyCart.setQuantity(quantity);
				break;
			}
		}
		
	} // end updateOrderList
	
	
	

	/**
	 * List all all items with quantity in order list
	 */
	public void showMyOrderList() {
		
		if (myOrder == null || myOrder.isEmpty())
			return;
		
		String formatForPrinting = "Item: %20s, quantity: %20d";
		
		for( WebShopItem itemInMyCart : myOrder ) {
			System.out.println( String.format(formatForPrinting, itemInMyCart.getItemName(), itemInMyCart.getQuantity()) );
		}

	} // end showMyOrerList
	
	
	
	
	public boolean isOrderListEmpty() {
		
		initOrderListIfNull();
		
		return myOrder.isEmpty();
		
	}
	
	
	private void initOrderListIfNull() {
		if (myOrder == null) 
			myOrder = new ArrayList<WebShopItem>();
	}
	
	
	
	public List<WebShopItem> getOrderList() {
		
		initOrderListIfNull();
		
		return myOrder;
	}
	
	
	public static boolean itemIDsMatch(String item1ID, String item2ID) {
		return item1ID != null && item2ID != null && !item1ID.trim().equals("") && item1ID.equals(item2ID);
	}
	
	
	
	public void printOrderList() {
		
		initOrderListIfNull();
		
		String format = "%-8s [$%-6s] -------------------- x %-2d";
		for(WebShopItem item : myOrder) {
			if (item.getItemName() == null ) continue;			
			System.out.println(String.format(format, item.getItemName(), item.getItemPrice(), item.getQuantity()));
		}
	}
	
	
} // end class MyChart