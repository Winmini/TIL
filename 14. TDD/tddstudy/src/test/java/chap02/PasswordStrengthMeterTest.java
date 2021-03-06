package chap02;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PasswordStrengthMeterTest {

	private final PasswordStrengthMeter meter = new PasswordStrengthMeter();

	private void assertStrength(String password, PasswordStrength expStr) {
		PasswordStrength result = meter.meter(password);
		assertEquals(expStr, result);
	}

	@Test
	void meterStrongTest() {
		assertStrength("ab12!@AB", PasswordStrength.STRONG);
		assertStrength("abc1!Add", PasswordStrength.STRONG);
	}

	@Test
	void meterLengthNormalTest() {
		assertStrength("ab12!@A", PasswordStrength.NORMAL);
	}

	@Test
	void meterNumberNormalTest() {
		assertStrength("ab!@!bqw@A", PasswordStrength.NORMAL);
	}

	@Test
	void meterUppercaseNormalTest() {
		assertStrength("ab12!@df", PasswordStrength.NORMAL);
	}

	@Test
	void meterLengthWeekTest() {
		assertStrength("password", PasswordStrength.WEEK);
	}

	@Test
	void meterNumberWeek() {
		assertStrength("12345", PasswordStrength.WEEK);
	}

	@Test
	void meterUppercaseWeek() {
		assertStrength("KEYS", PasswordStrength.WEEK);
	}

	@Test
	void meterTheOtherCase() {
		assertStrength("abc", PasswordStrength.WEEK);
	}

	@Test
	void validateNullInput() {
		assertStrength(null, PasswordStrength.INVALID);
	}

	@Test
	void validateEmptyInput() {
		assertStrength("", PasswordStrength.INVALID);
	}
}
