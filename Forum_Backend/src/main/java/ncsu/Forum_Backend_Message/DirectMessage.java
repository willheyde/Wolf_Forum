package ncsu.Forum_Backend_Message;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import ncsu.Forum_Backend_Classes.Classes;
import ncsu.Forum_Backend_Major.Major;
import ncsu.Forum_Backend_User.GroupChat;
import ncsu.Forum_Backend_User.User;
@Entity
public class DirectMessage extends Message {
	@ManyToOne
	private User reciever;
	public DirectMessage() {
		super();
		setReceiver(null);
	}
	public DirectMessage(Long id, String content, LocalDateTime timestamp, User sender, User reciever) {
		super(id, content, timestamp, sender, MessageType.DIRECT);
		setReceiver(reciever);
	}
	public User getReciever() {
		return reciever;
	}
	@Override
	public void setReceiver(User reciever) {
		this.reciever = reciever;
	}
	@Override
	public void setGroupName(GroupChat cls) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setClassName(Classes cls) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setMajor(Major major) {
		// TODO Auto-generated method stub
		
	}
	
}
