package com.agl.persistence.entity;

import io.micronaut.core.annotation.Introspected;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.transaction.Transactional;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "games")
@Introspected
@Transactional
@Getter
public class UserGameInfoEntity implements Serializable {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "user_id", updatable = false, nullable = false)
    private String userId;

    @Column(name = "game_slug")
    private String gameSlug;

    @Column(name = "game_name")
    private String gameName;

    @Column(name = "game_time")
    private String gameTime;

    @Type(type = "com.agl.persistence.entity.PostgresSqlStringArrayType")
    @Column(name = "platforms")
    private String[] platforms;

}
