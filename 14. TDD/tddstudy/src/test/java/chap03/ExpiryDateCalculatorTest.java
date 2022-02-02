package chap03;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class ExpiryDateCalculatorTest {

	private void assertExpiryDate(LocalDate billingDate, int payAmount, LocalDate expectedExpiryDate) {
		ExpiryDateCalculator  calculator = new ExpiryDateCalculator();
		LocalDate realExpiryDate = calculator.calculateExpiryDate(billingDate, payAmount);
		assertEquals(expectedExpiryDate, realExpiryDate);
	}

	@Test
	void 만원_납부하면_한달_뒤가_만료일이_됨() {
		assertExpiryDate(LocalDate.of(2019, 3, 1), 10_000,
			LocalDate.of(2019, 4, 1));
		assertExpiryDate(LocalDate.of(2020, 5, 5), 10_000,
			LocalDate.of(2020, 6, 5));
	}
}
