package com.agl.persistence;

import com.agl.persistence.entity.UserGameInfoEntity;
import io.micronaut.transaction.annotation.TransactionalAdvice;

import javax.transaction.Transactional;

@Transactional
public class UserGameDao extends AbstractDao {

    @TransactionalAdvice
    public UserGameInfoEntity findByGameSlugForUser(final String gameSlug, final String userId) throws Exception {
        return findOne("SELECT games FROM UserGameInfoEntity games WHERE games.gameSlug=:gameSlug AND " +
                        "games.userId=:userId",
                UserGameInfoEntity.class,
                "gameSlug", gameSlug, "userId", userId);
    }

    @TransactionalAdvice
    @Transactional
    public UserGameInfoEntity create(final UserGameInfoEntity gameEntity) {
        entityManager.persist(gameEntity);
        return gameEntity;
    }

}
