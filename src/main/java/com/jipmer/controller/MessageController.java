package com.jipmer.controller;


import com.jipmer.dto.MessageDTO;
import com.jipmer.dto.PatientDTO;
import com.jipmer.entity.Admission;
import com.jipmer.entity.Message;
import com.jipmer.repository.AdmissionRepository;
import com.jipmer.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/message")
@CrossOrigin(origins = "*")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    AdmissionRepository admissionRepository;

    @GetMapping("/messages")
    public List<MessageDTO> getMessages(@RequestParam Integer admissionId) {

        List<Message> messages = messageRepository.findByAdmissionId(admissionId);
        List<MessageDTO> dtoList = new ArrayList<MessageDTO>();
       for(Message message : messages) {

           MessageDTO messageDto = new MessageDTO();
           messageDto.setId(message.getId());
           messageDto.setMessages(message.getMessages());
           messageDto.setMessageType(message.getMessageType());
           messageDto.setBucketUrl(message.getBucketUrl());
           messageDto.setMediaType(message.getMediaType());
           dtoList.add(messageDto);
       }

    return dtoList;


    }

    @PostMapping("/add/message")
    public String addMessage(@RequestParam String admissionId, @RequestParam String message,  @RequestParam String mediaType) {


        Message messages = new Message();
        Admission admission = admissionRepository.getById(Integer.parseInt(admissionId));
        messages.setAdmission(admission);
        messages.setMessages(message);
        messages.setMediaType(mediaType);
        messages.setCreatedDate(LocalDate.now());
        messages.setUpdatedDate(LocalDate.now());
        messageRepository.save(messages);
        return "Message added";
    }

}
