package com.ecommerce.computer.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void itemBasicShouldWork() {
        Item item = Item.builder()
                .id("item1")
                .name("TV")
                .description("fun Item")
                .price(19.99)
                .build();

        assertThat(item.getId()).isEqualTo("item1");
        assertThat(item.getName()).isEqualTo("TV");
        assertThat(item.getDescription()).isEqualTo("fun Item");
        assertThat(item.getPrice()).isEqualTo(19.99);
        System.out.println("ss");

    }
}