package com.github.giovane.webserver.view;

import com.github.giovane.webserver.entity.Product;
import com.github.giovane.webserver.factory.NotificationError;
import com.google.gson.Gson;

import java.util.List;

public final class ProductView {

    public String show(List<Product> products) {
        return new Gson().toJson(products);
    }

    public String showOne(Product product) {
        return new Gson().toJson(product);
    }

    public String showErrors(NotificationError notificationError) {
        if(!notificationError.getErrors().isEmpty()) {
            return new Gson().toJson(notificationError.getErrors());
        }
        return "";
    }

}
