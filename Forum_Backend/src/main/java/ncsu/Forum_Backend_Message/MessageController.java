package ncsu.Forum_Backend_Message;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ncsu.Forum_Backend_User.GroupChat;
import ncsu.Forum_Backend_User.User;
import ncsu.Forum_Backend_Classes.*;
import ncsu.Forum_Backend_Major.Major;
import ncsu.Forum_Backend_Major.MajorRepository;
import ncsu.Forum_Backend_Message.Message.MessageType;
import ncsu.Forum_Backend_User.UserRepository;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ClassesRepository classesRepository;
    private final MajorRepository majorRepository;
    private final GroupChatRepository groupChatRepository;

    public MessageController(MessageRepository messageRepository,
                             UserRepository userRepository,
                             ClassesRepository classesRepository,
                             MajorRepository majorRepository,
                             GroupChatRepository groupChatRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.classesRepository = classesRepository;
        this.majorRepository = majorRepository;
        this.groupChatRepository = groupChatRepository;
    }

    // POST a new message of any type
    @PostMapping
    public ResponseEntity<?> postMessage(@RequestBody Message message,
                                         @RequestParam Long senderId,
                                         @RequestParam(required = false) Long classId,
                                         @RequestParam(required = false) Long majorId,
                                         @RequestParam(required = false) Long groupChatId,
                                         @RequestParam(required = false) Long receiverId) {

        Optional<User> senderOpt = userRepository.findById(senderId);
        if (senderOpt.isEmpty()) return ResponseEntity.badRequest().body("Sender not found");

        message.setSender(senderOpt.get());
        message.setTimestamp(LocalDateTime.now());

        switch (message.getType()) {
            case CLASS -> {
                if (classId == null) return ResponseEntity.badRequest().body("Missing classId");
                Classes cls = classesRepository.findById(classId).orElse(null);
                if (cls == null) return ResponseEntity.badRequest().body("Class not found");
                message.setClassName(cls);
            }
            case MAJOR -> {
                if (majorId == null) return ResponseEntity.badRequest().body("Missing majorId");
                Major major = majorRepository.findById(majorId).orElse(null);
                if (major == null) return ResponseEntity.badRequest().body("Major not found");
                message.setMajor(major);
            }
            case GROUP -> {
                if (groupChatId == null) return ResponseEntity.badRequest().body("Missing groupChatId");
                GroupChat group = groupChatRepository.findById(groupChatId).orElse(null);
                if (group == null) return ResponseEntity.badRequest().body("GroupChat not found");
                message.setGroupName(group);
            }
            case DIRECT -> {
                if (receiverId == null) return ResponseEntity.badRequest().body("Missing receiverId");
                User receiver = userRepository.findById(receiverId).orElse(null);
                if (receiver == null) return ResponseEntity.badRequest().body("Receiver not found");
                message.setReceiver(receiver);
            }
        }

        return new ResponseEntity<>(messageRepository.save(message), HttpStatus.CREATED);
    }

    // Get messages by CLASS
    @GetMapping("/class/{classId}")
    public ResponseEntity<List<Message>> getMessagesByClass(@PathVariable Long classId) {
        return ResponseEntity.ok(messageRepository.findByClassId(classId));
    }

    // Get messages by MAJOR
    @GetMapping("/major/{majorId}")
    public ResponseEntity<List<Message>> getMessagesByMajor(@PathVariable Long majorId) {
        return ResponseEntity.ok(messageRepository.findByMajorId(majorId));
    }

    // Get messages by GROUP
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<Message>> getMessagesByGroup(@PathVariable Long groupId) {
        return ResponseEntity.ok(messageRepository.findByGroupChatId(groupId));
    }

    // Get direct messages between two users
    @GetMapping("/direct")
    public ResponseEntity<List<Message>> getDirectMessages(@RequestParam Long senderId, @RequestParam Long receiverId) {
        return ResponseEntity.ok(messageRepository.findDirectMessages(senderId, receiverId));
    }

    // Get a specific message by ID
    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        return messageRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a message
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

	@PostMapping("/messages")
public ResponseEntity<Message> postClassMessage(@RequestBody Message message,
                                                @RequestParam Long senderId,
                                                @RequestParam Long classId) {
    Optional<User> senderOpt = userRepository.findById(senderId);
    if (senderOpt.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    Optional<Classes> classOpt = classesRepository.findById(classId);
    if (classOpt.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    // Ensure the message is actually a ClassMessage
    if (!(message instanceof ClassMessage)) {
        return ResponseEntity.badRequest().build();
    }

    User sender = senderOpt.get();
    Classes cls = classOpt.get();

    message.setSender(sender);
    message.setTimestamp(LocalDateTime.now());
    ((ClassMessage) message).setClassName(cls);

    Message saved = messageRepository.save(message);
    return ResponseEntity.status(HttpStatus.CREATED).body(saved);
}

}
