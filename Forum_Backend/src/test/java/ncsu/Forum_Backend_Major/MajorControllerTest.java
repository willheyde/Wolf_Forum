package ncsu.Forum_Backend_Major;

import ncsu.Forum_Backend_Classes.Classes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MajorControllerTest {

    @Mock
    private MajorRepository majorRepository;

    @InjectMocks
    private MajorController majorController;

    private Major sampleMajor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleMajor = new Major();
        sampleMajor.setId(1L);
        sampleMajor.setName("Computer Science");
    }

    @Test
    void testGetAllMajors() {
        List<Major> majors = List.of(sampleMajor);
        when(majorRepository.findAll()).thenReturn(majors);

        List<Major> result = majorController.getAllMajors();

        assertEquals(1, result.size());
        assertEquals("Computer Science", result.get(0).getName());
    }

    @Test
    void testGetMajorByIdFound() {
        when(majorRepository.findById(1L)).thenReturn(Optional.of(sampleMajor));

        ResponseEntity<Major> response = majorController.getMajorById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sampleMajor, response.getBody());
    }

    @Test
    void testGetMajorByIdNotFound() {
        when(majorRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Major> response = majorController.getMajorById(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetMajorByNameFound() {
        when(majorRepository.findByName("Computer Science")).thenReturn(Optional.of(sampleMajor));

        ResponseEntity<Major> response = majorController.getMajorByName("Computer Science");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sampleMajor, response.getBody());
    }

    @Test
    void testGetMajorByNameNotFound() {
        when(majorRepository.findByName("Math")).thenReturn(Optional.empty());

        ResponseEntity<Major> response = majorController.getMajorByName("Math");

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreateMajorWhenNew() {
        when(majorRepository.findByName("Computer Science")).thenReturn(Optional.empty());
        when(majorRepository.save(any())).thenReturn(sampleMajor);

        ResponseEntity<Major> response = majorController.createMajor(sampleMajor);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(sampleMajor, response.getBody());
    }

    @Test
    void testCreateMajorWhenDuplicate() {
        when(majorRepository.findByName("Computer Science")).thenReturn(Optional.of(sampleMajor));

        ResponseEntity<Major> response = majorController.createMajor(sampleMajor);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testAddClassToMajorWhenMajorExists() {
        Classes newClass = new Classes();
        newClass.setCourseTitle("CSC116");

        when(majorRepository.findById(1L)).thenReturn(Optional.of(sampleMajor));
        when(majorRepository.save(any())).thenReturn(sampleMajor);

        ResponseEntity<Major> response = majorController.addClassToMajor(1L, newClass);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().getClasses().contains(newClass));
    }

    @Test
    void testAddClassToMajorWhenMajorNotFound() {
        Classes newClass = new Classes();
        when(majorRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Major> response = majorController.addClassToMajor(1L, newClass);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testRemoveClassFromMajorFound() {
        Classes class1 = new Classes();
        class1.setCourseTitle("CSC116");
        sampleMajor.getClasses().add(class1);

        when(majorRepository.findById(1L)).thenReturn(Optional.of(sampleMajor));
        when(majorRepository.save(any())).thenReturn(sampleMajor);

        ResponseEntity<Major> response = majorController.removeClassFromMajor(1L, "CSC116");

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().getClasses().isEmpty());
    }

    @Test
    void testRemoveClassFromMajorNotFound() {
        when(majorRepository.findById(1L)).thenReturn(Optional.of(sampleMajor));

        ResponseEntity<Major> response = majorController.removeClassFromMajor(1L, "Nonexistent");

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testRemoveClassFromMajorWhenMajorNotFound() {
        when(majorRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Major> response = majorController.removeClassFromMajor(1L, "Anything");

        assertEquals(404, response.getStatusCodeValue());
    }
}

