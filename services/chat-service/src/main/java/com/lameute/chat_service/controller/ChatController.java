package com.lameute.chat_service.controller;

import com.lameute.chat_service.model.ChatMessage;
import com.lameute.chat_service.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


/**
 * Controller class for handling chat-related functionality.
 */
@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @MessageMapping("/sendMessage/{rcvId}")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage,
                                   @DestinationVariable("rcvId") long receiverId) {
        System.out.println(receiverId);
        System.out.println(chatMessage);
        chatService.sendMessageToConvId(chatMessage, receiverId);
        return chatMessage;
    }

    @GetMapping("/chat/conversation/{convId}")
    public ResponseEntity<List<ChatMessage>> getChatMessageByConvId(@PathVariable("convId") String convId){
        return new ResponseEntity<>(chatService.getChatMessageByConvId(convId), HttpStatus.OK);
    }
}
