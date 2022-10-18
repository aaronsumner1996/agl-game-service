package com.agl.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Introspected
public class RawgClientGameInfoResponse {

    private Integer id;

    private String slug;

    private String name;

    private String released;

    private String description;

    private Boolean tba;

    private BigDecimal rating;

    @JsonProperty(value = "background_image")
    private String backgroundImage;

    private List<RawgClientResponsePlatform> platforms;

}
