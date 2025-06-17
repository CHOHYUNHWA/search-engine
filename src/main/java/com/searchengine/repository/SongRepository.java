package com.searchengine.repository;

import com.searchengine.dbEntity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, String> {

    List<Song> findAll();

}
