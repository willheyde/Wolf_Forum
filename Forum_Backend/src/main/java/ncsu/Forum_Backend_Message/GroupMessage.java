package ncsu.Forum_Backend_Message;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import ncsu.Forum_Backend_Classes.Classes;
import ncsu.Forum_Backend_Major.Major;
import ncsu.Forum_Backend_User.GroupChat;
import ncsu.Forum_Backend_User.User;
@Entity
public class GroupMessage extends Message {
	@ManyToOne
	private GroupChat groupName;
	public GroupMessage() {
		super();
		setGroupName(groupName);
	}
	public GroupMessage(Long id, String content, LocalDateTime timestamp, User sender, GroupChat groupName) {
		super(id, content, timestamp, sender, MessageType.GROUP);
		setGroupName(groupName);
	}
	public GroupChat getGroupName() {
		return groupName;
	}
	@Override
	public void setGroupName(GroupChat groupName) {
		this.groupName = groupName;
	}
	@Override
	public void setReceiver(User receiver) {
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
