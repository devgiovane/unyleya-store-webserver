package com.github.giovane.webserver.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "product")
public final class Product {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private double price;

}
