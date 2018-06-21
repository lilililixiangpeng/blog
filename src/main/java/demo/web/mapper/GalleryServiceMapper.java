package demo.web.mapper;

import demo.web.model.Gallery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GalleryServiceMapper {

    public List<Gallery> GetGallerybyId(@Param("user_id")int id);

    public void DeleteGallery(@Param("user_id")int id,@Param("path")String path);

}
