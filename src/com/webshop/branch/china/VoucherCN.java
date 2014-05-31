package com.webshop.branch.china;

import java.math.BigDecimal;
import java.util.Date;

import com.webshop.promotions.Voucher;

public class VoucherCN extends Voucher {
	
	
	public VoucherCN(Date dateExpired, BigDecimal voucherValue, 
			BigDecimal miniPurchase, int voucherValueType, boolean inlcudeShippingFee) {
		super(dateExpired, voucherValue, miniPurchase, voucherValueType, inlcudeShippingFee);
	}
	
	public String voucherCodeGenerator() {
		return null;
	}
	
	public boolean voucherCodeChecker(String voucherCode) {
		return false;
	}
}
