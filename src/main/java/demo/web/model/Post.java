package demo.web.model;

public class Post {

    private int id;
    private String postname;
    private String flag;
    private String content;
    private String date;
    private String subject;

    public Post(String postname, String flag, String content, String subject, String date) {
        this.postname = postname;
        this.flag = flag;
        this.content = content;
        this.date = date;
        this.subject = subject;
    }

    public Post(int id,String postname, String flag, String content, String subject, String date) {
        this.id = id;
        this.postname = postname;
        this.flag = flag;
        this.content = content;
        this.date = date;
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostname() {
        return postname;
    }

    public void setPostname(String postname) {
        this.postname = postname;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
