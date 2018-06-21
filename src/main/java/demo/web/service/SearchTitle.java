package demo.web.service;

import demo.web.mapper.TitleSearchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class SearchTitle {

    @Autowired
    TitleSearchMapper titleSearch;

    @Transactional
    public String searchtitle(String name){
        String title = titleSearch.searchtitle(name);
        return title;
    }
}