package com.lameute.chat_service.controller;

import com.lameute.chat_service.model.ChatMessage;
import com.lameute.chat_service.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


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

    @MessageMapping("/chat.sendMessage/{rcvId}")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage,
                                   @DestinationVariable("rcvId") long receiverId) {
        System.out.println(receiverId);
        chatService.sendMessageToConvId(chatMessage, receiverId);
        return chatMessage;
    }

//    @MessageMapping("/chat.addUser")
//    @SendTo("/topic/public")
//    public ChatMessage addUser(ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
//        headerAccessor.getSessionAttributes().put("username", chatMessage.getSenderName());
//        return chatMessage;
//    }
}
