package edu.udacity.java.nano.chat;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint("/chat")
public class WebSocketChatServer {

	/**
	 * All chat sessions.
	 */
	private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

	private static void sendMessageToAll(String msg) {
		onlineSessions.forEach((s, session) -> {
			RemoteEndpoint.Basic basic = session.getBasicRemote();

			try {
				basic.sendText(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Open connection, 1) add session, 2) add user.
	 */
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("OnOpen: " + session.getId());

		onlineSessions.put(session.getId(), session);

		Map<String, Object> authData = new HashMap<>();

		authData.put("onlineCount", onlineSessions.size());

		Message message = new Message("UPDATE", authData);

		sendMessageToAll(JSON.toJSONString(message));
	}

	/**
	 * Send message, 1) get username and session, 2) send message to all.
	 */
	@OnMessage
	public void onMessage(Session session, String jsonStr) {
		System.out.println("OnMessage: " + jsonStr);

		Map clientMessage = JSON.parseObject(jsonStr, Map.class);
		Message message = new Message("MESSAGE", clientMessage);

		sendMessageToAll(JSON.toJSONString(message));
	}

	/**
	 * Close connection, 1) remove session, 2) update user.
	 */
	@OnClose
	public void onClose(Session session) {
		System.out.println("onClose: " + session.getId());

		onlineSessions.remove(session.getId());
	}

	/**
	 * Print exception.
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		error.printStackTrace();
	}

}
