package demo.web.controller;

import demo.web.filestorage.DefauleUser;
import demo.web.model.Gallery;
import demo.web.service.GalleryService;
import demo.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.extras.springsecurity4.auth.AuthUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@Controller
public class GalleryController {

    @Autowired
    DefauleUser defauleUser;

    @Autowired
    GalleryService galleryService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/get/gallery",method = RequestMethod.GET)
    public void GetGallery(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userImg = request.getParameter("path");
        try {
            FileInputStream hFile=new FileInputStream(userImg);
            int i=hFile.available();
            byte data[]=new byte[i];
            hFile.read(data);
            int test1;
            hFile.close();
            response.setContentType("image/*");
            OutputStream toClient=response.getOutputStream();
            toClient.write(data);
            toClient.close();
        }catch (IOException e){
            PrintWriter toClient=response.getWriter();
            response.setContentType("text/html;charset=gb2312");
            toClient.write("无法打开图片");
            toClient.close();
        }
    }

    @RequestMapping(value = "/down/gallery",method = RequestMethod.GET)
    public void DownLoadGallery(HttpServletRequest request, HttpServletResponse res){

        String filepath = request.getParameter("imgpath");
        String filename = filepath.substring(filepath.lastIndexOf("/")+1);
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
                int block;
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

    @RequestMapping(value = "/delete/garllery",method = RequestMethod.GET)
    public String deletegarllery(HttpServletRequest request){
        String username = AuthUtils.getAuthenticationObject().getName();
        int id = userService.SearchUserId(username);
        String path = request.getParameter("imgpath");
        galleryService.DeleteGallery(id,path);
        return "redirect:/user/gallery";
    }

}
