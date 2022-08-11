package com.github.giovane.webserver.service.product.validation;

import com.github.giovane.webserver.entity.Product;
import com.github.giovane.webserver.factory.NotificationError;
import com.github.giovane.webserver.repository.IProductRepository;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public final class ProductExists {

    private final IProductRepository repository;

    public ProductExists(IProductRepository repository) {
        this.repository = repository;
    }

    public Optional<Product> byId(NotificationError notificationError, String id) {
        Optional<Product> productExist = this.repository.findById(id);
        if (productExist.isEmpty()) {
            notificationError.setStatusCode(HttpStatus.NOT_FOUND);
            return productExist;
        }
        notificationError.setStatusCode(HttpStatus.CONFLICT);
        return productExist;
    }

    public Optional<Product> byName(NotificationError notificationError, String name) {
        Optional<Product> productExist = this.repository.findByName(name);
        if (productExist.isEmpty()) {
            notificationError.setStatusCode(HttpStatus.NOT_FOUND);
            return productExist;
        }
        notificationError.setStatusCode(HttpStatus.CONFLICT);
        return productExist;
    }

}
