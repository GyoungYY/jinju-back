package com.aguang.jinjuback.dao.chat;

import com.aguang.jinjuback.pojo.chat.ChatMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ChatDao {

//	@Insert("INSERT INTO `chat_message`(user_id, username, photo_url, type, message, is_visitor, is_show_time, create_time) " +
//			"VALUES (#{userId}, #{username}, #{photoUrl}, #{type}, #{message}, #{isVisitor}, #{isShowTime}, #{createTime})")
	Integer createChatMessage(ChatMessage chatMessage);

	List<ChatMessage> findChatMessage(@Param("id") Integer id, @Param("limit") Integer limit);
}
