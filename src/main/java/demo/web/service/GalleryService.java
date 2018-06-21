package demo.web.service;

import demo.web.mapper.GalleryServiceMapper;
import demo.web.model.Gallery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;
import java.util.List;

@Service
public class GalleryService {

    @Autowired
    GalleryServiceMapper galleryServiceMapper;

    public List<Gallery> GetGallerybyId(int id){
        return galleryServiceMapper.GetGallerybyId(id);
    }

    public void DeleteGallery(int id,String path){
        galleryServiceMapper.DeleteGallery(id,path);
    }
}
