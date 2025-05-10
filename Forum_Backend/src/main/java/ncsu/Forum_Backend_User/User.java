package ncsu.Forum_Backend_User;


import jakarta.persistence.*;

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
    /**
     * The studnets unityID
     */
    private String unityId;
    private String displayName;
    private String email;
    private String bio;
    private String profilePictureUrl;
    /**
     * User constructor with ID
     * @param id unique Id of user
     * @param unityId the user ID of the user
     * @param displayName the display name of the user
     * @param email the email of the user
     * @param bio the users bio
     * @param profilePictureUrl the users profile picture
     */
    public User(Long id, String unityId, String displayName, String email, String bio, String profilePictureUrl) {
        setId(id);
        setUnityId(unityId);
        setDisplayName(displayName);
        setEmail(email);
        setBio(bio);
        setProfilePictureUrl(profilePictureUrl);
    }
    /**
     * User constructor without ID
     * @param unityId the user ID of the user
     * @param displayName the display name of the user
     * @param email the email of the user
     * @param bio the users bio
     * @param profilePictureUrl the users profile picture
     */
    public User(String unityId, String displayName, String email, String bio, String profilePictureUrl) {
        setUnityId(unityId);
        setDisplayName(displayName);
        setEmail(email);
        setBio(bio);
        setProfilePictureUrl(profilePictureUrl);
    }
	public Long getId() {
		return id;
	}
	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}
	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUnityId() {
		return unityId;
	}
	public void setUnityId(String unityId) {
		this.unityId = unityId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}

}
