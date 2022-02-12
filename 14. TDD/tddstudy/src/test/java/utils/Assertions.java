package utils;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.function.Executable;

public class Assertions {
	private static final Duration SIMPLE_TEST_TIMEOUT = Duration.ofSeconds(1L);

	private Assertions() {
	}

	public static void assertSimpleTest(final Executable executable) {
		assertTimeoutPreemptively(SIMPLE_TEST_TIMEOUT, executable);
	}
}
