package ncsu.Forum_Backend_User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
/**
 * User controler takes HTML calls and returns JSON
 * @restController marks this as a REST API
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    /**
     * Creates instances of the interface at runtime completing the correct fuctions
     * @param userRepository the interface to create.
     */
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Add user
    @PostMapping
    public User addUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // Remove user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ Edit user (supports partial or full edits)
    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setDisplayName(updatedUser.getDisplayName());
            user.setEmail(updatedUser.getEmail());
            user.setBio(updatedUser.getBio());
            user.setProfilePictureUrl(updatedUser.getProfilePictureUrl());
            return ResponseEntity.ok(userRepository.save(user));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get by ID
    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get by Unity ID (custom search)
    @GetMapping("/unity/{unityId}")
    public ResponseEntity<User> getUserByUnityId(@PathVariable String unityId) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUnityId(unityId));
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/student-discussion/{unityId}")
    public ResponseEntity<?> studentArea(@PathVariable String unityId) {
        User user = userRepository.findByUnityId(unityId);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (!user.isStudent()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied: Faculty cannot access student-only area.");
        }

        return ResponseEntity.ok("Welcome to student discussion!");
    }
    @PutMapping("/{id}/add-friend/{friendId}")
    public ResponseEntity<String> addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        Optional<User> userOpt = userRepository.findById(id);
        Optional<User> friendOpt = userRepository.findById(friendId);
        if (userOpt.isEmpty() || friendOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("One or both users not found");
        }
        if (userOpt.isPresent() && friendOpt.isPresent()) {
            User user = userOpt.get();
            User friend = friendOpt.get();

            // Prevent duplicates and self-friendship
            if (user.getId().equals(friend.getId())) {
                return ResponseEntity.badRequest().body("You cannot befriend yourself.");
            }
            if (user.getFriends().contains(friend)) {
                return ResponseEntity.badRequest().body("You are already friends.");
            }
            

            // Add each other to friends set (mutual)
            user.getFriends().add(friend);
            friend.getFriends().add(user);

            // Save both changes to DB
            userRepository.save(user);
            userRepository.save(friend);

            return ResponseEntity.ok("Friend added successfully.");
        }

        return ResponseEntity.notFound().build(); // One or both users not found
    }
    @GetMapping("/{id}/friends")
    public ResponseEntity<Set<User>> getFriends(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(user.getFriends()))
                .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}/remove-friend/{friendId}")
    public ResponseEntity<?> removeFriend(@PathVariable Long id, @PathVariable Long friendId) {
        Optional<User> userOpt = userRepository.findById(id);
        Optional<User> friendOpt = userRepository.findById(friendId);
        if (userOpt.isEmpty() || friendOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("One or both users not found");
        }

        User user = userOpt.get();
        User friend = friendOpt.get();
        user.getFriends().remove(friend);
        friend.getFriends().remove(user);

        userRepository.save(user);
        userRepository.save(friend);

        return ResponseEntity.ok("Friend removed");
    }
}