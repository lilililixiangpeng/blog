package demo.web.controller;


import demo.web.filestorage.DefauleUser;
import demo.web.model.Post;
import demo.web.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.extras.springsecurity4.auth.AuthUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Action;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    DefauleUser defauleUser;

    @RequestMapping(path = "/post/save",method = RequestMethod.POST)
    public String PostSave(HttpServletRequest request, @RequestParam("file[]")List<MultipartFile> files,@RequestParam("img[]")List<MultipartFile> imgs) throws IOException {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("MMM dd", Locale.ENGLISH);
        String englishtime = df.format(date);
        String username = AuthUtils.getAuthenticationObject().getName();
        Post post = new Post(username,request.getParameter("flag"),request.getParameter("email-text"),request.getParameter("subject"),englishtime);
        postService.SavePost(post);
        int id = post.getId();
        String filepath = defauleUser.getPostfile()+id;
        if (files.size() != 0){
            File dir = new File(filepath);
            if(!dir.exists())//判断该文件是否存在
                dir.mkdir();
            for (MultipartFile f : files) {
                if (!f.getOriginalFilename().isEmpty()){
                    String filename = f.getOriginalFilename();
                    File file = new File(filepath +"/" +filename);
                    postService.SaveFilebyId(id,filepath +"/" +filename,f.getOriginalFilename());
                    f.transferTo(file);
                }
            }
        }
        if (imgs.size() != 0){
            for (MultipartFile i : imgs) {
                if (!i.getOriginalFilename().isEmpty()){
                    String imgname = i.getOriginalFilename();
                    File imgfile = new File(filepath +"/" +imgname);
                    i.transferTo(imgfile);
                    postService.SaveFilebyId(id,filepath +"/" +imgname,i.getOriginalFilename());
                }
            }
        }
        return "user/email-write";
    }

    @RequestMapping(path = "/post/download",method = RequestMethod.GET)
    public void downloadfile(HttpServletRequest request,HttpServletResponse res){
        int id = Integer.parseInt(request.getParameter("postid"));
        String filename = request.getParameter("filename");

        String filepath = postService.GetFilepath(id,filename);

        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + filename);

        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File(filepath)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
