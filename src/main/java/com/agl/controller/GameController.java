package com.agl.controller;

import com.agl.client.model.RawgClientSearchResponse;
import com.agl.controller.model.RawgSearchRequest;
import com.agl.controller.model.UserGameImages;
import com.agl.controller.model.UserGameInfo;
import com.agl.service.GameService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static io.micronaut.http.MediaType.APPLICATION_JSON;

@Controller
@Slf4j
public class GameController {

    private final GameService gameService;

    @Inject
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Get(value = "/api/games/{userId}", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse<List<UserGameInfo>> retrieveAllGamesForUser(@PathVariable String userId) {
        log.info("retrieveAllGamesForUser, retrieving games for: [{}] ", userId);
        return HttpResponse.ok(gameService.retrieveAllGamesForUser(userId));
    }

    @Get(value = "/api/games/images/{userId}", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse<List<UserGameImages>> retrieveAllGamesImagesForUser(@PathVariable String userId) {
        log.info("retrieveAllGamesImagesForUser, retrieving game images for: [{}] ", userId);
        return HttpResponse.ok(gameService.retrieveAllGameImagesForUser(userId));
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


    @Put(value = "/api/games/save/update", consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
    @ExecuteOn(TaskExecutors.IO)
    public HttpResponse<UserGameInfo> updateGameInformationToUser(@Body UserGameInfo userGameInfo) throws Exception {
        log.info("Saving game details against user,  for: [{}] ", userGameInfo.getUserId());
        return HttpResponse.created(gameService.updateGameInformationToUser(userGameInfo));
    }

}
