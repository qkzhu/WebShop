package com.webshop.tools;

import java.math.BigDecimal;

import com.webshop.utils.*;


public interface ShopCartCalculator {
	
	// return sub total for a given cart 
	public BigDecimal getSubTotal(MyCart myCart);
	
	
	// return ground total for a given cart
	public BigDecimal getGroundTotal(MyCart myCart, String voucherCode);
	
	
	// return total amount savd by applying voucher code
	public BigDecimal getTotalAmountSavedFromVoucher(MyCart myCart, String voucherCode);
	
	
	// return total amount savd by bundle discount
	public BigDecimal getTotalAmountSavedFromBundle(MyCart myCart);
	
}