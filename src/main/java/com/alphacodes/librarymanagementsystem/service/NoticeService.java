package com.alphacodes.librarymanagementsystem.service;

import com.alphacodes.librarymanagementsystem.DTO.NoticeDto;
import com.alphacodes.librarymanagementsystem.DTO.NoticeViewDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoticeService {
    List<NoticeViewDto> getAllNotices();
    NoticeDto addNotice(NoticeDto noticeDto);
    String deleteNotice(Long id);
    NoticeDto updateNotice(Long id, NoticeDto noticeDto);
}
