package com.agl.service;

import com.agl.client.GameAdapterClientFacade;
import com.agl.client.model.RawgClientSearchResponse;
import com.agl.controller.model.RawgSearchRequest;
import com.agl.controller.model.UserGameInfo;
import com.agl.persistence.UserGameDao;
import com.agl.persistence.entity.UserGameInfoEntity;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.exceptions.HttpStatusException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Singleton
@Slf4j
public class GameService {

    private final GameAdapterClientFacade gameAdapterClientFacade;
    private final UserGameDao userGameDao;

    @Inject
    public GameService(GameAdapterClientFacade gameAdapterClientFacade, UserGameDao userGameDao) {
        this.gameAdapterClientFacade = gameAdapterClientFacade;
        this.userGameDao = userGameDao;
    }

    public RawgClientSearchResponse retrieveGameSearchByName (RawgSearchRequest rawgSearchRequest) {
        log.info("RetrieveGame Info Facade, retrieving: [{}]", rawgSearchRequest);
        return gameAdapterClientFacade.retrieveGameSearchByName(rawgSearchRequest);
    }

    public UserGameInfo saveGameInformationToUser(UserGameInfo userGameInfo)  {

        //Check if the user has this game currently saved
        checkForExistingGame(userGameInfo);

        //Map request to gameEntity
        UserGameInfoEntity gameInfoEntity = mapToEntity(userGameInfo);

        //create Object in DB
        gameInfoEntity = userGameDao.create(gameInfoEntity);

        //Map the game entity to the game Response
        return mapToResponse(gameInfoEntity);
    }

    private UserGameInfo mapToResponse(UserGameInfoEntity gameInfoEntity) {
        return UserGameInfo.builder()
                .gameName(gameInfoEntity.getGameName())
                .gameSlug(gameInfoEntity.getGameSlug())
                .platforms(Arrays.asList(gameInfoEntity.getPlatforms()))
                .userId(gameInfoEntity.getUserId())
                .build();
    }

    private UserGameInfoEntity mapToEntity(UserGameInfo userGameInfo) {
        final String[] platforms = userGameInfo.getPlatforms() == null ? new String[0]
                : userGameInfo.getPlatforms().toArray(String[]:: new);

        return UserGameInfoEntity.builder()
                .id(UUID.randomUUID().toString())
                .gameName(userGameInfo.getGameName())
                .userId(userGameInfo.getUserId())
                .platforms(platforms)
                .gameSlug(userGameInfo.getGameSlug())
                .build();
    }

    private void checkForExistingGame(UserGameInfo userGameInfo) {
        try {
            UserGameInfoEntity userGameInfoEntity = userGameDao.findByGameSlugForUser(userGameInfo.getGameSlug(),
                    userGameInfo.getUserId());
            if(!Objects.isNull(userGameInfoEntity)) {
                log.info("Game already exists");
                throw new HttpStatusException(HttpStatus.BAD_GATEWAY, "Game already exists for user");
            }
        } catch (Exception e) {
            throw new HttpStatusException(HttpStatus.BAD_GATEWAY, "Could not save game to DB");
        }
    }
}
