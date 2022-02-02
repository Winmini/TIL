package chap03;

import java.time.LocalDate;

public class ExpiryDateCalculator {
	public LocalDate calculateExpiryDate(PayData payData) {
		if (payData.getFirstBillingDate().equals(payData.getBillingDate()))
			return payData.getBillingDate().plusMonths(1);
		return payData.getBillingDate()
			.plusMonths(1)
			.withDayOfMonth(payData.getFirstBillingDate().getDayOfMonth());
	}
}
