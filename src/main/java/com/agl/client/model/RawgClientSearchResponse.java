package com.agl.client.model;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Introspected
public class RawgClientSearchResponse {

    private Integer count;

    private String next;

    private String previous;

    private List<RawgClientGameInfoResponse> results;

}
