package ncsu.Forum_Backend_Message;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ncsu.Forum_Backend_Message.Message.MessageType;


public interface MessageRepository extends JpaRepository<Message, Long> {


	List<Message> findByClassId(long l);

	List<Message> findByMajorId(long l);

	List<Message> findByGroupChatId(long l);

	List<Message> findDirectMessages(long l, long m);
}