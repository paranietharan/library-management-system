package com.alphacodes.librarymanagementsystem.controller;

import com.alphacodes.librarymanagementsystem.DTO.NoticeDto;
import com.alphacodes.librarymanagementsystem.DTO.NoticeViewDto;
import com.alphacodes.librarymanagementsystem.service.NoticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    // get all notifications
    @GetMapping("/all")
    public ResponseEntity<List<NoticeViewDto>> getAllNotices() {
        return ResponseEntity.ok(noticeService.getAllNotices());
    }

    // add new notice to the system
    @PostMapping("/add")
    public ResponseEntity<NoticeDto> addNotice(@RequestBody NoticeDto noticeDto) {
        return ResponseEntity.ok(noticeService.addNotice(noticeDto));
    }

    // delete notice from the system
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteNotice(@PathVariable Long id) {
        return ResponseEntity.ok(noticeService.deleteNotice(id));
    }

    // update notice in the system
    @PutMapping("/update/{id}")
    public ResponseEntity<NoticeDto> updateNotice(@PathVariable Long id, @RequestBody NoticeDto noticeDto) {
        return ResponseEntity.ok(noticeService.updateNotice(id, noticeDto));
    }
}
