package com.instagram.search.controller;

import com.instagram.search.document.User;
import com.instagram.search.search.SearchRequestDto;
import com.instagram.search.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/search")
public class ElasticSearchController {
    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public User save(User user){
        return userService.save(user);
    }

    @PostMapping
    public List<User> search(@RequestBody SearchRequestDto searchRequestDto){
        List<String> fields = new ArrayList<>();
        fields.add("userId");
        fields.add("userName");
        searchRequestDto.setFields(fields);
        return userService.search(searchRequestDto);
    }


}
