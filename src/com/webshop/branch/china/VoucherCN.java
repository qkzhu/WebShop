package com.webshop.branch.china;

import java.math.BigDecimal;
import java.util.Date;

import com.webshop.promotions.Voucher;

public class VoucherCN extends Voucher {
	
	private String __voucherCodeCN;
	
	public VoucherCN(String voucherCodeKey, Date dateExpired, BigDecimal voucherValue, 
			BigDecimal miniPurchase, int voucherValueType, boolean inlcudeShippingFee) {
		super(voucherCodeKey, dateExpired, voucherValue, miniPurchase, voucherValueType, inlcudeShippingFee);
	}
	
	public void voucherCodeGenerator(String key) {
		// provide an algorithm to generate a valid voucher Code based on the given key.
		// hard code here for demo
		setVoucherCode("voucherCode" + key);
	}
	
	public boolean voucherCodeChecker(String voucherCode) {
		return voucherCode != null && getVoucherCode() != null && voucherCode.equals(getVoucherCode());
	}
	
	public void setVoucherCode(String voucherCode) { __voucherCodeCN = voucherCode; }
	public String getVoucherCode() { return __voucherCodeCN; }
}
