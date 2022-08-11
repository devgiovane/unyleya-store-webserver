package com.github.giovane.webserver.service.product.storage;

import com.github.giovane.webserver.dto.ProductDto;
import com.github.giovane.webserver.entity.Product;
import com.github.giovane.webserver.factory.NotificationError;
import com.github.giovane.webserver.repository.IProductRepository;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public final class ProductStorage {

    private final IProductRepository repository;

    public ProductStorage(IProductRepository repository) {
        this.repository = repository;
    }

    public boolean save(NotificationError notificationError, ProductDto productDto) {
        try {
            Product product = new Product();
            product.setName(productDto.name());
            product.setPrice(productDto.price());
            repository.save(product);
            return true;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            notificationError.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            notificationError.setErrors("internal", "error in create product");
            return false;
        }
    }

    public List<Product> find(NotificationError notificationError) {
        try {
            List<Product> productDocuments = this.repository.findAll();
            if(productDocuments.isEmpty()) {
                notificationError.setStatusCode(HttpStatus.NOT_FOUND);
                return new ArrayList<>();
            }
            return productDocuments;
        } catch (Exception exception) {
            System.out.print(exception.getMessage());
            notificationError.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            notificationError.setErrors("internal", "error in list all product");
            return new ArrayList<>();
        }
    }

    public boolean update(NotificationError notificationError, Product product, ProductDto productDto) {
        try {
            product.setName(productDto.name());
            product.setPrice(productDto.price());
            repository.save(product);
            return true;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            notificationError.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            notificationError.setErrors("internal", "error in update product");
            return false;
        }
    }

    public boolean delete(NotificationError notificationError, Product product) {
        try {
            repository.delete(product);
            return true;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            notificationError.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            notificationError.setErrors("internal", "error in delete product");
            return false;
        }
    }
}
