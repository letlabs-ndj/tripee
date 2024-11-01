package com.lameute.chat_service.controller;

import com.lameute.chat_service.dto.ConversationRequest;
import com.lameute.chat_service.model.Conversation;
import com.lameute.chat_service.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/conversations")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @PostMapping("/save")
    public void createConversation(@RequestBody ConversationRequest conversationRequest){
        conversationService.createConversation(conversationRequest);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Conversation>> getConversationsByUserId(@PathVariable("userId") long userId){
        return new ResponseEntity<>(conversationService.getConversationByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/exist/{convId}")
    public ResponseEntity<Boolean> conversationExist(@PathVariable("convId") String convId){
        return new ResponseEntity<>(conversationService.conversationExist(convId),HttpStatus.OK);
    }
}
