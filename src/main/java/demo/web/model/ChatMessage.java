package demo.web.model;
/**
 * Created by rajeevkumarsingh on 24/07/17.
 */
public class ChatMessage {
  /*  private MessageType type;*/
    private String content;
    private String sender;
    private String senderto;
    public String time;
    /*public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
*/
   /* public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }*/

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSenderto() {
        return senderto;
    }

    public void setSenderto(String senderto) {
        this.senderto = senderto;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
