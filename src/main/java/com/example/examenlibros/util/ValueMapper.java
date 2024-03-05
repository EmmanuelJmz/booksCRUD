package com.example.examenlibros.util;

import com.example.examenlibros.dto.BookRequestDTO;
import com.example.examenlibros.dto.BookResponseDTO;
import com.example.examenlibros.entity.book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ValueMapper {
    public static book convertToEntity(BookRequestDTO bookRequestDTO){
        book book = new book();
        book.setName(bookRequestDTO.getName());
        book.setAuthor(bookRequestDTO.getAuthor());
        book.setPublishDate(bookRequestDTO.getPublishDate());
        return book;
    }

    public static BookResponseDTO convertToDTO(book book){
        BookResponseDTO bookResponseDTO = new BookResponseDTO();
        bookResponseDTO.setId(book.getId());
        bookResponseDTO.setAuthor(book.getAuthor());
        bookResponseDTO.setName(book.getName());
        bookResponseDTO.setPublishDate(book.getPublishDate());
        return bookResponseDTO;
    }

    public static String jssonAsString(Object ojc){
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(ojc);
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }
}
