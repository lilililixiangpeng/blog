package demo.web.filestorage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("user")
public class DefauleUser {

    private String userimg = "upload-dir";

    private String imgpath = "upload-dir";

    private String postfile = "upload-dir";

    private String gallery = "upload-dir";

    public String getGallery() {
        return gallery;
    }

    public void setGallery(String gallery) {
        this.gallery = gallery;
    }

    public String getPostfile() {
        return postfile;
    }

    public void setPostfile(String postfile) {
        this.postfile = postfile;
    }

    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }
}
