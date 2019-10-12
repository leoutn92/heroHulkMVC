package com.hulkStore.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ArticleDTO {
    private int articleCode;
    private String hero;
    private String articleType;
    private String size;
    private int stock;
    private float price;
    private String description;
}
