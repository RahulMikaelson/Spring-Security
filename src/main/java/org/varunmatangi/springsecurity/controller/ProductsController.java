package org.varunmatangi.springsecurity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.varunmatangi.springsecurity.documents.Product;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("1","Monitor","Samsung 2k 22 inch Monitor"));
        products.add(new Product("2","Keyboard","Red Dragon keyboard model 512"));
        products.add(new Product("3","Laptop","Lenovo Laptop with 16GB RAM and 512 ROM"));

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
