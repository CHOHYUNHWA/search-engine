package com.searchengine.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchCondition {

    private String searchType;
    private String sortField;
    private String sortOrder;
    private String keyword;

}
