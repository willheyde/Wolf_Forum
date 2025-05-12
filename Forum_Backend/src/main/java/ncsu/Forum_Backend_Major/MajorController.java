package ncsu.Forum_Backend_Major;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ncsu.Forum_Backend_Classes.Classes;

import java.util.*;

@RestController
@RequestMapping("/api/majors")
public class MajorController {

    private final MajorRepository majorRepository;

    public MajorController(MajorRepository majorRepository) {
        this.majorRepository = majorRepository;
    }

    //Get all majors
    @GetMapping
    public List<Major> getAllMajors() {
        return majorRepository.findAll();
    }

    //Get a major by ID
    @GetMapping("/{id}")
    public ResponseEntity<Major> getMajorById(@PathVariable Long id) {
        return majorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Get a major by name (e.g., "Computer Science")
    @GetMapping("/name/{name}")
    public ResponseEntity<Major> getMajorByName(@PathVariable String name) {
        return majorRepository.findByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Create a new major (could be used for initial seed data)
    @PostMapping
    public ResponseEntity<Major> createMajor(@RequestBody Major major) {
        if (majorRepository.findByName(major.getName()).isPresent()) {
            return ResponseEntity.badRequest().body(null); // Avoid duplicates
        }
        Major saved = majorRepository.save(major);
        return ResponseEntity.ok(saved);
    }

    //Add a class to a major
    @PostMapping("/{id}/classes")
    public ResponseEntity<Major> addClassToMajor(@PathVariable Long id, @RequestBody Classes classCourse) {
        Optional<Major> optionalMajor = majorRepository.findById(id);
        if (optionalMajor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Major major = optionalMajor.get();
        major.getClasses().add(classCourse);
        return ResponseEntity.ok(majorRepository.save(major)); // Save both Major and new ClassCourse
    }

    //Remove a class by name from a major
    @DeleteMapping("/{id}/classes/{className}")
    public ResponseEntity<Major> removeClassFromMajor(@PathVariable Long id, @PathVariable String classTitle) {
        Optional<Major> optionalMajor = majorRepository.findById(id);
        if (optionalMajor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Major major = optionalMajor.get();
        boolean removed = major.getClasses().removeIf(c -> c.getCourseTitle().equalsIgnoreCase(classTitle));
        if (!removed) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(majorRepository.save(major));
    }
}

