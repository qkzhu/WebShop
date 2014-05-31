package com.webshop.branch.singapore;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.webshop.promotions.Voucher;

public class VoucherSG extends Voucher {
	
	private List<String> __voucherCodes;
	

	public VoucherSG(String voucherCodeKey, Date dateExpired, BigDecimal voucherValue, 
			BigDecimal miniPurchase, int voucherValueType, boolean inlcudeShippingFee) {
		super(voucherCodeKey, dateExpired, voucherValue, miniPurchase, voucherValueType, inlcudeShippingFee);
	}
	
	/**
	 * For Singapore branch, it will generate a set of 5 vouchers based on one voucher key
	 */
	public void voucherCodeGenerator(String key) {
		// provide an algorithm to generate a valid voucher Code based on the given key.
		// hard code here for demo
		List<String> voucherCodeToGenerate = new ArrayList<String>();
		for(int i = 1; i <= 5; i++) {
			voucherCodeToGenerate.add("voucherCode" + key + i);
		}
		setVoucherList(voucherCodeToGenerate);
	}
	
	public boolean voucherCodeChecker(String voucherCode) {
		if (voucherCode == null || getVoucherList() == null || getVoucherList().size() == 0) return false;
		
		for(String availableVoucherCode : getVoucherList()) {
			if (availableVoucherCode.equals(voucherCode))
				return true;
		}
		
		return false;
	}
	
	
	public void setVoucherList(List<String> voucherCodes) { __voucherCodes = voucherCodes; }
	public List<String> getVoucherList() { return __voucherCodes; }
}