package com.ecommerce.computer.controller;

import com.ecommerce.computer.controller.response.SuccessResponse;
import com.ecommerce.computer.domain.Item;
import com.ecommerce.computer.service.InventoryService;
import com.ecommerce.computer.repository.item.SearchParameter;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("item")
@RequiredArgsConstructor
public class ItemController {

    private final InventoryService inventoryService;

    @GetMapping
    public Flux<SuccessResponse> getItem(SearchParameter searchParameter) {
        return inventoryService.getInventory(searchParameter)
                .map(GetItemResponseDto::new)
                .map(SuccessResponse::new);
    }

    @Data
    static class GetItemResponseDto {
        private String name;
        private String description;
        private double price;

        public GetItemResponseDto(Item item){
            name = item.getName();
            description = item.getDescription();
            price = item.getPrice();
        }
    }

}
