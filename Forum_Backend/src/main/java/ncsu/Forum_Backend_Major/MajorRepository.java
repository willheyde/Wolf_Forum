package ncsu.Forum_Backend_Major;

import java.util.List;
import java.util.Optional;
import ncsu.Forum_Backend_Message.Message;
import ncsu.Forum_Backend_Message.Message.MessageType;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MajorRepository extends JpaRepository<Major, Long> {
    Optional<Major> findByName(String name);
    List<Message> findByMajorIdAndType(Long majorId, MessageType type);

}

