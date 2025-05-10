package ncsu.Forum_Backend_User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
}