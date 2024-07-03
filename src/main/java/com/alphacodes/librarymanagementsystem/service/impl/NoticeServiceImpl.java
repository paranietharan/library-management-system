package com.alphacodes.librarymanagementsystem.service.impl;

import com.alphacodes.librarymanagementsystem.DTO.NoticeDto;
import com.alphacodes.librarymanagementsystem.DTO.NoticeViewDto;
import com.alphacodes.librarymanagementsystem.Model.Notice;
import com.alphacodes.librarymanagementsystem.repository.NoticeRepository;
import com.alphacodes.librarymanagementsystem.repository.UserRepository;
import com.alphacodes.librarymanagementsystem.service.NoticeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    public NoticeServiceImpl(NoticeRepository noticeRepository, UserRepository userRepository) {
        this.noticeRepository = noticeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<NoticeViewDto> getAllNotices() {
        List<Notice> notices = noticeRepository.findAll();
        return notices
                .stream()
                .map(this::convertToNoticeViewDto)
                .collect(Collectors.toList());
    }

    @Override
    public NoticeDto addNotice(NoticeDto noticeDto) {
        Notice notice = convertToNoticeEntity(noticeDto);
        noticeRepository.save(notice);
        return noticeDto;
    }

    @Override
    public String deleteNotice(Long id) {
        noticeRepository.deleteById(id);
        return "Notice deleted successfully";
    }

    @Override
    public NoticeDto updateNotice(Long id, NoticeDto noticeDto) {
        Notice notice = convertToNoticeEntity(noticeDto);
        notice.setId(id);
        noticeRepository.save(notice);
        return noticeDto;
    }

    // convert notice entity to notice view dto
    private NoticeViewDto convertToNoticeViewDto(Notice notice) {
        NoticeViewDto noticeViewDto = new NoticeViewDto();
        noticeViewDto.setId(notice.getId());
        noticeViewDto.setTitle(notice.getTitle());
        noticeViewDto.setMessage(notice.getMessage());
        return noticeViewDto;
    }

    // convert notice dto to notice entity
    private Notice convertToNoticeEntity(NoticeDto noticeDto) {
        Notice notice = new Notice();
        notice.setTitle(noticeDto.getTitle());
        notice.setMessage(noticeDto.getMessage());
        return notice;
    }
}
