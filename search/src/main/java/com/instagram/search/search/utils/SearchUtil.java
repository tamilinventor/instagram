package com.instagram.search.search.utils;

import com.instagram.search.search.SearchRequestDto;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class SearchUtil {
    private SearchUtil(){}


    public static SearchRequest buildSearchRequest(final String indexName, final SearchRequestDto searchRequestDto){
        try{
            final SearchSourceBuilder builder = new SearchSourceBuilder()
                    .postFilter(getQueryBuilder(searchRequestDto));
            SearchRequest searchRequest = new SearchRequest(indexName);
            searchRequest.source(builder);
            return searchRequest;
        }catch (final Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static QueryBuilder getQueryBuilder(final SearchRequestDto dto){
        if(dto == null)
            return null;

        final List<String> fields = dto.getFields();
        if(CollectionUtils.isEmpty(fields)){
            return null;
        }
        if(fields.size() > 1){
            MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(dto.getSearchTerm()).fuzziness(Fuzziness.AUTO);
//                 .type(MultiMatchQueryBuilder.Type.CROSS_FIELDS)
//                 .operator(Operator.AND);
            fields.forEach(queryBuilder::field);
            return queryBuilder;
        }
        return fields.stream()
                .findFirst()
                .map(field ->
                        QueryBuilders.matchQuery(field, dto.getSearchTerm())
                                .operator(Operator.AND))
                .orElse(null);
    }
}
