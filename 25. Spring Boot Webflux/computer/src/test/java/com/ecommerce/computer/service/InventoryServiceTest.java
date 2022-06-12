package com.ecommerce.computer.service;

import com.ecommerce.computer.domain.Cart;
import com.ecommerce.computer.domain.CartItem;
import com.ecommerce.computer.domain.Item;
import com.ecommerce.computer.repository.CartRepository;
import com.ecommerce.computer.repository.item.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class InventoryServiceTest {

    @MockBean
    private ItemRepository itemRepository;
    @MockBean
    private CartRepository cartRepository;

    private InventoryService inventoryService;



    @BeforeEach
    void setUp() {
        Item sampleItem = Item.builder()
                .id("item1")
                .name("TV")
                .description("fun Item")
                .price(19.99)
                .build();

        CartItem sampleCartItem = new CartItem(sampleItem);
        Cart sampleCart = new Cart("My Cart");
        sampleCart.getCartItems().add(sampleCartItem);

        when(cartRepository.findById(anyString())).thenReturn(Mono.empty());
        when(itemRepository.findByName(anyString())).thenReturn(Mono.just(sampleItem));
        when(cartRepository.save(any(Cart.class))).thenReturn(Mono.just(sampleCart));

        inventoryService = new InventoryServiceImpl(cartRepository, itemRepository);
    }

    @Test
    void addItemToEmptyCartShouldProduceOneCartItem() {
        Item item = Item.builder()
                .id("item1")
                .name("TV")
                .description("fun Item")
                .price(19.99)
                .build();

        inventoryService.addToCart("My Cart", "TV")
                .as(StepVerifier::create)
                .expectNextMatches(cart -> {
                    assertThat(cart.getCartItems()).extracting(CartItem::getItem)
                            .containsExactly(item);
                    assertThat(cart.getCartItems()).extracting(CartItem::getQuantity)
                            .containsExactlyInAnyOrder(1);
                    return true;
                })
                .verifyComplete();
    }
}
