package ncsu.Forum_Backend_Message;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.hibernate.annotations.Cascade;

import jakarta.persistence.*;
import ncsu.Forum_Backend_Classes.Classes;
import ncsu.Forum_Backend_Major.Major;
import ncsu.Forum_Backend_User.GroupChat;
import ncsu.Forum_Backend_User.User;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 1000)
    private String content;
    private LocalDateTime timestamp;
    @ElementCollection
    @CollectionTable(name = "message_attachments", joinColumns = @JoinColumn(name = "message_id"))
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN) // Optional with Hibernate
    private ArrayList<Attachment> attachments;

    @ManyToOne
    private User sender;

    @PrePersist
    private void onCreate() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }
    @Enumerated(EnumType.STRING)
    private MessageType type;

    public Message() {
		this(null, null, null, null, MessageType.DIRECT);
	}
    
	public Message(Long id, String content, LocalDateTime timestamp, User sender, MessageType type) {
		setId(id);
		setContent(content);
		setTimestamp(timestamp);
		setSender(sender);
		setType(type);
	}
	public Message(String content, LocalDateTime timestamp, User sender, MessageType type) {
		this(null, content, timestamp, sender, type);
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
	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		if(type == null) {
			throw new IllegalArgumentException("Message dosn't have a place");
		}
		this.type = type;
	}
	 public enum MessageType {
	        MAJOR,
	        CLASS,
	        GROUP,
	        DIRECT
	    }
	public void addAttatchment(Attachment application) {
		attachments.add(application);
	}
	public Attachment removeAttatchment(Attachment attatchment) {
		attachments.remove(attatchment);
		return attatchment;
	}
	public abstract void setReceiver(User receiver);
	public abstract void setGroupName(GroupChat group);
	public abstract void setClassName(Classes cls);
	public abstract void setMajor(Major major);
	@Embeddable
	public static class Attachment {
		String url;
	}
}

