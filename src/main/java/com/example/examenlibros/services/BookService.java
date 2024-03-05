package com.example.examenlibros.services;

import com.example.examenlibros.dto.BookRequestDTO;
import com.example.examenlibros.dto.BookResponseDTO;
import com.example.examenlibros.entity.book;
import com.example.examenlibros.entity.bookRepository;
import com.example.examenlibros.exception.BookNotFoundException;
import com.example.examenlibros.exception.BookServiceBusinessException;
import com.example.examenlibros.util.ValueMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BookService {

    private bookRepository bookRepository;

    public BookResponseDTO createNewBook(BookRequestDTO bookRequestDTO) throws BookServiceBusinessException{
        BookResponseDTO bookResponseDTO;
        try {
            log.info("BookService::Creando nuevo libro se ha ejecutado y creando");
            book book = ValueMapper.convertToEntity(bookRequestDTO);
            log.debug("BookService:: Creacion de nuevo libro con los siguientes atributos {}", ValueMapper.jssonAsString(bookRequestDTO));

            book bookResults = bookRepository.save(book);
            bookResponseDTO = ValueMapper.convertToDTO(bookResults);
            log.debug("BookService:Creacion de nuevo libro recibido en la BD {}", ValueMapper.jssonAsString(bookRequestDTO));

        }catch (Exception e){
            log.error("Ocurrio un error durante la creacion del libro en la BD, {}", e.getMessage());
            throw new BookServiceBusinessException("Excepcion ocurrio en la creacion del libro");
        }
        log.info("BookService:: creacion finalizada");
        return bookResponseDTO;
    }

    @Cacheable(value = "books")
    public List<BookResponseDTO> getBooks() throws BookServiceBusinessException{
        List<BookResponseDTO> bookResponseDTO = null;
        try {
            log.info("BookService:: comenzando a obtener todos los libros.");

            List<book> bookList = bookRepository.findAll();

            if (!bookList.isEmpty()){
                bookResponseDTO = bookList.stream()
                        .map(ValueMapper::convertToDTO)
                        .collect(Collectors.toList());
            }else {
                bookResponseDTO = Collections.emptyList();
            }
            log.debug("BookService:: libros de la BD {}", ValueMapper.jssonAsString(bookResponseDTO));

        }catch (Exception e){
            log.error("Ocurrio un error durante el get de los libros en la BD, {}", e.getMessage());
            throw new BookServiceBusinessException("Excepcion ocurrio en el get de los libros");
        }
        log.info("Servicio de obetener todos los libro finalizado");
        return bookResponseDTO;
    }

    @Cacheable(value = "books")
    public List<BookResponseDTO> getBooksByAuthorContaining(String author) throws BookServiceBusinessException{
        List<BookResponseDTO> bookResponseDTO = null;
        try {
            log.info("BookService:: comenzando a obtener libros por autor.");

            List<book> bookList = bookRepository.findByAuthorContaining(author);

            if (!bookList.isEmpty()){
                bookResponseDTO = bookList.stream()
                        .map(ValueMapper::convertToDTO)
                        .collect(Collectors.toList());
            }else {
                bookResponseDTO = Collections.emptyList();
            }
            log.debug("Servicio de libros:: libros de la BD {}", ValueMapper.jssonAsString(bookResponseDTO));
        }catch (Exception e){
            log.error("Ocurrio un error durante el get de los libros en la BD, {}", e.getMessage());
            throw new BookServiceBusinessException("Excepcion ocurrio en el get de los libros");
        }
        log.info("Servicio de libros por autor finalizado");
        return bookResponseDTO;
    }

//    @Cacheable(value = "books")
//    public List<BookResponseDTO> findByUrlImageContaining(String urlImage) throws BookServiceBusinessException{
//        List<BookResponseDTO> bookResponseDTO = null;
//        try {
//            log.info("BookService:: comenzando a obtener libros por imagen.");
//
//            List<book> bookList = bookRepository.findByUrlImageContaining(urlImage);
//
//            if (!bookList.isEmpty()){
//                bookResponseDTO = bookList.stream()
//                        .map(ValueMapper::convertToDTO)
//                        .collect(Collectors.toList());
//            }else {
//                bookResponseDTO = Collections.emptyList();
//            }
//            log.debug("Servicio de libros:: libros de la BD {}", ValueMapper.jssonAsString(bookResponseDTO));
//        }catch (Exception e){
//            log.error("Ocurrio un error durante el get de los libros en la BD, {}", e.getMessage());
//            throw new BookServiceBusinessException("Excepcion ocurrio en el get de los libros");
//        }
//        log.info("Servicio de libros por imagen finalizado");
//        return bookResponseDTO;
//    }

    @Cacheable(value = "books")
    public List<BookResponseDTO> findByOrderByPublishDate() throws BookServiceBusinessException {
        List<BookResponseDTO> bookResponseDTO = null;

        try {
            log.info("MovieService:getMoviesOrderByPublishDateDesc execution started.");

            List<book> bookList = bookRepository.findByOrderByPublishDate();

            if (!bookList.isEmpty()) {
                bookResponseDTO = bookList.stream()
                        .map(ValueMapper::convertToDTO)
                        .collect(Collectors.toList());
            } else {
                bookResponseDTO = Collections.emptyList();
            }
            log.debug("BookService:getBooksOrderByPublishDateDesc retrieving books from database  {}", ValueMapper.jssonAsString(bookResponseDTO));

        } catch (Exception e) {
            log.error("Exception ocurred while retrieving books from database, Exception message {}", e.getMessage());
            throw new BookServiceBusinessException("Exception occurred while fetch all books from Database");
        }
        log.info("BooksService:getBooksOrderByPublishDateDesc execution ended");
        return bookResponseDTO;
    }

    @Cacheable(value = "books")
    public BookResponseDTO getBookById(long id){
        BookResponseDTO bookResponseDTO;
        try {
            log.info("booksService:books execution started");

            book book = bookRepository.findById(id)
                    .orElseThrow(() -> new BookNotFoundException("Movie not found with id" + id));
            bookResponseDTO = ValueMapper.convertToDTO(book);

            log.debug("MovieService:getMovieById retrieving product from database for id {} {}", id, ValueMapper.jssonAsString(bookResponseDTO));

        }catch (Exception e){
            log.error("Exception occurred while retrieving books {} from database , Exception message {}", id, e.getMessage());
            throw new BookServiceBusinessException("Exception occurred while fetch books from Database " + id);
        }
        log.info("BooksService:getbookById execution ended");
        return bookResponseDTO;
    }

    public BookResponseDTO updateBook (long id, BookRequestDTO bookRequestDTO){
        BookResponseDTO bookResponseDTO;
        try {
            log.info("BookService::actualizando libro...");

            book book = bookRepository.findById(id)
                    .orElseThrow(() -> new BookNotFoundException("Libro no encontrado con el id: " + id));
            book.setName(bookRequestDTO.getName());
            book.setAuthor(bookRequestDTO.getAuthor());
            book.setPublishDate(bookRequestDTO.getPublishDate());


            book bookResults = bookRepository.save(book);
            bookResponseDTO = ValueMapper.convertToDTO(bookResults);

            log.debug("Actualizando el libro con el id {} ", id, ValueMapper.jssonAsString(bookResponseDTO));
        }catch (Exception e){
            log.error("Exception ocurrida durante la ejecucion del libro {}", id, e.getMessage());
            throw new BookServiceBusinessException("Exception ocurrido durante la actualizacion del libro {} " + id);
        }
        log.info("Actualización finalizada");
        return bookResponseDTO;
    }

    public void deleteBook(long id){
        try {
            log.info("BookService::Eliminación de libro ha comenzado...");

            book book = bookRepository.findById(id)
                    .orElseThrow(() -> new BookNotFoundException("Libro no encontrado con el id {}" + id));
            bookRepository.delete(book);

            log.debug("BookService:: eliminando el libro con el id {}", id);
        }catch (Exception e){
            log.error("Excepcion ocurrida mientras se eliminaba un libro {}", id, e.getMessage());
        }
        log.info("BookService:: libro eliminado");
    }
}
