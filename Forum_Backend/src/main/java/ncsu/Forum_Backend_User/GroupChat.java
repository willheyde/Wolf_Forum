package ncsu.Forum_Backend_User;

import java.util.ArrayList;

import ncsu.Forum_Backend_Message.Message;

public class GroupChat {
	ArrayList<User> users = new ArrayList<User>();
	ArrayList<Message> messages = new ArrayList<Message>();
	String GroupTitle;
	public ArrayList<User> getUsers() {
		return users;
	}
	public GroupChat(String groupTitle) {
		super();
		GroupTitle = groupTitle;
	}
	public boolean addUser(User user) {
		if(user == null) {
			throw new IllegalArgumentException("Can't add a null user");
		}
		users.add(user);
		return true;
	}
	public User removeUser(User user) {
		if(user == null) {
			throw new IllegalArgumentException("Can't remove null argumnet");
		}
		users.remove(user);
		return user;
	}
	
}
