package com.ecommerce.computer.repository.item;

import lombok.Data;

@Data
public
class SearchParameter {
    private String keyword;
    private double price;
    private String condition;
}
