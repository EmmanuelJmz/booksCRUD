package com.example.examenlibros.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface bookRepository extends JpaRepository<book, Long> {
    List<book> findByOrderByPublishDate();
    List<book> findByAuthorContaining(String author);

}
