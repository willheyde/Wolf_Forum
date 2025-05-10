package ncsu.Forum_Backend_User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "wwheyde", "Will Heyde", "will@ncsu.edu", "Wolfpack!", "http://image.url");
    }

    @Test
    void testAddUser() {
        when(userRepository.save(user)).thenReturn(user);
        User result = userController.addUser(user);
        assertEquals(user, result);
        verify(userRepository).save(user);
    }

    @SuppressWarnings("deprecation")
	@Test
    void testDeleteUserExists() {
        when(userRepository.existsById(1L)).thenReturn(true);
        ResponseEntity<Void> response = userController.deleteUser(1L);
        assertEquals(204, response.getStatusCodeValue());
        verify(userRepository).deleteById(1L);
    }

    @SuppressWarnings("deprecation")
	@Test
    void testDeleteUserNotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);
        ResponseEntity<Void> response = userController.deleteUser(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @SuppressWarnings("deprecation")
	@Test
    void testEditUserFound() {
        User updated = new User("wwheyde", "William Heyde", "william@ncsu.edu", "EE major", "http://new.image");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        ResponseEntity<User> response = userController.editUser(1L, updated);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("William Heyde", response.getBody().getDisplayName());
    }

    @SuppressWarnings("deprecation")
	@Test
    void testEditUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<User> response = userController.editUser(1L, user);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetAllUsers() {
        List<User> users = List.of(user);
        when(userRepository.findAll()).thenReturn(users);
        List<User> result = userController.getAllUsers();
        assertEquals(1, result.size());
        assertEquals(user, result.get(0));
    }

    @SuppressWarnings("deprecation")
	@Test
    void testGetUserByIdFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        ResponseEntity<User> response = userController.getUserById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
    }

    @SuppressWarnings("deprecation")
	@Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseEntity<User> response = userController.getUserById(1L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @SuppressWarnings("deprecation")
	@Test
    void testGetUserByUnityIdFound() {
        when(userRepository.findByUnityId("wwheyde")).thenReturn(user);
        ResponseEntity<User> response = userController.getUserByUnityId("wwheyde");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
    }

    @SuppressWarnings("deprecation")
	@Test
    void testGetUserByUnityIdNotFound() {
        when(userRepository.findByUnityId("unknown")).thenReturn(null);
        ResponseEntity<User> response = userController.getUserByUnityId("unknown");
        assertEquals(404, response.getStatusCodeValue());
    }
}

