package com.github.giovane.webserver.repository;

import com.github.giovane.webserver.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProductRepository extends MongoRepository<Product, Long> {

    @Query("{_id: ?0}")
    Optional<Product> findById(String id);

    @Query("{name:'?0'}")
    Optional<Product> findByName(String name);

}
