package com.searchengine.viewController;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.searchengine.dto.SearchCondition;
import com.searchengine.entity.Song;
import com.searchengine.repository.SongRepository;
import com.searchengine.service.SearchEngineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ViewController {


    private final SearchEngineService searchEngineService;


    @GetMapping("/")
    public String home() {
        return "index";  // 실제로는 /WEB-INF/views/index.jsp 렌더링
    }

    @GetMapping("/registerIndex")
    public String registerIndex() {

        return "registerIndex";

    }

    @PostMapping("/registerIndex")
    public String register(@ModelAttribute Song song) throws IOException {

        // 필수 데이터 세팅
        searchEngineService.registerIndex(song);

        return "redirect:/registerIndex?success=true";
    }

    @GetMapping("/searchPage")
    public String searchPage(){
        return "search";
    }

    @GetMapping("/search")
    public String search(@ModelAttribute SearchCondition searchCondition,
                         @RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "10") int size,
                         Model model) {
        List<Song> songs = searchEngineService.searchSongs(searchCondition, page, size);
        long totalCount = searchEngineService.countSongs(searchCondition); // 총 개수 계산 메서드 필요

        int totalPages = (int) Math.ceil((double) totalCount / size);

        model.addAttribute("songs", songs);
        model.addAttribute("currentPage", page);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("keyword", searchCondition.getKeyword());
        model.addAttribute("sortOrder", searchCondition.getSortOrder());

        return "search";
    }


    @GetMapping("/scheduleRegisterIndex")
    public void scheduleRegisterIndex() throws IOException {
        searchEngineService.registerIndexFromDb();;

    }

}
