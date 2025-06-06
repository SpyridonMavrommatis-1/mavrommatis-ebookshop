package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dao.AuthorDetailsRepository;
import com.mavrommatis.ebookshop.ebookshop.entity.AuthorDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for {@link AuthorDetailsService}.
 * Handles business logic related to the {@link AuthorDetails} entity.
 */
@Service
public class AuthorDetailsServiceImpl implements AuthorDetailsService {

    private final AuthorDetailsRepository authorDetailsRepository;

    /**
     * Constructor-based dependency injection of the repository.
     *
     * @param authorDetailsRepository repository for AuthorDetails.
     */
    public AuthorDetailsServiceImpl(AuthorDetailsRepository authorDetailsRepository) {
        this.authorDetailsRepository = authorDetailsRepository;
    }

    /**
     * Retrieve all AuthorDetails records from the database.
     *
     * @return list of AuthorDetails.
     */
    @Override
    public List<AuthorDetails> findAll() {
        return authorDetailsRepository.findAll();
    }

    /**
     * Retrieve an AuthorDetails record by its ID.
     *
     * @param id the ID to search.
     * @return an Optional of AuthorDetails.
     */
    @Override
    public Optional<AuthorDetails> findById(Integer id) {
        return authorDetailsRepository.findById(id);
    }

    /**
     * Save a new AuthorDetails record.
     * Throws an exception if the ID already exists.
     *
     * @param authorDetails the AuthorDetails to save.
     * @return the saved entity.
     */
    @Override
    public AuthorDetails save(AuthorDetails authorDetails) {
        if (authorDetails.getAuthorId() != 0 && authorDetailsRepository.existsById(authorDetails.getAuthorId())) {
            throw new RuntimeException("AuthorDetails already exists with id: " + authorDetails.getAuthorId());
        }
        return authorDetailsRepository.save(authorDetails);
    }

    /**
     * Save a list of AuthorDetails.
     * Each entry is validated before saving.
     *
     * @param authorsDetails list of AuthorDetails.
     * @return list of saved AuthorDetails.
     */
    @Override
    public List<AuthorDetails> saveAll(List<AuthorDetails> authorsDetails) {
        for (AuthorDetails authorDetails : authorsDetails) {
            if (authorDetails.getAuthorId() != 0 && authorDetailsRepository.existsById(authorDetails.getAuthorId())) {
                throw new RuntimeException("AuthorDetails already exists with id: " + authorDetails.getAuthorId());
            }
        }
        return authorDetailsRepository.saveAll(authorsDetails);
    }

    /**
     * Delete an AuthorDetails record by ID.
     * Throws exception if the ID does not exist.
     *
     * @param id ID to delete.
     */
    @Override
    public void deleteById(Integer id) {
        Optional<AuthorDetails> authorDetails = authorDetailsRepository.findById(id);

        if (authorDetails.isPresent()) {
            authorDetailsRepository.deleteById(id);
        } else {
            throw new RuntimeException("AuthorDetails not found with id: " + id);
        }
    }

    /**
     * Delete a list of AuthorDetails records by their IDs.
     * If any ID does not exist, exception is thrown.
     *
     * @param ids list of IDs to delete.
     */
    @Override
    public void deleteAllById(List<Integer> ids) {
        for (Integer id : ids) {
            if (authorDetailsRepository.existsById(id)) {
                authorDetailsRepository.deleteById(id);
            } else {
                throw new RuntimeException("AuthorDetails not found with id: " + id);
            }
        }
    }
}
