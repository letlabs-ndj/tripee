package com.lameute.chat_service.service;

import com.lameute.chat_service.dto.ConversationRequest;
import com.lameute.chat_service.model.Conversation;
import com.lameute.chat_service.repo.ConversationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationService {
    @Autowired
    private ConversationRepo conversationRepo;

    // We create a conversation for the communicating users
    public void createConversation(ConversationRequest conversationRequest){
        Conversation conversation1 = new Conversation();
        conversation1.setConvId(conversationRequest.convId());
        conversation1.setUserId(conversationRequest.userId());
        conversation1.setUsername(conversationRequest.username());
        conversation1.setInterlocutorId(conversationRequest.interlocutorId());
        conversation1.setInterlocutorName(conversationRequest.interlocutorName());

        conversationRepo.save(conversation1);

        Conversation conversation2 = new Conversation();
        conversation2.setConvId(conversationRequest.convId());
        conversation2.setUserId(conversationRequest.interlocutorId());
        conversation2.setUsername(conversationRequest.interlocutorName());
        conversation2.setInterlocutorId(conversationRequest.userId());
        conversation2.setInterlocutorName(conversationRequest.username());

        conversationRepo.save(conversation2);
    }

    // We get the list of all user conversations
    public List<Conversation> getConversationByUserId(long userId){
        return conversationRepo.findByUserId(userId)
                .orElseThrow(()->new RuntimeException("No conversation for this userId"));
    }

    // Check if a conversation exist
    public boolean conversationExist(String convId){
        return conversationRepo.existsByConvId(convId);
    }
}
