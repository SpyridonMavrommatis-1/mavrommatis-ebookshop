package com.mavrommatis.ebookshop.ebookshop.service.basic;

import com.mavrommatis.ebookshop.ebookshop.dao.AuthorRepository;
import com.mavrommatis.ebookshop.ebookshop.dao.BookRepository;
import com.mavrommatis.ebookshop.ebookshop.dto.request.BookRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.response.BookResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.BookEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.details.BookDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.exception.book.AuthorNotFoundException;
import com.mavrommatis.ebookshop.ebookshop.exception.book.BookNotFoundException;
import com.mavrommatis.ebookshop.ebookshop.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository,
                           BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    public List<BookResponseDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toResponse)
                .toList();
    }

    @Override
    public BookResponseDTO findById(Integer id) {
        BookEntity entity = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        return bookMapper.toResponse(entity);
    }

    @Override
    @Transactional
    public BookResponseDTO save(BookRequestDTO dto) {
        BookEntity book = bookMapper.toEntity(dto);

        if (dto.getDetails() != null) {
            BookDetailsEntity details = bookMapper.bookDetailsDtoToEntity(dto.getDetails());
            details.setBook(book);
            book.setBookDetails(details);
        }

        AuthorEntity author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new AuthorNotFoundException(dto.getAuthorId()));
        book.setAuthor(author);

        BookEntity saved = bookRepository.save(book);
        return bookMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public BookResponseDTO update(Integer id, BookRequestDTO dto) {
        BookEntity existing = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        bookMapper.updateEntity(dto, existing);

        AuthorEntity author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new AuthorNotFoundException(dto.getAuthorId()));
        existing.setAuthor(author);

        BookEntity updated = bookRepository.save(existing);
        return bookMapper.toResponse(updated);
    }

    @Override
    public void deleteById(Integer id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllById(List<Integer> ids) {
        for (Integer id : ids) {
            if (!bookRepository.existsById(id)) {
                throw new BookNotFoundException(id);
            }
        }
        bookRepository.deleteAllById(ids);
    }
}