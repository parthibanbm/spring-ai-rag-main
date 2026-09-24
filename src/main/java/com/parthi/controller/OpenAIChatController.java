package com.parthi.controller;

import org.springframework.web.bind.annotation.*;

import com.parthi.service.OpenAIChatService;

@RestController
@RequestMapping("/api")
public class OpenAIChatController {


    private final OpenAIChatService openAIChatService;

    public OpenAIChatController(OpenAIChatService openAIChatService) {
        this.openAIChatService = openAIChatService;
    }


    @GetMapping("/chat")
    public String chat(@RequestParam String message,@RequestHeader("username") String username) {
        return openAIChatService.askToAI(message,username);
    }


}
