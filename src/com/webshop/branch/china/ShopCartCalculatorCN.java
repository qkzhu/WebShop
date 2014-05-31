package com.webshop.branch.china;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.webshop.promotions.BundleDiscount;
import com.webshop.promotions.Voucher;
import com.webshop.tools.ShopCartCalculator;
import com.webshop.utils.MyCart;
import com.webshop.utils.WebShopItem;

public class ShopCartCalculatorCN implements ShopCartCalculator {
	
	private static final BigDecimal DEFAULT_TOTAL = new BigDecimal("0.00");
	private static final BigDecimal MIN_PURCHASE_FOR_FREE_SHIPPING = new BigDecimal("100.00");
	
	private List<Voucher>        __voucherList;
	private List<BundleDiscount> __bundleDiscountList;
	private BigDecimal           __shippingFee;
	private BigDecimal           __tax;
	private boolean              __voucherIncludeShippingFeeFlag = false;
	
	
	// set up business rules: vouchers, bundle discount, shipping fee, tax
	public void init(List<Voucher> voucherList, List<BundleDiscount> bundleDiscountList, BigDecimal shippingFee, BigDecimal tax) {
		setVoucherList(voucherList);
		setBundleDiscountList(bundleDiscountList);
		
		shippingFee = shippingFee == null ? DEFAULT_TOTAL : shippingFee;
		setShippingFee(shippingFee);
		
		tax = tax == null ? DEFAULT_TOTAL : tax;
		setTax(tax);
	}
	
	
	
	 
	
	/**
	 * Get subtotal from shopping list without deduction for any of vouchers, bundle discounts, shipping fee and tax
	 * 
	 * @param myCart
	 * @return
	 */
	public BigDecimal getSubTotal(MyCart myCart) {
		
		List<WebShopItem> shoppingList = myCart.getOrderList();
		
		if (myCart == null || shoppingList.isEmpty()) return DEFAULT_TOTAL;
		
		BigDecimal total = new BigDecimal("0.00");
		BigDecimal itemPrice = null;
		
		for( WebShopItem item : shoppingList ) {
			itemPrice = item.getItemPrice();
			total = total.add( itemPrice.multiply(new BigDecimal(item.getQuantity())) );
		}
		
		return total;
		
	} // end getTotalFromCart
		
		
	
	
	
	/**
	 * Core method to sum up everything.
	 * @param myCart
	 * @param voucherKey
	 * @return
	 */
	public BigDecimal getGroundTotal(MyCart myCart, String voucherKey) {
		
		BigDecimal total = getSubTotal(myCart);
		
		if ( total.compareTo(DEFAULT_TOTAL) != 1 ) return DEFAULT_TOTAL; // for case subTotal <= 0
		
		BigDecimal totalDeductionByBundleDiscount = getTotalAmountSavedFromBundle(myCart);
		BigDecimal totalDeductionByVoucher = getTotalAmountSavedFromVoucher(myCart, voucherKey);
		
		if ( !freeShipping(total) ) {
			if ( getVoucherIncludeShippingFeeFlag() ) 
				total = total.add(getShippingFee()).subtract(totalDeductionByBundleDiscount).subtract(totalDeductionByVoucher);
			else 
				total = total.subtract(totalDeductionByBundleDiscount).subtract(totalDeductionByVoucher).add(getShippingFee());
		}
		
		total = total.add( total.multiply(getTax()) );
		
		return total.setScale(2, BigDecimal.ROUND_HALF_UP);
		
	} // end getGroundTotal
		
	
	
	
	
	/**
	 * Get total voucher deduction by vouhcerKey and subtotal
	 * 
	 * @param myCart
	 * @param voucherKey
	 * @param subTotal
	 * @return the amount deduction by voucher, returns $0 if voucher is invalid.
	 */
	public BigDecimal getTotalAmountSavedFromVoucher(MyCart myCart, String voucherKey) {
		
		BigDecimal subTotal = getSubTotal(myCart);
		
		// return $0 if no item in shopping list or no voucher key given
		List<Voucher> availableVouchers = getVoucherList();
		if ( myCart == null || myCart.getOrderList().isEmpty() || availableVouchers == null || availableVouchers.isEmpty() 
				|| voucherKey == null || voucherKey.trim().equals("") ) return DEFAULT_TOTAL;
		
		for (Voucher voucher : availableVouchers) {
			if ( voucher.voucherIsReadyToUse(voucherKey, subTotal) ) {
				setVoucherIncludeShippingFeeFlag( voucher.getIncludeShippingFeeFlag() );
				return voucher.getVoucherValueType() == Voucher.VOUCHER_VALUE_FIX ? 
						voucher.getVoucherValue() : subTotal.multiply(voucher.getVoucherValue());
			}
		}
		
		return new BigDecimal("0.00");
		
	} // end getTotalDeductionByVoucher
	

	
	
	
	/**
	 * Get total bundle deduction for all items in shopping list
	 * 
	 * @param myCart
	 * @return
	 */
	public BigDecimal getTotalAmountSavedFromBundle(MyCart myCart) {
		
		List<BundleDiscount> bundleRules = getBundleDiscountList();
		
		if ( myCart == null || myCart.getOrderList().isEmpty() || bundleRules == null || bundleRules.isEmpty() ) return DEFAULT_TOTAL;
		
		Date today = new Date();
		BigDecimal result = new BigDecimal("0.00");
		
		BigDecimal amountDuductionForItem;
		
		for( WebShopItem item : myCart.getOrderList() ) {
			for(BundleDiscount bundleDiscount: bundleRules) {
				
				// verify bundle is matched and still available
				if ( MyCart.itemIDsMatch(item.getItemID(), bundleDiscount.getItemIDForBundle()) 
						&& today.before(bundleDiscount.getExpiredDate())) {
					
					int setOfBundle = item.getQuantity() / bundleDiscount.getQuanityPerBundle();
					if ( setOfBundle > 0 ) {
						amountDuductionForItem = bundleDiscount.getTotalDeductionPerBundle().multiply(new BigDecimal(setOfBundle));
						result = result.add(amountDuductionForItem);
					}
				}
			}
		} 
		
		return result;
		
	} // end getTotalDeductionByBundle
	
	
	
	/*
	 * Check if user's order meet the requirement for free shipping.
	 * 
	 * compare to returns -1 means less than minimum purchase requirement
	 */
	public boolean freeShipping(BigDecimal subtotalBeforeTaxBeforShippingFee) {
		return subtotalBeforeTaxBeforShippingFee.compareTo(MIN_PURCHASE_FOR_FREE_SHIPPING) != -1;
	}
	
	public void setVoucherList(List<Voucher> voucherList) { __voucherList = voucherList; }
	public List<Voucher> getVoucherList() { return __voucherList; }
	
	
	public void setBundleDiscountList(List<BundleDiscount> bundleDiscountList) { __bundleDiscountList = bundleDiscountList; }
	public List<BundleDiscount> getBundleDiscountList() { return __bundleDiscountList; }
	
	
	public void setShippingFee(BigDecimal shippingFee) { __shippingFee = shippingFee; }
	public BigDecimal getShippingFee() { return __shippingFee; }
	
	
	public void setTax(BigDecimal tax) { __tax = tax; }
	public BigDecimal getTax() { return __tax; }
	
	public void setVoucherIncludeShippingFeeFlag(boolean voucherIncludeShippingFeeFlag) { __voucherIncludeShippingFeeFlag = voucherIncludeShippingFeeFlag; }
	public boolean getVoucherIncludeShippingFeeFlag() { return __voucherIncludeShippingFeeFlag; }

}
