package com.searchengine.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.searchengine.dto.SearchCondition;
import com.searchengine.entity.Song;
import com.searchengine.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchEngineService {

    private final ElasticsearchClient elasticsearchClient;
    private final SongRepository songRepository;

    public List<Song> searchSongs(SearchCondition searchCondition, int page, int size){

        String keyword = searchCondition.getKeyword();
        String searchType = "keyword";
        String sortField = "issuedDt";

        SortOrder sortOrder = "asc".equalsIgnoreCase(searchCondition.getSortOrder()) ? SortOrder.Asc : SortOrder.Desc;

        SearchRequest request = SearchRequest.of(s -> s
                .index("song")
                .query(q -> q
                        .wildcard(w -> w
                                .field(searchType)
                                .value("*" + keyword + "*")  // 부분 검색을 위한 와일드카드
                        )
                )
                .sort(sort -> sort
                        .field(f -> f
                                .field(sortField)
                                .order(sortOrder)
                        )
                )
                .from(page * size)
                .size(size)
        );

        try {
            SearchResponse<Song> response = elasticsearchClient.search(request, Song.class);

            return response.hits().hits().stream()
                    .map(Hit::source)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException("Elasticsearch 검색 실패", e);
        }

    }

    public void registerIndex(Song song) throws IOException {

        song.setId(UUID.randomUUID().toString());
        song.setCreateDt(new Date());        // 현재 시각으로 설정
        song.setUpdateDt(new Date());        // 동일
        song.setIssuedDt(new Date());        // 동일
        // Elasticsearch에 저장
        elasticsearchClient.index(IndexRequest.of(i -> i
                .index("song")
                .document(song)
        ));
    }

    public void registerIndexFromDb() throws IOException {

        List<com.searchengine.dbEntity.Song> all = songRepository.findAll();

        log.info("총 등록 인덱스 수 : {}" , all.size());
        int index = 1;

        for (com.searchengine.dbEntity.Song song : all) {

            Song indexSong = new Song();

            indexSong.setId(song.getId());
            indexSong.setKeyword(song.getKeyword());
            indexSong.setSongId(song.getSongId());
            indexSong.setTitle(song.getTitle());
            indexSong.setAlbumId(song.getAlbumId());
            indexSong.setArtistName(song.getArtistName());
            indexSong.setAlbumName(song.getAlbumName());
            indexSong.setAlbumCoverImg(song.getAlbumCoverImg());

            indexSong.setCreateDt(new Date());        // 현재 시각으로 설정
            indexSong.setUpdateDt(new Date());        // 동일
            indexSong.setIssuedDt(new Date());        // 동일
            // Elasticsearch에 저장
            elasticsearchClient.index(IndexRequest.of(i -> i
                    .index("song")
                    .document(indexSong)
            ));

            log.info("{}개 인덱스 등록 완료", ++index);
        }

    }


    public long countSongs(SearchCondition searchCondition) {
        String keyword = searchCondition.getKeyword();
        String searchType = "keyword";

        try {
            return elasticsearchClient.count(c -> c
                    .index("song")
                    .query(q -> q
                            .match(m -> m
                                    .field(searchType)
                                    .query(keyword)
                            )
                    )
            ).count();
        } catch (IOException e) {
            throw new RuntimeException("Elasticsearch count 실패", e);
        }
    }
}
