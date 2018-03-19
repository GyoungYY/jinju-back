package com.aguang.jinjuback.services.chat;

import com.aguang.jinjuback.configuration.CustomException;
import com.aguang.jinjuback.dao.chat.ChatDao;
import com.aguang.jinjuback.pojo.chat.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Service
public class ChatService {
	public final static Logger logger = LoggerFactory.getLogger(ChatService.class);

	/* 游客id */
	public static Long localVisitorId = 10000L;

	@Autowired
	private JedisPool jedisPool;

	@Autowired
	private ChatDao chatDao;

	/**
	 * 获取下一个id
	 * @return
	 */
	public Long nextVisitorId() {

		Jedis jedis = null;
        Long visitorId = null;

		try {
			jedis = jedisPool.getResource();

            if(jedis.get("VISITOR_ID") == null) {
                jedis.set("VISITOR_ID", "10001");
                visitorId = 10001L;
            } else {
                visitorId = jedis.incrBy("VISITOR_ID", 1L);
            }

        } catch (Exception ex) {
			logger.error("错误", ex);
			throw new CustomException("Redis连接错误");
		} finally {
			if (jedis != null) {
				//关闭连接
				jedis.close();
			}
		}

		if(visitorId == null) {
            visitorId = localVisitorId++;
        }

		return visitorId;
	}

	/**
	 * 获取历史消息
	 * @param id
	 * @param limit
	 * @return
	 */
	public List<ChatMessage> getHistoryMessage(Integer id, Integer limit) {


		List<ChatMessage> chatMessages = chatDao.findChatMessage(id, limit);

//		try {
//
//			chatMessages = jedis.lrange("chat", start, end);
//		} catch (Exception ex) {
//			logger.error("错误", ex);
//			throw new CustomException("Redis连接错误");
//		} finally {
//			if (jedis != null) {
//				//关闭连接
//				jedis.close();
//			}
//		}

		return chatMessages;
	}

	/**
	 * 创建消息
	 * @param chatMessage
	 * @return
	 */
	public Integer createChatMessage(ChatMessage chatMessage) {
		return chatDao.createChatMessage(chatMessage);
	}
	
	public boolean isNeedShowTime() {

	    boolean flag = false;

		Jedis jedis = null;

		try {
			jedis = jedisPool.getResource();

			// 两分钟的规则
            if (jedis.get("TWO_MIN") == null) {
                jedis.set("TWO_MIN", "1");
            }

            if (jedis.ttl("TWO_MIN") < 0) {
                flag = true;
            }

            jedis.expire("TWO_MIN", 120);

            // 十分钟的规则
            if (jedis.get("TEN_MIN") == null) {
                jedis.set("TEN_MIN", "1");
            }

            if (flag) {
                jedis.expire("TEN_MIN", 600);
            }

            if (jedis.ttl("TEN_MIN") < 0) {
                flag = true;
                jedis.expire("TEN_MIN", 600);
            }

        } catch (Exception ex) {
			logger.error("错误", ex);
			throw new CustomException("Redis连接错误");
		} finally {
			if (jedis != null) {
				//关闭连接
				jedis.close();
			}
		}
		return flag;
	}

}
