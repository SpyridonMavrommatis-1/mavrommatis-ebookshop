package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dao.AuthorDetailsRepository;
import com.mavrommatis.ebookshop.ebookshop.entity.AuthorDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service 
public class AuthorDetailsServiceImpl implements AuthorDetailsService {

    private final AuthorDetailsRepository authorDetailsRepository;

    public AuthorDetailsServiceImpl(AuthorDetailsRepository authorDetailsRepository) {
        this.authorDetailsRepository = authorDetailsRepository;
    }

    @Override
    public List<AuthorDetails> findAll() {
        return authorDetailsRepository.findAll();
    }

    @Override
    public Optional<AuthorDetails> findById(Integer id) {
        return authorDetailsRepository.findById(id);
    }

    @Override
    public AuthorDetails save(AuthorDetails authorDetails) {
        if (authorDetails.getAuthorId() != 0 && authorDetailsRepository.existsById(authorDetails.getAuthorId())) {
            throw new RuntimeException("BookDetails already exists with id: " + authorDetails.getAuthorId());
        }
        return authorDetailsRepository.save(authorDetails);
    }

    @Override
    public List<AuthorDetails> saveAll(List<AuthorDetails> authorsDetails) {
        for (AuthorDetails authorDetails : authorsDetails) {
            if (authorDetails.getAuthorId() != 0 && authorDetailsRepository.existsById(authorDetails.getAuthorId())) {
                throw new RuntimeException("BookDetails already exists with id: " + authorDetails.getAuthorId());
            }
        }
        return authorDetailsRepository.saveAll(authorsDetails);
    }

    @Override
    public void deleteById(Integer id) {
        Optional<AuthorDetails> authorDetails = authorDetailsRepository.findById(id);

        if (authorDetails.isPresent()) {
            authorDetailsRepository.deleteById(id);
        } else {
            throw new RuntimeException("BookDetails not found with id: " + id);
        }
    }

    @Override
    public void deleteAllById(List<Integer> ids) {
        for (Integer id : ids) {
            if (authorDetailsRepository.existsById(id)) {
                authorDetailsRepository.deleteById(id);
            } else {
                throw new RuntimeException("BookDetails not found with id: " + id);
            }
        }
    }
}

