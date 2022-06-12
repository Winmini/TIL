package com.ecommerce.computer.repository.item;

import com.ecommerce.computer.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.support.SpringDataMongodbQuery;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
@RequiredArgsConstructor
public class ItemQueryRepository {


    public Flux<Item> findAllBySearchParameter(SearchParameter searchParameter){
        return null;
    }
}
