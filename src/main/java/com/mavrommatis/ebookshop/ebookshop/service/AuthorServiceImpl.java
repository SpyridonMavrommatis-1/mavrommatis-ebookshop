package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dao.AuthorRepository;
import com.mavrommatis.ebookshop.ebookshop.dto.AuthorRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.AuthorResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.AuthorDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.AuthorEntity;
import com.mavrommatis.ebookshop.ebookshop.mapper.AuthorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for {@link AuthorService}, handling business logic
 * and mapping between DTOs and entities via {@link AuthorMapper}.
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    /**
     * Constructs an AuthorServiceImpl with the given repository and mapper.
     *
     * @param authorRepository repository for Author entities
     * @param authorMapper     mapper for converting between Author DTOs and entities
     */
    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository,
                             AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper     = authorMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AuthorResponseDTO> findAll() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AuthorResponseDTO findById(Integer id) {
        AuthorEntity entity = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found: " + id));
        return authorMapper.toResponse(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public AuthorResponseDTO save(AuthorRequestDTO dto) {
        AuthorEntity author = authorMapper.toEntity(dto);
        if (dto.getAuthorDetails() != null) {
            AuthorDetailsEntity details = authorMapper.toEntity(dto.getAuthorDetails());
            details.setAuthor(author);
            author.setAuthorDetails(details);
        }
        AuthorEntity saved = authorRepository.save(author);
        return authorMapper.toResponse(saved);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public AuthorResponseDTO update(Integer id, AuthorRequestDTO dto) {
        if (!authorRepository.existsById(id)) {
            throw new RuntimeException("Cannot update. Author not found: " + id);
        }
        AuthorEntity existing = authorRepository.findById(id).get();
        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setEmail(dto.getEmail());
        if (dto.getAuthorDetails() != null) {
            AuthorDetailsEntity newDetails = authorMapper.toEntity(dto.getAuthorDetails());
            if (existing.getAuthorDetails() == null) {
                newDetails.setAuthor(existing);
                existing.setAuthorDetails(newDetails);
            } else {
                AuthorDetailsEntity managed = existing.getAuthorDetails();
                managed.setBiography(newDetails.getBiography());
                managed.setBirthDate(newDetails.getBirthDate());
                managed.setWebsite(newDetails.getWebsite());
            }
        }
        AuthorEntity updated = authorRepository.save(existing);
        return authorMapper.toResponse(updated);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteById(Integer id) {
        if (!authorRepository.existsById(id)) {
            throw new RuntimeException("Author not found: " + id);
        }
        authorRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteAllById(List<Integer> ids) {
        for (Integer id : ids) {
            if (!authorRepository.existsById(id)) {
                throw new RuntimeException("Author not found: " + id);
            }
        }
        authorRepository.deleteAllById(ids);
    }
}
