package ncsu.Forum_Backend_Major;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MajorRepository extends JpaRepository<Major, Long> {
    Optional<Major> findByName(String name);

}

