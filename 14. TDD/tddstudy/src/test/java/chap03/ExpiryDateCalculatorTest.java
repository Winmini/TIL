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

	@Test
	void 첫_납부일과_만료일_일자가_다른_경우() {
		assertExpiryDate(
			new PayData
				.Builder(LocalDate.of(2019, 1, 31), 10_000)
				.billingDate(LocalDate.of(2019, 2, 28))
				.build(),
			LocalDate.of(2019, 3, 31));

		assertExpiryDate(
			new PayData
				.Builder(LocalDate.of(2019, 5, 31), 10_000)
				.billingDate(LocalDate.of(2019, 6, 30))
				.build(),
			LocalDate.of(2019, 7, 31));
	}

	@Test
	void 만원을_초과하여_납부() {
		assertExpiryDate(
			new PayData
				.Builder(LocalDate.of(2019, 1, 31), 20_000)
				.build(),
			LocalDate.of(2019, 3, 31));

		assertExpiryDate(
			new PayData
				.Builder(LocalDate.of(2019, 3, 11), 30_000)
				.build(),
			LocalDate.of(2019, 6, 11));
	}

	@Test
	void 첫_납부일과_만료일_일자가_다를_때_만원을_초과하여_납부() {
		assertExpiryDate(
			new PayData
				.Builder(LocalDate.of(2019, 1, 31), 20_000)
				.billingDate(LocalDate.of(2019, 2, 28))
				.build(),
			LocalDate.of(2019, 4, 30));
	}

	@Test
	void 십만원을_납부하면_1년_제공() {
		assertExpiryDate(
			new PayData
				.Builder(LocalDate.of(2019, 1, 31), 100_000)
				.build(),
			LocalDate.of(2020, 1, 31));
	}
}
