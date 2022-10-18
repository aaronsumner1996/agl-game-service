package com.agl.client;

import com.agl.client.model.RawgClientSearchResponse;
import com.agl.controller.model.RawgSearchRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;

import static io.micronaut.http.MediaType.APPLICATION_JSON;

@Client(value = "${rawg-adapter.api.url}")
public interface GameAdapterClient {

    @Put(value = "/api/games/search", produces = APPLICATION_JSON)
    HttpResponse<RawgClientSearchResponse> retrieveGameInfo(@Body RawgSearchRequest rawgSearchRequest);
}
