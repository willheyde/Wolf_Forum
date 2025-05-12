package ncsu.Forum_Backend_Classes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import ncsu.Forum_Backend_Message.Message;
import ncsu.Forum_Backend_User.User;

@Entity
public class Classes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseTitle; // e.g., CSC116

    // Chat messages in this class
    @OneToMany(mappedBy = "classGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<Message> messages = new ArrayList<>();

    // Optional: users who joined this class
    @ManyToMany(mappedBy = "joinedClasses")
    private Set<User> participants = new HashSet<>();
    private boolean chatEnabled = true;
	public Classes(Long id, String courseCode, ArrayList<Message> messages, Set<User> participants) {
		setId(id);
		setCourseTitle(courseCode);
		setMessages(messages);
		setParticipants(participants);
	}
	public Classes(String courseCode, ArrayList<Message> messages, Set<User> participants) {
		this(null, courseCode, messages, participants);
	}
	public Classes() {
		this(null, null, null, null);
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseCode) {
		this.courseTitle = courseCode;
	}
	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<Message> messages) {
		if(messages == null) {
			return;
		}
		this.messages = messages;
	}

	public Set<User> getParticipants() {
		return participants;
	}

	public void setParticipants(Set<User> participants) {
		if(participants == null) {
			return;
		}
		this.participants = participants;
	}
	public void addUser(User user) {
		participants.add(user);
	}
	public void removeUser(User user) {
		participants.remove(user);
	}
	public void removeUserByUnityId(String unityId) {
		User toRemove = null;
		for(User user : participants) {
			if(user.getUnityId().equals(unityId)) {
				toRemove = user;
			}
		}
		participants.remove(toRemove);
	}
	public boolean isChatEnabled() {
		return chatEnabled;
	}
	public void setChatEnabled(boolean chatEnabled) {
		this.chatEnabled = chatEnabled;
	}
}
