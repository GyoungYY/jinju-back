package com.aguang.jinjuback.services.chat;

import com.aguang.jinjuback.configuration.CustomException;
import com.aguang.jinjuback.dao.chat.ChatDao;
import com.aguang.jinjuback.pojo.chat.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ChatService {
	public final static Logger logger = LoggerFactory.getLogger(ChatService.class);

	/* 游客id */
	public static Long LOCAL_VISITOR_ID = 10000L;

//	@Autowired
//	private JedisPool jedisPool;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	private ChatDao chatDao;

	/**
	 * 获取下一个id
	 * @return
	 */
	public Long nextVisitorId() {

        Long visitorId = null;

		try {
            if(redisTemplate.boundValueOps("VISITOR_ID").get() == null) {
				redisTemplate.opsForValue().set("VISITOR_ID", "10001");
                visitorId = 10001L;
            } else {
                visitorId = redisTemplate.opsForValue().increment("VISITOR_ID", 1L);
            }

        } catch (Exception ex) {
			logger.error("错误", ex);
//			throw new CustomException("Redis连接错误");
		}

		if(visitorId == null) {
            visitorId = LOCAL_VISITOR_ID++;
        }

		return visitorId;
	}

	/**
	 * 创建消息
	 * @param chatMessage
	 * @return
	 */
	public Integer createChatMessage(ChatMessage chatMessage) {
		return chatDao.createChatMessage(chatMessage);
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
	 * 根据两分钟、十分钟原则判断是否需要显示时间
	 * @return
	 */
	public boolean isNeedShowTime() {

		boolean flag = false;

		try {
			ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();

			// 两分钟的规则
			if (opsForValue.get("TWO_MIN") == null) {
                opsForValue.set("TWO_MIN", "1");
			}

			if (redisTemplate.getExpire("TWO_MIN") < 0) {
				flag = true;
			}

			// 设置两分钟过期
			redisTemplate.expire("TWO_MIN", 120, TimeUnit.SECONDS);

			// 十分钟的规则
			if (opsForValue.get("TEN_MIN") == null) {
				opsForValue.set("TEN_MIN", "1");
			}

			if (flag) {
				redisTemplate.expire("TEN_MIN", 600, TimeUnit.SECONDS);
			}

			if (redisTemplate.getExpire("TEN_MIN") < 0) {
				flag = true;
				redisTemplate.expire("TEN_MIN", 600, TimeUnit.SECONDS);
			}

		} catch (Exception ex) {
			logger.error("错误", ex);
			throw new CustomException("Redis连接错误");
		}
		return flag;
	}

	@Deprecated
	public boolean isNeedShowTime2() {

		boolean flag = false;

		Jedis jedis = null;

		try {
//			jedis = jedisPool.getResource();

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
