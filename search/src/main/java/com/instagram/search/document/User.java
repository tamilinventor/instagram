package com.instagram.search.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "user")
@Setting(settingPath = "es-config/elastic-analyzer.json")
public class User {

    @Id
    @Field(type = FieldType.Text,  analyzer = "autocomplete_index", searchAnalyzer = "autocomplete_search")
    private String userId;

    @Field(type = FieldType.Text,  analyzer = "autocomplete_index", searchAnalyzer = "autocomplete_search")
    private String userName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
