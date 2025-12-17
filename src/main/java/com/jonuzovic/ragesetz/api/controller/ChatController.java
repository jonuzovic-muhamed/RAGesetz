package com.jonuzovic.ragesetz.api.controller;

import com.jonuzovic.ragesetz.api.dto.AdviceResponseDto;
import com.jonuzovic.ragesetz.api.dto.LawExplanationResponseDto;
import com.jonuzovic.ragesetz.api.mapper.impl.AdviceResponseDtoMapper;
import com.jonuzovic.ragesetz.api.mapper.impl.LawExplanationResponseDtoMapper;
import com.jonuzovic.ragesetz.core.service.IChatService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final IChatService chatService;
    private final LawExplanationResponseDtoMapper lawExplanationResponseDtoMapper;
    private final AdviceResponseDtoMapper adviceResponseDtoMapper;

    public ChatController(IChatService chatService, LawExplanationResponseDtoMapper lawExplanationResponseDtoMapper, AdviceResponseDtoMapper adviceResponseDtoMapper) {
        this.chatService = chatService;
        this.lawExplanationResponseDtoMapper = lawExplanationResponseDtoMapper;
        this.adviceResponseDtoMapper = adviceResponseDtoMapper;
    }

    @PostMapping("/ask")
    public ResponseEntity<AdviceResponseDto> askAdvice(@RequestBody String userQuestion) {
        return ResponseEntity.ok(
                adviceResponseDtoMapper.mapToDto(
                        chatService.askForAdvice(userQuestion)
                )
        );
    }

    @PostMapping("/explain")
    public ResponseEntity<LawExplanationResponseDto> explain(@RequestBody String userQuestion, @RequestBody String lawCode, @RequestBody String lawSectionNumber) {
        return ResponseEntity.ok(
                lawExplanationResponseDtoMapper.mapToDto(
                        chatService.askForLawExplanation(userQuestion, lawCode, lawSectionNumber)
                )
        );
    }
}
