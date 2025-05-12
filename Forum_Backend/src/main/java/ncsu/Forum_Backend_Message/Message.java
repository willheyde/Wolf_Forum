package ncsu.Forum_Backend_Message;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import ncsu.Forum_Backend_Classes.Classes;
import ncsu.Forum_Backend_Major.Major;
import ncsu.Forum_Backend_User.User;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "major_id", nullable = true)
    private Major major;

    @ManyToOne
    @JoinColumn(name = "classgroup_id", nullable = true)
    private Classes classTitle;
    @PrePersist
    protected void onCreate() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }
    public Message() {
		this(null,null,null,null,null,null);
	}
    
	public Message(Long id, String content, LocalDateTime timestamp, User sender, Major major, Classes classGroup) {
		this.id = id;
		this.content = content;
		this.timestamp = timestamp;
		this.sender = sender;
		this.major = major;
		this.classTitle = classGroup;
	}

	public Message(String content, LocalDateTime timestamp, User sender, Major major, Classes classGroup) {
		this(null, content, timestamp, sender, major, classGroup);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public Classes getClasses() {
		return classTitle;
	}

	public void setClassTitle(Classes classGroup) {
		this.classTitle = classGroup;
	}

}
