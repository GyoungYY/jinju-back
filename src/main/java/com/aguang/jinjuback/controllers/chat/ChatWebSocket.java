package com.aguang.jinjuback.controllers.chat;

import com.aguang.jinjuback.pojo.chat.ChatMessage;
import com.aguang.jinjuback.pojo.chat.ChatUser;
import com.aguang.jinjuback.services.UserService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
//    @Resource
//    private Webcomment webcomment;

    @Autowired
    private UserService userService;

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    public static int ONLINE_COUNT = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
//    private static CopyOnWriteArraySet<ChatWebSocket> webSocketSet = new CopyOnWriteArraySet<ChatWebSocket>();
    //线程安全的Map
    public static ConcurrentHashMap<String, Session> webSocketMap = new ConcurrentHashMap<String, Session>();//建立连接的方法

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        System.out.println("onOpen");
        // , @PathParam("userId") String userId
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        Integer userId1 = (Integer) request.getSession().getAttribute("userId");
//        String userId = userId1 + "";
        /*获取从/websocket开始的整条链接，用于获取userId？***=***的参数
        String uri = session.getRequestURI().toString();*/

//        User user = userService.getUserById(335);
        webSocketMap.put(userId, session);
        addOnlineCount(); //在线数加

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUserId(userId);
        chatMessage.setUsername("游客"+userId);
        chatMessage.setPhotoUrl("http://p2g5cb64g.bkt.clouddn.com/FgD_kc3J6DT5I8Zq0dggaQgN6j43");
        chatMessage.setMessage("游客"+userId+" 进入聊天室...");
        chatMessage.setType("2");

        ChatUser chatUser = new ChatUser();
        chatUser.setUserId("1001");
        chatUser.setUsername("游客1");
        chatUser.setPhotoUrl("http://p2g5cb64g.bkt.clouddn.com/qiniu.png");

        ChatUser chatUser2 = new ChatUser();
        chatUser2.setUserId("1002");
        chatUser2.setUsername("游客2");
        chatUser2.setPhotoUrl("http://p2g5cb64g.bkt.clouddn.com/FgD_kc3J6DT5I8Zq0dggaQgN6j43");

        ChatUser chatUser3 = new ChatUser();
        chatUser3.setUserId("1003");
        chatUser3.setUsername("游客3");
        chatUser3.setPhotoUrl("http://p2g5cb64g.bkt.clouddn.com/FtQYnIg235Jn1qRUTGrNl3N2pRXV");

        List<ChatUser> userList = new ArrayList<>();
        userList.add(chatUser);
        userList.add(chatUser2);
        userList.add(chatUser3);
        chatMessage.setUserList(userList);


        String chatMessageJson = JSON.toJSONString(chatMessage);

        for (String user : webSocketMap.keySet()) {
            try {
//                sendMessage(user + "你好，我是" + userId + "   " + message, webSocketMap.get(user));
                sendMessage(chatMessageJson, webSocketMap.get(user));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        System.out.println(userId + "进入聊天室");
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
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
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUserId(userId);
        chatMessage.setUsername("游客"+userId);
        chatMessage.setPhotoUrl("http://p2g5cb64g.bkt.clouddn.com/FgD_kc3J6DT5I8Zq0dggaQgN6j43");
        chatMessage.setMessage("游客"+userId+" 进入聊天室...");
        chatMessage.setType("2");

        ChatUser chatUser = new ChatUser();
        chatUser.setUserId("1001");
        chatUser.setUsername("游客1");
        chatUser.setPhotoUrl("http://p2g5cb64g.bkt.clouddn.com/qiniu.png");

        ChatUser chatUser2 = new ChatUser();
        chatUser2.setUserId("1002");
        chatUser2.setUsername("游客2");
        chatUser2.setPhotoUrl("http://p2g5cb64g.bkt.clouddn.com/FgD_kc3J6DT5I8Zq0dggaQgN6j43");

        ChatUser chatUser3 = new ChatUser();
        chatUser3.setUserId("1003");
        chatUser3.setUsername("游客3");
        chatUser3.setPhotoUrl("http://p2g5cb64g.bkt.clouddn.com/FtQYnIg235Jn1qRUTGrNl3N2pRXV");

        List<ChatUser> userList = new ArrayList<>();
        userList.add(chatUser);
        userList.add(chatUser2);
        userList.add(chatUser3);
        chatMessage.setUserList(userList);


        String chatMessageJson = JSON.toJSONString(chatMessage);

        for (String user : webSocketMap.keySet()) {
            try {
//                sendMessage(user + "你好，我是" + userId + "   " + message, webSocketMap.get(user));
                sendMessage(chatMessageJson, webSocketMap.get(user));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }






        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
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

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setUserId(userId);
        chatMessage.setUsername("akaks");
        chatMessage.setPhotoUrl("http://p2g5cb64g.bkt.clouddn.com/FgD_kc3J6DT5I8Zq0dggaQgN6j43");
        chatMessage.setMessage(message);
        chatMessage.setType("1");

        String chatMessageJson = JSON.toJSONString(chatMessage);

        for (String user : webSocketMap.keySet()) {
            try {
//                sendMessage(user + "你好，我是" + userId + "   " + message, webSocketMap.get(user));
                sendMessage(chatMessageJson, webSocketMap.get(user));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    public void sendMessage(String message, Session session) throws IOException {
        if (session.isOpen()) {
            session.getAsyncRemote().sendText(message);
        }
        //this.session.getAsyncRemote().sendText(message);
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
}