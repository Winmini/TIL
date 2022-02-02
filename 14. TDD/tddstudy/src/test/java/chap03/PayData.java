package chap03;

import java.time.LocalDate;

public class PayData {
	private final LocalDate billingDate;
	private final int payAmount;

	public static class Builder {
		private final LocalDate billingDate;
		private final int payAmount;

		public Builder(LocalDate billingDate, int payAmount) {
			this.billingDate = billingDate;
			this.payAmount = payAmount;
		}

		public PayData build() {
			return new PayData(this);
		}
	}

	private PayData(Builder builder) {
		billingDate = builder.billingDate;
		payAmount = builder.payAmount;
	}

	public LocalDate getBillingDate() {
		return billingDate;
	}

	public int getPayAmount() {
		return payAmount;
	}
}
