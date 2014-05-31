package com.webshop.promotions;

import java.util.Date;
import java.math.BigDecimal;

public class BundleDiscount {

	private String     __itemIDForBundle;
	private BigDecimal __totalDeductionPerBundle;
	private int        __quanityPerBundle;
	private Date       __expiredDate;
	
	
	public BundleDiscount(String itemIDForBundle, BigDecimal totalDeductionPerBundle, int quanityPerBundle, Date expiredDate) {
		setItemIDForBundle(itemIDForBundle);
		setTotalDeducationPerBundle(totalDeductionPerBundle);
		setQuanityPerBundle(quanityPerBundle);
		setExpiredDate(expiredDate);
	}
	

	public void setItemIDForBundle(String itemIDForBundle) { 
		__itemIDForBundle = itemIDForBundle; 
	}
	public String getItemIDForBundle() { return __itemIDForBundle; }
	
	
	public void setTotalDeducationPerBundle(BigDecimal totalDeductionPerBundle) {
		__totalDeductionPerBundle = totalDeductionPerBundle;
	}
	public BigDecimal getTotalDeductionPerBundle() { return __totalDeductionPerBundle; }
	
	
	public void setQuanityPerBundle(int quanityPerBundle) { __quanityPerBundle = quanityPerBundle; }
	public int getQuanityPerBundle() { return __quanityPerBundle; }
	
	
	public void setExpiredDate(Date expiredDate) { 	__expiredDate = expiredDate; }
	public Date getExpiredDate() { return __expiredDate;}
	
} // end of class BundleDiscount