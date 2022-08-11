package com.github.giovane.webserver.controller;

import com.github.giovane.webserver.dto.ProductDto;
import com.github.giovane.webserver.entity.Product;
import com.github.giovane.webserver.factory.NotificationError;
import com.github.giovane.webserver.repository.IProductRepository;
import com.github.giovane.webserver.service.product.storage.ProductStorage;
import com.github.giovane.webserver.service.product.validation.ProductExists;
import com.github.giovane.webserver.view.ProductView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/product")
public final class ProductController {

    public final ProductView productView;
    public final ProductExists productExists;
    public final ProductStorage productStorage;

    @Autowired
    public ProductController(IProductRepository repository) {
        this.productView = new ProductView();
        this.productExists = new ProductExists(repository);
        this.productStorage = new ProductStorage(repository);
    }

    @PostMapping("")
    public @ResponseBody ResponseEntity<String> create(@Valid ProductDto productDto, BindingResult bindingResult) {
        NotificationError notificationError = new NotificationError();
        if (bindingResult.hasErrors()) {
            notificationError.captureErrors(bindingResult);
            return new ResponseEntity<>(this.productView.showErrors(notificationError), notificationError.getStatusCode());
        }
        Optional<Product> productExist = this.productExists.byName(notificationError, productDto.name());
        if (productExist.isPresent()) {
            return new ResponseEntity<>(this.productView.showErrors(notificationError), notificationError.getStatusCode());
        }
        boolean isSave = this.productStorage.save(notificationError, productDto);
        if (isSave) {
            return new ResponseEntity<>("", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(this.productView.showErrors(notificationError), notificationError.getStatusCode());
    }

    @GetMapping("")
    public ResponseEntity<String> read() {
        NotificationError notificationError = new NotificationError();
        List<Product> products = this.productStorage.find(notificationError);
        if (!products.isEmpty()) {
            return new ResponseEntity<>(this.productView.show(products), HttpStatus.OK);
        }
        return new ResponseEntity<>(this.productView.showErrors(notificationError), notificationError.getStatusCode());
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> readOne(@PathVariable(name = "id") String id) {
        NotificationError notificationError = new NotificationError();
        Optional<Product> productExist = this.productExists.byId(notificationError, id);
        if (productExist.isEmpty()) {
            return new ResponseEntity<>(this.productView.showErrors(notificationError), notificationError.getStatusCode());
        }
        return new ResponseEntity<>(this.productView.showOne(productExist.get()), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable(name = "id") String id, @Valid ProductDto productDto, BindingResult bindingResult) {
        NotificationError notificationError = new NotificationError();
        if (bindingResult.hasErrors()) {
            notificationError.captureErrors(bindingResult);
            return new ResponseEntity<>(this.productView.showErrors(notificationError), notificationError.getStatusCode());
        }
        Optional<Product> productExist = this.productExists.byId(notificationError, id);
        if (productExist.isEmpty()) {
            return new ResponseEntity<>(this.productView.showErrors(notificationError), notificationError.getStatusCode());
        }
        boolean isEdited = this.productStorage.update(notificationError, productExist.get(), productDto);
        if (isEdited) {
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(this.productView.showErrors(notificationError), notificationError.getStatusCode());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") String id) {
        NotificationError notificationError = new NotificationError();
        Optional<Product> productExist = this.productExists.byId(notificationError, id);
        if (productExist.isEmpty()) {
            return new ResponseEntity<>(this.productView.showErrors(notificationError), notificationError.getStatusCode());
        }
        boolean isDeleted = this.productStorage.delete(notificationError, productExist.get());
        if (isDeleted) {
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(this.productView.showErrors(notificationError), notificationError.getStatusCode());
    }

}
