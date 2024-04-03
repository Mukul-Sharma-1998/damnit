package com.damn.it.codeShare;

import com.damn.it.config.RoomSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@Slf4j
public class CodeShareController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private RoomSessionManager roomSessionManager;

//    @MessageMapping("/createRoom")
//    public String createRoom(SimpMessageHeaderAccessor simpMessageHeaderAccessor){
//        log.info("Create Room");
//        String sessionId = simpMessageHeaderAccessor.getSessionId();
//        String roomId = roomSessionManager.createRoom(sessionId);
//        simpMessagingTemplate.convertAndSendToUser(sessionId, "/topic/room/" + roomId, roomId);
//        return roomId;
//    }
//
//    @MessageMapping("/joinRoom/{roomId}")
//    public String joinRoom(@DestinationVariable String roomId, SimpMessageHeaderAccessor simpMessageHeaderAccessor){
//        String sessionId = simpMessageHeaderAccessor.getSessionId();
//        return roomSessionManager.joinRoom(roomId, sessionId);
//
//    }

//    @MessageMapping("/removeUser/{roomId}")
//    public String removeUser(@DestinationVariable String roomId, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
//        String sessionId = simpMessageHeaderAccessor.getSessionId();
//        return roomSessionManager.removeUserFromRoom(roomId, sessionId);
//
//    }

    @MessageMapping("/codeShare/{roomId}")
    public CodeMessage shareCode(@DestinationVariable String roomId, @Payload CodeMessage code,SimpMessageHeaderAccessor simpMessageHeaderAccessor){
        log.info("We are here to send message");
        messagingTemplate.convertAndSend("/topic/" + roomId, code);
        return code;
    }
}
