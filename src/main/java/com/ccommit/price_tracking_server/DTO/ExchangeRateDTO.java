package com.ccommit.price_tracking_server.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateDTO {

    @JsonProperty("source")
    private String source;

    @JsonProperty("quotes")
    private Map<String, Double> quotes;

}
