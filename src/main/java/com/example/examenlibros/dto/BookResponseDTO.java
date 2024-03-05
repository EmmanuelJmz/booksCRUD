package com.example.examenlibros.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookResponseDTO {
    private Long id;
    private String name;
    private String author;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishDate;

}
