package com.ecommerce.computer.repository;

import com.ecommerce.computer.domain.Item;
import com.ecommerce.computer.repository.item.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class MongoDbSliceTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    void itemRepositorySaveTest() {
        Item sampleItem = Item.builder()
                .id("id")
                .name("name")
                .description("description")
                .price(19.99)
                .build();

        itemRepository.save(sampleItem)
                .as(StepVerifier::create)
                .expectNextMatches(item -> {
                    assertThat(item.getId()).isEqualTo("id");
                    assertThat(item.getName()).isEqualTo("name");
                    assertThat(item.getDescription()).isEqualTo("description");
                    assertThat(item.getPrice()).isEqualTo(19.99);
                    return true;
                })
                .verifyComplete();
    }
}
