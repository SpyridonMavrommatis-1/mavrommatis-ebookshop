package com.mavrommatis.ebookshop.ebookshop.service.basic;

import com.mavrommatis.ebookshop.ebookshop.dao.basic.AuthorRepository;
import com.mavrommatis.ebookshop.ebookshop.dto.request.AuthorRequestDTO;
import com.mavrommatis.ebookshop.ebookshop.dto.response.AuthorResponseDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.basic.AuthorEntity;
import com.mavrommatis.ebookshop.ebookshop.entity.details.AuthorDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.exception.author.AuthorNotFoundException;
import com.mavrommatis.ebookshop.ebookshop.mapper.AuthorMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing authors and their details.
 * <p>
 * Provides CRUD functionality and filters out sensitive information
 * (like email) for customers without elevated roles.
 * </p>
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    /**
     * Repository for performing database operations on authors.
     */
    private final AuthorRepository authorRepository;

    /**
     * Mapper to convert between AuthorEntity and Author DTOs.
     */
    private final AuthorMapper authorMapper;

    /**
     * Constructs the AuthorServiceImpl with necessary dependencies.
     *
     * @param authorRepository repository for authors
     * @param authorMapper     mapper for author data
     */
    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    /**
     * Retrieves all authors.
     * If the current user is a customer, email addresses are hidden.
     *
     * @return list of author response DTOs
     */
    @Override
    public List<AuthorResponseDTO> findAll() {
        List<AuthorResponseDTO> authors = authorRepository.findAll().stream()
                .map(authorMapper::toResponse)
                .collect(Collectors.toList());

        if (isCustomer()) {
            authors.forEach(author -> author.setEmail(null));
        }

        return authors;
    }

    /**
     * Finds a single author by ID. Hides email if caller is a customer.
     *
     * @param id the ID of the author
     * @return author response DTO
     * @throws AuthorNotFoundException if author not found
     */
    @Override
    public AuthorResponseDTO findById(Integer id) {
        AuthorEntity entity = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));

        AuthorResponseDTO dto = authorMapper.toResponse(entity);
        if (isCustomer()) {
            dto.setEmail(null);
        }
        return dto;
    }

    /**
     * Saves a new author along with optional details.
     *
     * @param dto request DTO containing author info
     * @return saved author as response DTO
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
     * Updates an existing author and their details.
     *
     * @param id  the ID of the author to update
     * @param dto request DTO containing updated data
     * @return updated author as response DTO
     * @throws AuthorNotFoundException if author does not exist
     */
    @Override
    @Transactional
    public AuthorResponseDTO update(Integer id, AuthorRequestDTO dto) {
        AuthorEntity existing = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));

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
     * Deletes a specific author by ID.
     *
     * @param id the ID of the author to delete
     * @throws AuthorNotFoundException if author is not found
     */
    @Override
    public void deleteById(Integer id) {
        if (!authorRepository.existsById(id)) {
            throw new AuthorNotFoundException(id);
        }
        authorRepository.deleteById(id);
    }

    /**
     * Deletes multiple authors by their IDs.
     *
     * @param ids list of IDs to delete
     * @throws AuthorNotFoundException if any author is not found
     */
    @Override
    @Transactional
    public void deleteAllById(List<Integer> ids) {
        for (Integer id : ids) {
            if (!authorRepository.existsById(id)) {
                throw new AuthorNotFoundException(id);
            }
        }
        authorRepository.deleteAllById(ids);
    }

    /**
     * Checks if the current user has the specified role.
     *
     * @param roleName the role name (e.g., ROLE_ADMIN)
     * @return true if user has role, false otherwise
     */
    private boolean hasRole(String roleName) {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals(roleName));
    }

    /**
     * Determines whether the current user has the CUSTOMER role.
     *
     * @return true if user is a customer
     */
    private boolean isCustomer() {
        return hasRole("ROLE_CUSTOMER");
    }
}
