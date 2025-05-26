package com.ccommit.price_tracking_server.DTO;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleProductResponse {

    @JsonProperty("position")
    private Integer position;

    @JsonProperty("title")
    private String title;

    @JsonProperty("immersive_product_page_token")
    private String immersiveProductPageToken;

    @JsonProperty("serpapi_immersive_product_api")
    private String serpapiImmersiveProductApi;

    @JsonProperty("product_link")
    private String productLink;

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("serpapi_product_api")
    private String serpapiProductApi;

    @JsonProperty("source")
    private String source;

    @JsonProperty("source_icon")
    private String sourceIcon;

    @JsonProperty("price")
    private String price;

    @JsonProperty("extracted_price")
    private Integer extractedPrice;

    @JsonProperty("old_price")
    private String oldPrice;

    @JsonProperty("extracted_old_price")
    private Integer extractedOldPrice;

    @JsonProperty("thumbnail")
    private String thumbnail;

    @JsonProperty("thumbnails")
    private List<String> thumbnails;

    @JsonProperty("serpapi_thumbnails")
    private List<String> serpapiThumbnails;

    @JsonProperty("tag")
    private String tag;

    @JsonProperty("extensions")
    private List<String> extensions;

    @JsonProperty("delivery")
    private String delivery;

    @JsonProperty("second_hand_condition")
    private String secondHandCondition;
}
