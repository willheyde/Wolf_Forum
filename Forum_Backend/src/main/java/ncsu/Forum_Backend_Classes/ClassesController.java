package ncsu.Forum_Backend_Classes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ncsu.Forum_Backend_Major.Major;
import ncsu.Forum_Backend_Major.MajorRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/classes")
public class ClassesController {

    private final ClassesRepository classCourseRepository;
    private final MajorRepository majorRepository;

    public ClassesController(ClassesRepository classCourseRepository, MajorRepository majorRepository) {
        this.classCourseRepository = classCourseRepository;
        this.majorRepository = majorRepository;
    }

    // Create a new class and optionally assign to a major
    @PostMapping
    public ResponseEntity<Classes> createClass(@RequestBody Classes classCourse,
                                                   @RequestParam(required = false) Long majorId) {
        if (majorId != null) {
            Optional<Major> majorOpt = majorRepository.findById(majorId);
            if (majorOpt.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            Major major = majorOpt.get();
            major.getClasses().add(classCourse);
            majorRepository.save(major); // Save major to update relation
        }

        Classes saved = classCourseRepository.save(classCourse);
        return ResponseEntity.ok(saved);
    }

    // Get a class by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Classes> getClassById(@PathVariable Long id) {
        return classCourseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get all classes
    @GetMapping
    public List<Classes> getAllClasses() {
        return classCourseRepository.findAll();
    }

    // Enable or disable chat
    @PutMapping("/{id}/chat")
    public ResponseEntity<Classes> setChatEnabled(@PathVariable Long id, @RequestParam boolean enabled) {
        Optional<Classes> opt = classCourseRepository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Classes c = opt.get();
        c.setChatEnabled(enabled);
        return ResponseEntity.ok(classCourseRepository.save(c));
    }

    // Future: Enable or disable call (you'll need a field like `callEnabled`)
    // @PutMapping("/{id}/call")
    // public ResponseEntity<ClassCourse> setCallEnabled(@PathVariable Long id, @RequestParam boolean enabled) { ... }
}
