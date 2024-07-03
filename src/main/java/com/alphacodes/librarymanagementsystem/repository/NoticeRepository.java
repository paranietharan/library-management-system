package com.alphacodes.librarymanagementsystem.repository;

import com.alphacodes.librarymanagementsystem.Model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
