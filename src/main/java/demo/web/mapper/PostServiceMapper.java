package demo.web.mapper;


import demo.web.model.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface PostServiceMapper {

    public List<Post> FindAllPost();

    public void SavePost(Post post);

    public Post GetPostbyId(@Param("id") int id);

    public void SaveFilebyId(@Param("id") int id,@Param("path") String path,@Param("filename")String name);

    public List<String> GetFilebyID(@Param("id")int id);

    public String GetFilepath(@Param("id")int id,@Param("filename") String name);
}
