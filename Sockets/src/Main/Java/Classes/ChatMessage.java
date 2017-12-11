package Classes;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Gebruiker on 11-12-2017.
 */
public class ChatMessage implements Serializable {

    private String message;
    private Date date;

    //propertys
    public String getMessage() {return message;}
    public void setMessage(String message) {this.message = message;}
    public Date getDate() {return date;}
    public void setDate(Date date) {this.date = date;}

    //constructor
    public ChatMessage(String message)
    {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return this.message;
    }
}
