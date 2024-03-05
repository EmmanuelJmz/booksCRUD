package com.example.examenlibros.controller;

import com.example.examenlibros.dto.ApiResponse;
import com.example.examenlibros.dto.BookRequestDTO;
import com.example.examenlibros.dto.BookResponseDTO;
import com.example.examenlibros.services.BookService;
import com.example.examenlibros.services.ImageService;
import com.example.examenlibros.util.ValueMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
@CrossOrigin(origins = {"*"})
@Slf4j
public class BookController {

    private final BookService bookService;

    private final ImageService imageService;

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse> createNewBook(@RequestPart @Valid BookRequestDTO bookRequestDTO){
        log.info("BookController:: Creando nuevo libro con el body {}", ValueMapper.jssonAsString(bookRequestDTO));


        BookResponseDTO bookResponseDTO = bookService.createNewBook(bookRequestDTO);

        ApiResponse<BookResponseDTO> responseDTO = ApiResponse
                .<BookResponseDTO>builder()
                .status("Realizado")
                .results(bookResponseDTO)
                .build();

        log.info("BookController:: respuesta {}", ValueMapper.jssonAsString(responseDTO));

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getBooks(){
        List<BookResponseDTO> books = bookService.getBooks();

        ApiResponse<List<BookResponseDTO>> responseDTO = ApiResponse
                .<List<BookResponseDTO>>builder()
                .status("Realizado")
                .results(books)
                .build();

        log.info("BookController:: obetener todos los libros {}", ValueMapper.jssonAsString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getBookById(@Valid @PathVariable Long id) throws Exception {
        BookResponseDTO movie = bookService.getBookById(id);

        ApiResponse<BookResponseDTO> responseDTO = ApiResponse
                .<BookResponseDTO>builder()
                .status("Success")
                .results(movie)
                .build();

        log.info("BookController:: obtener libro por id {}", ValueMapper.jssonAsString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/author")
    public ResponseEntity<ApiResponse> getBooksByAuthor(@RequestParam String author) {
        List<BookResponseDTO> books = bookService.getBooksByAuthorContaining(author);

        ApiResponse<List<BookResponseDTO>> responseDTO = ApiResponse
                .<List<BookResponseDTO>>builder()
                .status("Realizado")
                .results(books)
                .build();

        log.info("BookController:: libros por autor", ValueMapper.jssonAsString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

//    @GetMapping("/urlImage")
//    public ResponseEntity<ApiResponse> getBooksByImage(@RequestParam String urlImage) {
//        List<BookResponseDTO> books = bookService.findByUrlImageContaining(urlImage);
//
//        ApiResponse<List<BookResponseDTO>> responseDTO = ApiResponse
//                .<List<BookResponseDTO>>builder()
//                .status("Realizado")
//                .results(books)
//                .build();
//
//        log.info("BookController:: libros por imagenes", ValueMapper.jssonAsString(responseDTO));
//        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
//    }

    @GetMapping("/date")
    public ResponseEntity<ApiResponse> searchBooksOrderByPublishDateDesc() {
        List<BookResponseDTO> books = bookService.findByOrderByPublishDate();

        ApiResponse<List<BookResponseDTO>> responseDTO = ApiResponse
                .<List<BookResponseDTO>>builder()
                .status("Realizado")
                .results(books)
                .build();

        log.info("MovieController::libros por fecha de publicacion {}", ValueMapper.jssonAsString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateBook(@PathVariable Long id, @RequestBody @Valid BookRequestDTO bookRequestDTO) throws Exception {
        BookResponseDTO book = bookService.updateBook(id, bookRequestDTO);

        ApiResponse<BookResponseDTO> responseDTO = ApiResponse
                .<BookResponseDTO>builder()
                .status("Realizado")
                .results(book)
                .build();

        log.info("MovieController::actualizar libro {}", ValueMapper.jssonAsString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable Long id) throws Exception {
        bookService.deleteBook(id);

        ApiResponse<String> responseDTO = ApiResponse
                .<String>builder()
                .status("Realizado")
                .results("Libro eliminado")
                .build();

        log.info("MovieController::deleteMovie response {}", ValueMapper.jssonAsString(responseDTO));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
