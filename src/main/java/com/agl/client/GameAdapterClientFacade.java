package com.agl.client;

import com.agl.client.model.RawgClientGameInfoResponse;
import com.agl.client.model.RawgClientSearchResponse;
import com.agl.controller.model.RawgSearchRequest;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class GameAdapterClientFacade {

    private final GameAdapterClient gameAdapterClient;

    @Inject
    public GameAdapterClientFacade(GameAdapterClient gameAdapterClient) {
        this.gameAdapterClient = gameAdapterClient;
    }

    public RawgClientSearchResponse retrieveGameSearchByName (RawgSearchRequest rawgSearchRequest) {
        log.info("RetrieveGame Info Facade, retrieving: [{}]", rawgSearchRequest);
        return gameAdapterClient.retrieveGameInfoSearch(rawgSearchRequest).body();
    }

    public RawgClientGameInfoResponse retrieveGameInfo(String gameSlug) {
        log.info("RetrieveGame Info Facade, retrieving: [{}]", gameSlug);
        return gameAdapterClient.retrieveGameInfo(gameSlug).body();
    }

}
