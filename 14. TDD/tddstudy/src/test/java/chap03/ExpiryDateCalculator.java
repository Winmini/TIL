package chap03;

import java.time.LocalDate;

public class ExpiryDateCalculator {

	private static final int MONTHLY_FEE = 10_000;

	public LocalDate calculateExpiryDate(PayData payData) {
		int addedMonths = calculateAddedMonth(payData);

		if (payData.getFirstBillingDate().equals(payData.getBillingDate()))
			return payData.getBillingDate().plusMonths(addedMonths);

		return payData.getBillingDate()
			.plusMonths(addedMonths)
			.withDayOfMonth(payData.getFirstBillingDate().getDayOfMonth());
	}

	private int calculateAddedMonth(PayData payData){
		return payData.getPayAmount() / MONTHLY_FEE;
	}
}
