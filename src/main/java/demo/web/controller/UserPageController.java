package demo.web.controller;

import demo.web.filestorage.DefauleUser;
import demo.web.model.ChatMessage;
import demo.web.model.User;
import demo.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.extras.springsecurity4.auth.AuthUtils;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserPageController {

    @Autowired
    UserService userService;

    @Autowired
    DefauleUser defauleUser;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @RequestMapping("/getimgs")
    public void getimg(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = AuthUtils.getAuthenticationObject().getName();
        String userImg = userService.FindUserImg(username);
        try {
            FileInputStream hFile=new FileInputStream(userImg);
            int i=hFile.available();
            byte data[]=new byte[i];
            hFile.read(data);
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

    @RequestMapping(value = "/getfriendimg",method = RequestMethod.GET)
    public void getfriendimg(HttpServletRequest request, HttpServletResponse response)throws IOException{
        String username = request.getParameter("username");
        String userImg = userService.FindUserImg(username);
        try {
            FileInputStream hFile=new FileInputStream(userImg);
            int i=hFile.available();
            byte data[]=new byte[i];
            hFile.read(data);
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

    @RequestMapping(path = "/user/change",method = RequestMethod.POST)
    public String userchange(HttpServletRequest request){
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String username = AuthUtils.getAuthenticationObject().getName();
        int id = userService.SearchUserId(username);
        System.out.println(id+email+phone+password);
        User user = new User(id,password,phone,email);
        userService.UpdateUserbyUser(user);
        return "redirect:/logout";

    }

    @RequestMapping(path = "/user/userimgchange",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> userimgchange(HttpServletRequest request){
        String img = request.getParameter("img");
        String imgtrue = img.substring(23);
        String username = AuthUtils.getAuthenticationObject().getName();
        int id = userService.SearchUserId(username);
        String imgFilePath = defauleUser.getImgpath()+id+".jpg";//新生成的图片
        userService.updateimgpath(imgFilePath,id);
        try {
            //Base64解码
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = decoder.decodeBuffer(imgtrue);
            //生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
        }catch (Exception e){

        }
        Map<String,String> result = new HashMap<>();
        result.put("result","true");
        return result;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/channel/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage)
    {
        return chatMessage;
    }

    @MessageMapping("/chat.onetoone")
    public void onetoone(@Payload ChatMessage chatMessage){
        System.out.println(chatMessage.getSender());
        SimpleDateFormat df = new SimpleDateFormat("MM-dd HH:mm:ss");//设置日期格式
        chatMessage.setTime(df.format(new Date()));
        messagingTemplate.convertAndSendToUser(chatMessage.getSenderto(),
                    "/queue/notifications", chatMessage);
    }

    @RequestMapping(value = "/upload/file")
    @ResponseBody
    public Map<String,String> uploadfile(@RequestParam("files[]") MultipartFile file) throws IOException {

        File test = new File("F://"+file.getOriginalFilename());

        file.transferTo(test);

        System.out.println();

        Map<String,String> map = new HashMap<>();
        map.put("test","suncess");

        return map;
    }
}
