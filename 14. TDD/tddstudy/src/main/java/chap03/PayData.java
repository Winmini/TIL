package chap03;

import java.time.LocalDate;

public class PayData {
	private final LocalDate firstBillingDate;
	private final LocalDate billingDate;
	private final int payAmount;

	public static class Builder {
		private final LocalDate firstBillingDate;
		private final int payAmount;

		private LocalDate billingDate;

		public Builder(LocalDate firstBillingDate, int payAmount) {
			this.firstBillingDate = firstBillingDate;
			this.billingDate = firstBillingDate;
			this.payAmount = payAmount;
		}

		public Builder billingDate(LocalDate billingDate) {
			this.billingDate = billingDate;
			return this;
		}

		public PayData build() {
			return new PayData(this);
		}
	}

	private PayData(Builder builder) {
		firstBillingDate = builder.firstBillingDate;
		billingDate = builder.billingDate;
		payAmount = builder.payAmount;
	}

	public LocalDate getFirstBillingDate() {
		return firstBillingDate;
	}

	public LocalDate getBillingDate() {
		return billingDate;
	}

	public int getPayAmount() {
		return payAmount;
	}
}
