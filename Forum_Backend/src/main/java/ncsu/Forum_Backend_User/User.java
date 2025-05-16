package ncsu.Forum_Backend_User;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import ncsu.Forum_Backend_Classes.Classes;

/**
 * User class constructs a user
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Creates a unique ID
    /**
     * Stores the unique ID created above
     */
    private Long id;
    private boolean isStudent;
    /**
     * The students unityID
     */
    private String unityId;
    private String displayName;
    private String email;
    private String bio;
    private String profilePictureUrl;
    private ArrayList<Classes> classes = new ArrayList<>();
    /**
     * User constructor with ID
     * @param id unique Id of user
     * @param unityId the user ID of the user
     * @param displayName the display name of the user
     * @param email the email of the user
     * @param bio the users bio
     * @param profilePictureUrl the users profile picture
     */
    public User(Long id, String unityId, String displayName, String email, String bio, String profilePictureUrl, boolean isStudent) {
        setId(id);
        setUnityId(unityId);
        setDisplayName(displayName);
        setEmail(email);
        setBio(bio);
        setProfilePictureUrl(profilePictureUrl);
        setStudent(isStudent);
    }
	/**
     * User constructor without ID
     * @param unityId the user ID of the user
     * @param displayName the display name of the user
     * @param email the email of the user
     * @param bio the users bio
     * @param profilePictureUrl the users profile picture
     */
    public User(String unityId, String displayName, String email, String bio, String profilePictureUrl, boolean isStudent) {
        setUnityId(unityId);
        setDisplayName(displayName);
        setEmail(email);
        setBio(bio);
        setProfilePictureUrl(profilePictureUrl);
        setStudent(isStudent);

    }
    public User() {
    	this.unityId = null;
    	this.displayName = null;
    	this.bio = null;
    	this.email = null;
    	this.profilePictureUrl = null;
    	this.isStudent = false;
    	this.id = null;
    	
    }
    @ManyToMany
    @JoinTable(
        name = "user_friends", // name of the join table
        joinColumns = @JoinColumn(name = "user_id"), // owning user
        inverseJoinColumns = @JoinColumn(name = "friend_id") // the friend
    )
    private Set<User> friends = new HashSet<>();
	public Long getId() {
		return id;
	}
	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}
	public void setProfilePictureUrl(String profilePictureUrl) {
		if(profilePictureUrl == null || profilePictureUrl.length() == 0) {
			this.profilePictureUrl = null;
		}
		else{
			this.profilePictureUrl = profilePictureUrl;
		}
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUnityId() {
		return unityId;
	}
	public void setUnityId(String unityId) {
		if(unityId == null || unityId.length() == 0) {
			throw new IllegalArgumentException("Invalid UnityID");
		}
		this.unityId = unityId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		if(displayName == null || displayName.length() == 0) {
			throw new IllegalArgumentException("Invalid display name");
		}
		this.displayName = displayName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		if(email == null || email.length() == 0) {
			throw new IllegalArgumentException("Invalid email");
		}
//		if(email.substring(0, unityId.length()).equals(unityId)) {
//			throw new IllegalArgumentException("Email should be School Email");
//		}
		this.email = email;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public boolean isStudent() {
		return isStudent;
	}
	public void setStudent(boolean isStudnet) {
		this.isStudent = isStudnet;
	}
	public Set<User> getFriends() {
	       return friends;
	}

	public void setFriends(Set<User> friends) {
		this.friends = friends;
	}
    public ArrayList<Classes> getClasses() {
		return classes;
	}
	public void setClasses(ArrayList<Classes> classes) {
		this.classes = classes;
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
}
