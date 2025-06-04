package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dao.AuthorRepository;
import com.mavrommatis.ebookshop.ebookshop.entity.Author;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService{

    //See BookServiceImpl notes

    private final AuthorRepository authorRepository;


    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(Integer id) {
        return authorRepository.findById(id);
    }

    @Override
    public Author save(Author author) {
        if (author.getAuthorId() != 0 && authorRepository.existsById(author.getAuthorId())) {
            throw new RuntimeException("Author already exists with id: " + author.getAuthorId());
        }
        return authorRepository.save(author);
    }

    @Override
    public List<Author> saveAll(List<Author> authors) {
        for (Author author : authors) {
            if (author.getAuthorId() != 0 && authorRepository.existsById(author.getAuthorId())) {
                throw new RuntimeException("Author already exists with id: " + author.getAuthorId());
            }
        }
        return authorRepository.saveAll(authors);
    }

    @Override
    public void deleteById(Integer id) {

        Optional<Author> author = authorRepository.findById(id);

        if (author.isPresent()) {
            authorRepository.deleteById(id);
        } else {
            throw new RuntimeException("Author not found with id: " + id);
        }
    }

    @Override
    public void deleteAllById(List<Integer> ids) {
        for (Integer id : ids) {
            if (authorRepository.existsById(id)) {
                authorRepository.deleteById(id);
            } else {
                throw new RuntimeException("Author not found with id: " + id);
            }
        }
    }


}
