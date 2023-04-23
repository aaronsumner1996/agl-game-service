package com.agl.controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserGameImages {

        @NotNull
        private String gameName;

        private String imageUrl;

}
