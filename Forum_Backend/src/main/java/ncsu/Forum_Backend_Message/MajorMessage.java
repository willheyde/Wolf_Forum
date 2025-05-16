package ncsu.Forum_Backend_Message;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import ncsu.Forum_Backend_Classes.Classes;
import ncsu.Forum_Backend_Major.Major;
import ncsu.Forum_Backend_User.GroupChat;
import ncsu.Forum_Backend_User.User;
@Entity
public class MajorMessage extends Message {
	@ManyToOne
	private Major major;
	public MajorMessage() {
		super();
		setMajor(null);
	}
    
	public MajorMessage(Long id, String content, LocalDateTime timestamp, User sender, Major major) {
		super(id, content, timestamp, sender, MessageType.MAJOR);
		setMajor(major);
	}
	public Major getMajor() {
		return major;
	}
	public void setMajor(Major major) {
		this.major = major;
	}

	@Override
	public void setReceiver(User receiver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGroupName(GroupChat cls) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClassName(Classes cls) {
		// TODO Auto-generated method stub
		
	}
	
}
