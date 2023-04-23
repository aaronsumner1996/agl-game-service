package com.agl.service;

import com.agl.client.GameAdapterClientFacade;
import com.agl.client.model.RawgClientGameInfoResponse;
import com.agl.client.model.RawgClientSearchResponse;
import com.agl.controller.model.RawgSearchRequest;
import com.agl.controller.model.UserGameImages;
import com.agl.controller.model.UserGameInfo;
import com.agl.persistence.UserGameDao;
import com.agl.persistence.entity.UserGameInfoEntity;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.exceptions.HttpStatusException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public RawgClientSearchResponse retrieveGameSearchByName(RawgSearchRequest rawgSearchRequest) {
        log.info("RetrieveGame Info Facade, retrieving: [{}]", rawgSearchRequest);
        return gameAdapterClientFacade.retrieveGameSearchByName(rawgSearchRequest);
    }

    public List<UserGameInfo> retrieveAllGamesForUser(String userId) {
        log.info("Retrieving all games for user: [{}]", userId);
        try {
            List<UserGameInfoEntity> entityList = userGameDao.findByAllGamesForUser(userId);
            return entityList.stream().map(this::mapToUserGameInfoResponse).toList();
        } catch (Exception e) {
            throw new HttpStatusException(HttpStatus.NOT_FOUND, "unable to retrieve games for user");
        }
    }

    public List<UserGameImages> retrieveAllGameImagesForUser(String userId) {
        log.info("Retrieving all games images for user: [{}]", userId);
        try {
            List<UserGameInfoEntity> entityList = userGameDao.findByAllGamesForUser(userId);
            return entityList.stream().map(userGameInfoEntity -> mapToUserGameImages(
                            gameAdapterClientFacade.retrieveGameInfo(userGameInfoEntity.getGameSlug())))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new HttpStatusException(HttpStatus.NOT_FOUND, "unable to retrieve games for user");
        }
    }

    public UserGameInfo saveGameInformationToUser(UserGameInfo userGameInfo) {

        //Check if the user has this game currently saved
        checkForExistingGame(userGameInfo);

        //Map request to gameEntity
        UserGameInfoEntity gameInfoEntity = mapToUserGameInfoEntity(userGameInfo);

        //create Object in DB
        gameInfoEntity = userGameDao.create(gameInfoEntity);

        //Map the game entity to the game Response
        return mapToUserGameInfoResponse(gameInfoEntity);
    }

    public UserGameInfo updateGameInformationToUser(UserGameInfo userGameInfo) throws Exception {

        UserGameInfoEntity userGameInfoEntity = userGameDao.findByGameSlugForUser(userGameInfo.getGameSlug(),
                userGameInfo.getUserId());

        if (Objects.isNull(userGameInfoEntity)) {
            throw new HttpStatusException(HttpStatus.BAD_REQUEST, "No game found");
        }

        final String[] platforms = userGameInfo.getPlatforms() == null ? null
                : userGameInfo.getPlatforms().toArray(String[]::new);

        UserGameInfoEntity newGameEntity = UserGameInfoEntity.builder()
                .id(userGameInfoEntity.getId())
                .gameSlug(userGameInfoEntity.getGameSlug())
                .gameName(userGameInfoEntity.getGameName())
                .userId(userGameInfoEntity.getUserId())
                .platforms(Objects.isNull(platforms) ? userGameInfoEntity.getPlatforms() : platforms)
                .gameTime(Objects.isNull(userGameInfo.getGameTime()) ? Objects
                        .isNull(userGameInfoEntity.getGameTime()) ? null : userGameInfoEntity
                        .getGameTime() : userGameInfo.getGameTime().toString())
                .build();

        //update Object in DB
        userGameInfoEntity = userGameDao.update(newGameEntity);

        //Map the game entity to the game Response
        return mapToUserGameInfoResponse(userGameInfoEntity);
    }

    private UserGameInfo mapToUserGameInfoResponse(UserGameInfoEntity gameInfoEntity) {
        return UserGameInfo.builder()
                .gameName(gameInfoEntity.getGameName())
                .gameSlug(gameInfoEntity.getGameSlug())
                .platforms(Arrays.asList(gameInfoEntity.getPlatforms()))
                .userId(gameInfoEntity.getUserId())
                .gameTime(Objects.isNull(gameInfoEntity.getGameTime()) ?
                        null : new BigDecimal(gameInfoEntity.getGameTime()))
                .build();
    }

    private UserGameInfoEntity mapToUserGameInfoEntity(UserGameInfo userGameInfo) {
        final String[] platforms = userGameInfo.getPlatforms() == null ? new String[0]
                : userGameInfo.getPlatforms().toArray(String[]::new);

        return UserGameInfoEntity.builder()
                .id(UUID.randomUUID().toString())
                .gameName(userGameInfo.getGameName())
                .userId(userGameInfo.getUserId())
                .platforms(platforms)
                .gameTime(Objects.isNull(userGameInfo.getGameTime()) ? null : userGameInfo.getGameTime().toString())
                .gameSlug(userGameInfo.getGameSlug())
                .build();
    }

    private UserGameImages mapToUserGameImages(RawgClientGameInfoResponse gameInfoResponse) {
        return UserGameImages.builder()
                .gameName(gameInfoResponse.getName())
                .imageUrl(gameInfoResponse.getBackgroundImage())
                .build();
    }

    private void checkForExistingGame(UserGameInfo userGameInfo) {
        try {
            UserGameInfoEntity userGameInfoEntity = userGameDao.findByGameSlugForUser(userGameInfo.getGameSlug(),
                    userGameInfo.getUserId());
            if (!Objects.isNull(userGameInfoEntity)) {
                log.info("Game already exists");
                throw new HttpStatusException(HttpStatus.BAD_GATEWAY, "Game already exists for user");
            }
        } catch (Exception e) {
            throw new HttpStatusException(HttpStatus.BAD_GATEWAY, "Could not save game to DB");
        }
    }
}
