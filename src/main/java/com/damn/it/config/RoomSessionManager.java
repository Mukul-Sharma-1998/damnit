package com.damn.it.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class RoomSessionManager {

    private Map<String, Set<String>> roomSession = new HashMap<>();

    public String createRoom(String sessionId){
        String roomId = UUID.randomUUID().toString();
        roomSession.put(roomId,Set.of(sessionId));
        log.info(roomId);
        log.info(roomSession.get(roomId).toString());
        return roomId;
    }

    public String joinRoom(String roomId, String sessionId){
        if(roomSession.containsKey(roomId)){
            if (!roomSession.get(roomId).contains(sessionId)){
                roomSession.get(roomId).add(sessionId);
            }
        }
        else {
            roomSession.put(roomId, Set.of(sessionId));
        }
        log.info(roomId);
        log.info(roomSession.get(roomId).toString());
        return roomId;
    }

    public String removeUserFromRoom(String roomId, String sessionId){
        if(roomSession.containsKey(roomId)){
            if(roomSession.get(roomId).contains(sessionId)){
                roomSession.get(roomId).remove(sessionId);
                if(roomSession.get(roomId).isEmpty()){
                    roomSession.remove(roomId);
                }
            }
        }
        log.info(roomId);
        log.info(sessionId);
        log.info(roomSession.toString());
        return sessionId;
    }

    public Set<String> getSessionIdsFromRoom(String roomId){
        if(roomSession.containsKey(roomId)){
            return roomSession.get(roomId);
        }
        else{
            return Set.of();
        }
    }
}
