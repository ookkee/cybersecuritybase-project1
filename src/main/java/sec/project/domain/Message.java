package sec.project.domain;

import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Message extends AbstractPersistable<Long> {
    
    private String name;
    private String msg;
    
    public Message() {
        super();
    }
    
    public Message(String name, String msg) {
        this();
        this.name = name;
        this.msg = msg;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
}
