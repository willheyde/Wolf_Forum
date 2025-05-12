package ncsu.Forum_Backend_Message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import ncsu.Forum_Backend_Classes.Classes;
import ncsu.Forum_Backend_Message.Message;
import ncsu.Forum_Backend_Message.MessageController;
import ncsu.Forum_Backend_Message.MessageRepository;
import ncsu.Forum_Backend_User.User;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MessageControllerTest {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageController messageController;

    private Message sampleMessage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        User will = new User(null, "wwheyde", "Will", "wwheyde@ncsu.edu", "bios", null, true);
        Classes csc116 = new Classes("CSC 116", null, null);
        csc116.addUser(will);
        sampleMessage = new Message();
        sampleMessage.setId(1L);
        sampleMessage.setClassTitle(csc116);
        sampleMessage.setSender(will);
        sampleMessage.setContent("This is a test message.");
        sampleMessage.setTimestamp(LocalDateTime.now());
    }

    @Test
    void testPostMessage() {
        when(messageRepository.save(sampleMessage)).thenReturn(sampleMessage);

        ResponseEntity<Message> response = messageController.postMessage(sampleMessage);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals(sampleMessage, response.getBody());
        verify(messageRepository).save(sampleMessage);
    }

    @Test
    void testGetMessagesByClass() {
        List<Message> messages = List.of(sampleMessage);
        when(messageRepository.findByClassTitle("CSC116")).thenReturn(messages);

        ResponseEntity<List<Message>> response = messageController.getMessagesByClass("CSC116");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("CSC 116", response.getBody().get(0).getClasses().getCourseTitle());
    }

    @Test
    void testGetMessageByIdFound() {
        when(messageRepository.findById(1L)).thenReturn(Optional.of(sampleMessage));

        ResponseEntity<Message> response = messageController.getMessageById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sampleMessage, response.getBody());
    }

    @Test
    void testGetMessageByIdNotFound() {
        when(messageRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Message> response = messageController.getMessageById(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteMessageExists() {
        when(messageRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<Void> response = messageController.deleteMessage(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(messageRepository).deleteById(1L);
    }

    @Test
    void testDeleteMessageNotFound() {
        when(messageRepository.existsById(1L)).thenReturn(false);

        ResponseEntity<Void> response = messageController.deleteMessage(1L);

        assertEquals(404, response.getStatusCodeValue());
        verify(messageRepository, never()).deleteById(1L);
    }
}

