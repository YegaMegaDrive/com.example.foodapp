package com.example.controller;

import com.example.entities.Product;
import com.example.repositories.ProductRepo;
import com.example.to.controller.SimpleResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ProductList")
public class RequestControllerProductList {

    @Autowired
    private ProductRepo repo;

    @GetMapping("/All")
    public ResponseEntity getAllProducts(){
        return ResponseEntity.ok(repo.findAll());
    }

    @PostMapping("/Product/Add")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity addProduct(@RequestBody Product list){
        repo.save(list);
        return ResponseEntity.ok(new SimpleResp());
    }

    @GetMapping("/Product/Delete")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity deleteProduct(@RequestParam String name){
        Product product = repo.findProductByName(name);
        repo.delete(product);
        return ResponseEntity.ok(new SimpleResp());
    }

    @PostMapping("/Product/Update")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity updateProduct(@RequestBody Product list){
        repo.save(list);
        return ResponseEntity.ok(new SimpleResp());
    }

}
