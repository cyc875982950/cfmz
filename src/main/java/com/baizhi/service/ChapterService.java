package com.baizhi.service;

import com.baizhi.entity.Chapter;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public interface ChapterService {
    Map<String,Object> findAll(Integer page,Integer rows,String albumid);
    void add(Chapter chapter, HttpSession session) throws ReadOnlyFileException, IOException, TagException, InvalidAudioFrameException, CannotReadException;
    void delete(String [] id);
    void upload(Chapter chapter);
    void updateAll(Chapter chapter);
    Integer count(String album_id);
}
