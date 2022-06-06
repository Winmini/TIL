package com.ecommerce.computer.controller;

import com.ecommerce.computer.domain.Cart;
import com.ecommerce.computer.domain.Item;
import com.ecommerce.computer.service.InventoryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@WebFluxTest(ShoppingController.class)
public class ShoppingControllerSliceTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    InventoryService inventoryService;

    @Test
    void basicTest() {
        when(inventoryService.getInventory()).thenReturn(Flux.just(
                Item.builder().id("id1").name("name1").description("desc1").price(1.99).build(),
                Item.builder().id("id2").name("name2").description("desc2").price(9.99).build()
        ));
        when(inventoryService.getCart("My Cart")).thenReturn(Mono.just(
                Cart.builder().id("My Cart").build()
        ));

        client.get().uri("/").exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(exchangeResult -> {
                    assertThat(exchangeResult.getResponseBody()).contains("action=\"/add/id1\"");
                    assertThat(exchangeResult.getResponseBody()).contains("action=\"/add/id2\"");
                });
    }
}
