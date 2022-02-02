package chap03;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class ExpiryDateCalculatorTest {

	private void assertExpiryDate(PayData payData, LocalDate expectedExpiryDate) {
		ExpiryDateCalculator calculator = new ExpiryDateCalculator();
		LocalDate realExpiryDate = calculator.calculateExpiryDate(payData);
		assertEquals(expectedExpiryDate, realExpiryDate);
	}

	@Test
	void 만원_납부하면_한달_뒤가_만료일이_됨() {
		assertExpiryDate(
			new PayData
				.Builder(LocalDate.of(2019, 3, 1), 10_000)
				.build(),
			LocalDate.of(2019, 4, 1));

		assertExpiryDate(
			new PayData
				.Builder(LocalDate.of(2020, 5, 5), 10_000)
				.build(),
			LocalDate.of(2020, 6, 5));
	}

	@Test
	void 납부일과_한달_뒤_일자가_같지_않음() {
		assertExpiryDate(
			new PayData
				.Builder(LocalDate.of(2019, 1, 31), 10_000)
				.build(),
			LocalDate.of(2019, 2, 28));

		assertExpiryDate(
			new PayData
				.Builder(LocalDate.of(2019, 5, 31), 10_000)
				.build(),
			LocalDate.of(2019, 6, 30));

	}

}