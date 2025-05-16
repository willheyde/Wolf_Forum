package ncsu.Forum_Backend_Message;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import ncsu.Forum_Backend_Classes.Classes;
import ncsu.Forum_Backend_Major.Major;
import ncsu.Forum_Backend_User.GroupChat;
import ncsu.Forum_Backend_User.User;
@Entity
public class ClassMessage extends Message {
	@ManyToOne
	private Classes className;
	public ClassMessage() {
		super();
		setClassName(null);
		setType(MessageType.CLASS);
	}
	public ClassMessage(Long id, String content, LocalDateTime timestamp, User sender, Classes className) {
		super(id, content, timestamp, sender, MessageType.CLASS);
		setClassName(className);
	}
	public Classes getClassName() {
		return className;
	}
	public void setClassName(Classes className) {
		this.className = className;
	}
	@Override
	public void setReceiver(User receiver) {
		
	}
	@Override
	public void setGroupName(GroupChat cls) {
		
	}
	@Override
	public void setMajor(Major major) {
		// TODO Auto-generated method stub
		
	}
}

