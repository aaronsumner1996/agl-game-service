package com.agl.client.model;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Introspected
public class RawgClientResponsePlatformDetails {

    private Integer id;

    private String slug;

    private String name;

}
