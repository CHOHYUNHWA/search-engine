package com.searchengine.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.Date;


@Document(indexName = "song")
@Data
public class Song {

    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String keyword;

    @Field(type = FieldType.Long)
    private Long songId;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Long)
    private Long albumId;

    @Field(type = FieldType.Text)
    private String artistName;

    @Field(type = FieldType.Text)
    private String albumName;

    @Field(type = FieldType.Text)
    private String albumCoverImg;

    @Field(type = FieldType.Boolean)
    private boolean bandFlag;

    @Field(type = FieldType.Boolean)
    private boolean pianoFlag;

    @Field(type = FieldType.Boolean)
    private boolean melodyFlag;

    @Field(type = FieldType.Boolean)
    private boolean soloFlag;

    @Field(type = FieldType.Boolean)
    private boolean orchestraFlag;

    @Field(type = FieldType.Boolean)
    private boolean chorusFlag;

    @Field(type = FieldType.Boolean)
    private boolean mrFlag;

    @Field(type = FieldType.Date)
    private Date issuedDt;

    @Field(type = FieldType.Date)
    private Date createDt;

    @Field(type = FieldType.Date)
    private Date updateDt;

}
