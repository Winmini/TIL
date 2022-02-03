package chap03;

import java.time.LocalDate;
import java.time.YearMonth;

public class ExpiryDateCalculator {

	private static final int MONTHLY_FEE = 10_000;

	public LocalDate calculateExpiryDate(PayData payData) {
		int addedMonths = calculateAddedMonth(payData);
		LocalDate firstBillingDate = payData.getFirstBillingDate();
		LocalDate billingDate = payData.getBillingDate();

		if (firstBillingDate.equals(billingDate))
			return billingDate.plusMonths(addedMonths);

		return billingDate
			.plusMonths(addedMonths)
			.withDayOfMonth(Math.min(firstBillingDate.getDayOfMonth(),
				YearMonth.from(billingDate.plusMonths(addedMonths)).lengthOfMonth()));
	}

	private int calculateAddedMonth(PayData payData) {
		return payData.getPayAmount() / MONTHLY_FEE;
	}
}
