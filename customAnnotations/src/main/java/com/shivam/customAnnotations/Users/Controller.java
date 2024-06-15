package com.shivam.customAnnotations.Users;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class Controller {

    private final Services service;

    @Autowired
    public Controller(Services service) {
        this.service = service;
    }


    @Query
    @PostMapping("/")
    public ResponseEntity<?> handleGetAllUserDataRequest() {
      return  ResponseEntity.ok(service.myMethod(1));
    }
}
