package com.agl.controller;

import com.agl.client.model.RawgClientSearchResponse;
import com.agl.controller.model.RawgSearchRequest;
import com.agl.controller.model.UserGameInfo;
import com.agl.service.GameService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import static io.micronaut.http.MediaType.APPLICATION_JSON;

@Controller
@Slf4j
public class GameController {

    private final GameService gameService;

    @Inject
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Put(value = "/api/games/search", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse<RawgClientSearchResponse> retrieveGameSearchByName(@Body RawgSearchRequest searchRequest) {
        log.info("retrieveGameSearchByName, retrieving search for: [{}] ", searchRequest.getSearchTerm());
        return HttpResponse.ok(gameService.retrieveGameSearchByName(searchRequest));
    }

    @Post(value = "/api/games/save", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse<UserGameInfo> saveGameInformationToUser(@Body UserGameInfo userGameInfo) {
        log.info("Saving game details against user,  for: [{}] ", userGameInfo.getUserId());
        return HttpResponse.created(gameService.saveGameInformationToUser(userGameInfo));
    }


}
