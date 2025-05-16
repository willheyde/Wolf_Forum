package ncsu.Forum_Backend_Message;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ncsu.Forum_Backend_Message.Message.MessageType;
import ncsu.Forum_Backend_User.GroupChat;


public interface GroupChatRepository extends JpaRepository<GroupChat, Long> {
	List<Message> findByGroupChatIdAndType(Long groupId, MessageType type);

}
