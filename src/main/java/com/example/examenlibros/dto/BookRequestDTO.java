package com.example.examenlibros.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BookRequestDTO {

    @NotNull(message = "El libro debe contener un nombre")
    private String name;

    @NotNull(message = "El libro debe contener un autor")
    private String author;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "La fecha de publicación no puede ser null o vacio")
    private LocalDate publishDate;

}
