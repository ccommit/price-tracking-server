package com.ccommit.price_tracking_server.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@Data
@Builder
public class GoogleProductRequest {

    @Builder.Default
    private String engine = "google_shopping";

    @JsonProperty("q")
    private String q;

    @Builder.Default
    private String google_domain = "google.co.kr";

    @Builder.Default
    private String gl = "kr";

    @Builder.Default
    private String hl = "ko";

    @NotBlank
    private String api_key;

    public String toQueryString() {
        StringBuilder sb = new StringBuilder();

        sb.append("engine=").append(engine)
                .append("&q=").append(encode(q))
                .append("&google_domain=").append(google_domain)
                .append("&gl=").append(gl)
                .append("&hl=").append(hl)
                .append("&api_key=").append(api_key);

        return sb.toString();
    }

    private String encode(String value) {
        if (value == null) return "";
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}