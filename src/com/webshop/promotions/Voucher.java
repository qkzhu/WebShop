package com.webshop.promotions;

import java.util.Date;
import java.math.BigDecimal;

public abstract class Voucher {

	public static final int VOUCHER_VALUE_FIX = 1;
	public static final int VOUCHER_VALUE_PERCENTAGE = 2;
	
	private Date       __dateExpired;
	private BigDecimal __voucherValue;
	private BigDecimal __minPurchase;
	private int        __voucherValueType;
	private boolean    __includeShippingFee = false;
	
	/**
	 * Generator a valid voucher code base on customized code generating algorithm.
	 * @return: a valid voucher code.
	 */
	public abstract String voucherCodeGenerator();

	
	/**
	 * Check whether the given voucher code is valid.
	 * @param voucherCode
	 * @return true if voucher code is valid, else false.
	 */
	public abstract boolean voucherCodeChecker(String voucherCode); 
	
	
	
	/**
	 * Construct a voucher
	 * @param key
	 * @param dateExpired
	 * @param voucherValue
	 * @param minPurchase
	 * @param voucherValueType
	 * @param inlcudeShippingFee
	 */
	public Voucher(Date dateExpired, BigDecimal voucherValue, BigDecimal minPurchase, 
			int voucherValueType, boolean inlcudeShippingFee) {
		setDateExpired(dateExpired);
		setVoucherValue(voucherValue);
		setMinPurchase(minPurchase);
		setVoucherValueType(voucherValueType);
		setIncludeShippingFeeFlag(inlcudeShippingFee);
	}
	
	
	/**
	 * A voucher is ready to use if:
	 * 1. voucher code is valid.
	 * 2. sub total is more than Minimun amount of purchased.
	 * 3. meet voucher minimun amount of purchase reqirement.
	 * 
	 * @param voucher code 
	 * @param subTotal in BidDecimal
	 * @return true if the given voucher meets all requirments, else false;
	 */
	public boolean voucherIsReadyToUse(String voucherCode, BigDecimal subTotal) {
		if ( getMinPurchase().compareTo(new BigDecimal("0.00")) == 0 )
			return voucherCodeChecker(voucherCode);
		else return voucherCodeChecker(voucherCode) 
				&& (subTotal.compareTo(getMinPurchase()) == 0 || subTotal.compareTo(getMinPurchase()) == 1);
	}
	
	
	public void setDateExpired(Date dateExpired) { __dateExpired = dateExpired; }	
	public Date getDateExpired() { return __dateExpired; }
	
	
	public void setVoucherValue(BigDecimal voucherValue) { __voucherValue = voucherValue; }
	public BigDecimal getVoucherValue() { return __voucherValue; }
	
	
	public void setVoucherValueType(int voucherValueType) { __voucherValueType = voucherValueType; }
	public int getVoucherValueType() { return __voucherValueType; }
	
	
	public void setMinPurchase(BigDecimal minPurchase) {
		if ( minPurchase == null ) __minPurchase = new BigDecimal("0.00"); 
		else __minPurchase = minPurchase;
	}
	public BigDecimal getMinPurchase() {
		return __minPurchase == null ? new BigDecimal("0.00") : __minPurchase;
	}
	
	
	public void setIncludeShippingFeeFlag(boolean flag) { __includeShippingFee = flag; }
	public boolean getIncludeShippingFeeFlag() { return __includeShippingFee; }
	
} // end of class Voucher