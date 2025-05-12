package ncsu.Forum_Backend_Major;

import java.util.ArrayList;



import jakarta.persistence.*;
import ncsu.Forum_Backend_Classes.Classes;
import ncsu.Forum_Backend_Message.Message;


@Entity
public class Major {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // One major has many classes
    @OneToMany(mappedBy = "major", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<Classes> classes = new ArrayList<>();

    // Optional: chat messages within the major-wide chat
    @OneToMany(mappedBy = "major", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<Message> messages = new ArrayList<>();

	public Major(Long id, String name, ArrayList<Classes> classGroups, ArrayList<Message> messages) {
		setId(id);
		setName(name);
		setClassGroups(classGroups);
		setMessages(messages);
	}
	public Major(String name, ArrayList<Classes> classGroups, ArrayList<Message> messages) {
		this(null, name, classGroups, messages);
	}
	public Major() {
		this(null, null, null, null);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Classes> getClasses() {
		return classes;
	}

	public void setClassGroups(ArrayList<Classes> classGroups) {
		if(classGroups == null) {
			return;
		}
		this.classes = classGroups;
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
	public void addClasses(Classes classAdd) {
		if(classAdd == null) {
			throw new IllegalArgumentException("Can't add null class");
		}
		if(classes.contains(classAdd)) {
			throw new IllegalArgumentException("Can't add duplicate class");
		}
		classes.add(classAdd);
	}
	public void removeClasses(Classes classRemove) {
		classes.remove(classRemove);
	}

}
