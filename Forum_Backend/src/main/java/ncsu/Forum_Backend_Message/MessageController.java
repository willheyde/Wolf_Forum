package ncsu.Forum_Backend_Message;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Post a new message to a class.
     * The message object should include classId and userId.
     */
    @PostMapping
    public ResponseEntity<Message> postMessage(@RequestBody Message message) {
        return new ResponseEntity<>(messageRepository.save(message), HttpStatus.CREATED);
    }

    /**
     * Get all messages for a specific class.
     * Example URL: /api/messages/class/CSC116
     */
    @GetMapping("/class/{classCode}")
    public ResponseEntity<List<Message>> getMessagesByClass(@PathVariable String classTitle) {
        List<Message> messages = messageRepository.findByClassTitle(classTitle);
        return ResponseEntity.ok(messages);
    }

    /**
     * Get a specific message by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        Optional<Message> message = messageRepository.findById(id);
        return message.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete a message (optional)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
