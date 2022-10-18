package com.agl.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserGameInfo {

    @NotNull
    private String userId;

    @NotNull
    private String gameSlug;

    @NotNull
    private String gameName;

    private BigDecimal gameTime;

    private List<String> platforms;

}
