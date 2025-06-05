package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dao.BookDetailsRepository;
import com.mavrommatis.ebookshop.ebookshop.entity.BookDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookDetailsServiceImpl implements BookDetailsService{

    private final BookDetailsRepository bookDetailsRepository;

    public BookDetailsServiceImpl(BookDetailsRepository bookDetailsRepository) {
        this.bookDetailsRepository = bookDetailsRepository;
    }

    @Override
    public List<BookDetails> findAll() {
        return bookDetailsRepository.findAll();
    }

    @Override
    public Optional<BookDetails> findById(Integer id) {
        return bookDetailsRepository.findById(id);
    }

    @Override
    public BookDetails save(BookDetails bookDetails) {
        if (bookDetails.getBookId() != 0 && bookDetailsRepository.existsById(bookDetails.getBookId())) {
            throw new RuntimeException("BookDetails already exists with id: " + bookDetails.getBookId());
        }
        return bookDetailsRepository.save(bookDetails);
    }

    @Override
    public List<BookDetails> saveAll(List<BookDetails> booksDetails) {
        for (BookDetails bookDetails : booksDetails) {
            if (bookDetails.getBookId() != 0 && bookDetailsRepository.existsById(bookDetails.getBookId())) {
                throw new RuntimeException("BookDetails already exists with id: " + bookDetails.getBookId());
            }
        }
        return bookDetailsRepository.saveAll(booksDetails);
    }

    @Override
    public void deleteById(Integer id) {
        Optional<BookDetails> bookDetails = bookDetailsRepository.findById(id);

        if (bookDetails.isPresent()) {
            bookDetailsRepository.deleteById(id);
        } else {
            throw new RuntimeException("BookDetails not found with id: " + id);
        }
    }

    @Override
    public void deleteAllById(List<Integer> ids) {
        for (Integer id : ids) {
            if (bookDetailsRepository.existsById(id)) {
                bookDetailsRepository.deleteById(id);
            } else {
                throw new RuntimeException("BookDetails not found with id: " + id);
            }
        }
    }
}
