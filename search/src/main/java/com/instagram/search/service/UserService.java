package com.instagram.search.service;

import com.instagram.search.document.User;
import com.instagram.search.search.SearchRequestDto;

import java.util.List;

public interface UserService {
    List<User> search(SearchRequestDto searchRequestDto);
    User save(User user);
}
