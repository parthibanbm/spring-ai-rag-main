package com.parthi.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@Service
public class OpenAIChatService {

    private final ChatClient chatClient;

    public OpenAIChatService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    public String askToAI(String message, String username) {
        return chatClient
                .prompt(message)
                .advisors(adviceSpec ->
                        adviceSpec.param(CONVERSATION_ID, username)
                )
                .call()
                .content();
    }

    //Ankit -> List<Message>
    //Rahul -> List<Message>

}
