package com.instagram.search.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instagram.search.document.User;
import com.instagram.search.repository.UserRepository;
import com.instagram.search.search.SearchRequestDto;
import com.instagram.search.search.utils.SearchUtil;
import com.instagram.search.service.UserService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    private static final com.fasterxml.jackson.databind.ObjectMapper MAPPER = new ObjectMapper();


    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> search(SearchRequestDto searchRequestDto) {
        final SearchRequest searchRequest = SearchUtil.buildSearchRequest("user", searchRequestDto);

        if(searchRequest == null){
            LOG.error("Failed to build search request");
            return Collections.emptyList();
        }

        try{
            final SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            final SearchHit[] searchHits = searchResponse.getHits().getHits();
            final List<User> users = new ArrayList<>(searchHits.length);
            for(SearchHit hit : searchHits){
                MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                users.add(MAPPER.readValue(hit.getSourceAsString(), User.class));
            }

            return users;
        }catch (Exception e){
            LOG.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
    }

