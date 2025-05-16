package ncsu.Forum_Backend_Classes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ncsu.Forum_Backend_Message.Message;
import ncsu.Forum_Backend_Message.Message.MessageType;

public interface ClassesRepository extends JpaRepository<Classes, Long> {
	List<Message> findByClassGroupIdAndType(Long classId, MessageType type);
}

