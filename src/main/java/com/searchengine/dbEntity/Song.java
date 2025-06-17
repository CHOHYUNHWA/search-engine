package com.searchengine.dbEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "song")
public class Song {

    @Id
    private String id;

    private String keyword;

    private Long songId;

    private String title;

    private Long albumId;

    private String artistName;

    private String albumName;

    private String albumCoverImg;


}
