package chap03;

import java.time.LocalDate;
import java.time.YearMonth;

public class ExpiryDateCalculator {

	private static final int MONTHLY_FEE = 10_000;
	private static final int BONUS_STANDARD_FEE = 100_000;
	private static final int BONUS_MONTH = 2;

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
		int addedMonth = payData.getPayAmount() / MONTHLY_FEE;
		if (payData.getPayAmount() < BONUS_STANDARD_FEE) {
			return addedMonth;
		}
		return addedMonth + (payData.getPayAmount() / BONUS_STANDARD_FEE) * BONUS_MONTH;
	}
}
