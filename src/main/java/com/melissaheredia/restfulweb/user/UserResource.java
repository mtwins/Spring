package com.melissaheredia.restfulweb.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserResource {
    @Autowired
    private UserDaoService service;

    //retrieveAllUsers
    @GetMapping(path = "/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    //retrieveUser(int id)
    @GetMapping(path = "/user/{id}")
    public User retrieveUser(@PathVariable Integer id){
        User user = service.findOne(id);
        if (user==null)
                throw new UserNotFoundException("id - " + id);
        return user;
    }

    //Creates
    //input-details of user
    //output Created&uri of created user
    @PostMapping(path = "/users")
    public ResponseEntity createUser(@RequestBody User user){
        User save = service.save(user);
        URI location= ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(save.getId()).toUri();
       return ResponseEntity.created(location).build();
    }
}
