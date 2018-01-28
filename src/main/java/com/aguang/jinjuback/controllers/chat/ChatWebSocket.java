package com.aguang.jinjuback.controllers.chat;

import com.aguang.jinjuback.model.User;
import com.aguang.jinjuback.pojo.chat.ChatMessage;
import com.aguang.jinjuback.pojo.chat.ChatUser;
import com.aguang.jinjuback.services.UserService;
import com.aguang.jinjuback.utils.ConvertUtils;
import com.aguang.jinjuback.utils.DateUtils;
import com.aguang.jinjuback.utils.SpringUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint(value = "/chatsocket/{userId}")
@Component
public class ChatWebSocket {

    public static final String VISITOR_PHOTO = "http://p2g5cb64g.bkt.clouddn.com/FqzcOdtAM96-Fe3ssizwDpMVCbiD";

//    @Autowired
//    private UserService userService;

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    public static int ONLINE_COUNT = 0;

    // 存放userId和Session
    public static ConcurrentHashMap<String, Session> webSocketMap = new ConcurrentHashMap<String, Session>();//建立连接的方法

    /**
     * 连接打开的方法
     * @param session
     * @param userId
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        System.out.println("onOpen");

        webSocketMap.put(userId, session);
        addOnlineCount(); //在线数加

        User user1 = getUserById(userId);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(ConvertUtils.getUUID());
        chatMessage.setUserId(userId);
        // 设置消息类型为： 提示消息
        chatMessage.setType("2");
        chatMessage.setCreateTime(DateUtils.getCurrentTime());

        if(user1 == null) {
            chatMessage.setIsVisitor(true);
            chatMessage.setUsername("游客" + userId);
            chatMessage.setPhotoUrl(VISITOR_PHOTO);
            chatMessage.setMessage("游客"+userId+" 进入了聊天室...");
        } else {
            chatMessage.setIsVisitor(false);
            chatMessage.setUsername(user1.getUsername());
            chatMessage.setPhotoUrl(user1.getPhotoUrl());
            chatMessage.setMessage(user1.getUsername() +" 进入了聊天室...");
        }

        List<ChatUser> userList = new ArrayList<>();

        for (String userId2 : webSocketMap.keySet()) {
            User currentUser = getUserById(userId2);

            ChatUser chatUser = new ChatUser();
            chatUser.setUserId(userId2);

            // 用户为空时，为判断为游客
            if(currentUser == null) {
                chatUser.setIsVisitor(true);
                chatUser.setUsername("游客" + userId2);
                chatUser.setPhotoUrl(VISITOR_PHOTO);
            } else {
                chatUser.setIsVisitor(false);
                chatUser.setUsername(currentUser.getUsername());
                chatUser.setPhotoUrl(currentUser.getPhotoUrl());
            }
            userList.add(chatUser);
        }

        chatMessage.setUserList(userList);

        String chatMessageJson = JSON.toJSONString(chatMessage);

        for (String user : webSocketMap.keySet()) {
            try {
                System.out.println(user);
                System.out.println("关闭发送： " + chatMessageJson);
                sendMessage(chatMessageJson, webSocketMap.get(user));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(userId + "进入聊天室");
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        //获取用户ID
        Map<String, String> map = session.getPathParameters();

        String userId = map.get("userId");

        User currentUser = getUserById(userId);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(ConvertUtils.getUUID());
        chatMessage.setUserId(userId);
        // 设置消息类型为： 提示消息
        chatMessage.setType("1");
        chatMessage.setCreateTime(DateUtils.getCurrentTime());

        if(currentUser == null) {
            chatMessage.setIsVisitor(true);
            chatMessage.setUsername("游客" + userId);
            chatMessage.setPhotoUrl(VISITOR_PHOTO);
        } else {
            chatMessage.setIsVisitor(false);
            chatMessage.setUsername(currentUser.getUsername());
            chatMessage.setPhotoUrl(currentUser.getPhotoUrl());
        }

        chatMessage.setMessage(message);
        chatMessage.setType("1");

        // 将消息对象转换出json
        String chatMessageJson = JSON.toJSONString(chatMessage);

        // 将发送的消息存值redis
        saveToRedis(chatMessageJson);

        // 群发信息
        for (String user : webSocketMap.keySet()) {
            try {
                sendMessage(chatMessageJson, webSocketMap.get(user));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 连接关闭调用的方法
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        Map<String, String> map = session.getPathParameters();
        webSocketMap.remove(map.get("userId")); //从set中删除
        for (String user : webSocketMap.keySet()) {
            System.out.println(user);
        }
        subOnlineCount(); //在线数减

        String userId = map.get("userId");

        User user1 = getUserById(userId);

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(ConvertUtils.getUUID());
        chatMessage.setUserId(userId);
        // 设置消息类型为： 提示消息
        chatMessage.setType("2");
        chatMessage.setCreateTime(DateUtils.getCurrentTime());

        if(user1 == null) {
            chatMessage.setIsVisitor(true);
            chatMessage.setUsername("游客" + userId);
            chatMessage.setPhotoUrl(VISITOR_PHOTO);
            chatMessage.setMessage("游客"+userId+" 离开了聊天室...");
        } else {
            chatMessage.setIsVisitor(false);
            chatMessage.setUsername(user1.getUsername());
            chatMessage.setPhotoUrl(user1.getPhotoUrl());
            chatMessage.setMessage(user1.getUsername() +" 离开了聊天室...");
        }

        List<ChatUser> userList = new ArrayList<>();

        for (String userId2 : webSocketMap.keySet()) {
            User currentUser = getUserById(userId2);

            ChatUser chatUser = new ChatUser();
            chatUser.setUserId(userId2);

            // 用户为空时，为判断为游客
            if(currentUser == null) {
                chatUser.setIsVisitor(true);
                chatUser.setUsername("游客" + userId2);
                chatUser.setPhotoUrl(VISITOR_PHOTO);
            } else {
                chatUser.setIsVisitor(false);
                chatUser.setUsername(currentUser.getUsername());
                chatUser.setPhotoUrl(currentUser.getPhotoUrl());
            }
            userList.add(chatUser);
        }

        chatMessage.setUserList(userList);


        String chatMessageJson = JSON.toJSONString(chatMessage);

        // 群发消息
        for (String user : webSocketMap.keySet()) {
            try {
                sendMessage(chatMessageJson, webSocketMap.get(user));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 发送消息
     * @param message
     * @param session
     * @throws IOException
     */
    public void sendMessage(String message, Session session) throws IOException {
        if (session.isOpen()) {
            session.getAsyncRemote().sendText(message);
        }
    }

    /**
     * 群发信息
     * @param message
     * @param session
     * @throws IOException
     */
    public void sendMassMessage(String message, Session session) throws IOException {
//        for (String user : webSocketMap.keySet()) {
//            try {
//                System.out.println(user);
//                System.out.println("关闭发送： " + chatMessageJson);
//                sendMessage(chatMessageJson, webSocketMap.get(user));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public static synchronized int getOnlineCount() {
        return ONLINE_COUNT;
    }

    public static synchronized void addOnlineCount() {
        ChatWebSocket.ONLINE_COUNT++;
    }

    public static synchronized void subOnlineCount() {
        ChatWebSocket.ONLINE_COUNT--;
    }

    private User getUserById(String userId) {
        UserService userService = (UserService)SpringUtils.getBean("userService");

        try {
            return userService.getUserById(new Integer(userId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void saveToRedis(String message) {
        JedisPool jedisPool = (JedisPool)SpringUtils.getBean("jedisPool");

        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();

            jedis.lpush("chat", message);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (jedis != null) {
                //关闭连接
                jedis.close();
            }
        }
    }

}