package demo.web.service;


import demo.web.mapper.PostServiceMapper;
import demo.web.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    @Autowired
    PostServiceMapper postServiceMapper;

    public List<Post> FindAllPost(){
        return  postServiceMapper.FindAllPost();
    }

    public void SavePost(Post post){
        postServiceMapper.SavePost(post);
    }

   public Post GetPostbyId(int id){
        return postServiceMapper.GetPostbyId(id);
   }

   public void SaveFilebyId(int id,String path,String name){
       postServiceMapper.SaveFilebyId(id,path,name);
   }

    public List<String> GetFilebyID(int id){
       return postServiceMapper.GetFilebyID(id);
    }

    public String GetFilepath(int id, String name){
        return postServiceMapper.GetFilepath(id,name);
    }
}