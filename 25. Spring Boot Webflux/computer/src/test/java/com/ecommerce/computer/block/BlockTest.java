package com.ecommerce.computer.block;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class BlockTest {

    @Test
    void threadSleepIsABlockingCall() {
        Mono.delay(Duration.ofSeconds(1))
                .flatMap(tick ->{
                    try {
                        Thread.sleep(10);
                        return Mono.just(true);
                    } catch (InterruptedException e) {
                        return Mono.error(e);
                    }
                })
                .as(StepVerifier::create)
                .verifyComplete();
    }
}
