package com.mavrommatis.ebookshop.ebookshop.service.basic;


import com.mavrommatis.ebookshop.ebookshop.dao.AuthorRepository;
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

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

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

    @Override
    public void deleteById(Integer id) {
        if (!authorRepository.existsById(id)) {
            throw new AuthorNotFoundException(id);
        }
        authorRepository.deleteById(id);
    }

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

    private boolean hasRole(String roleName) {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals(roleName));
    }

    private boolean isCustomer() {
        return hasRole("ROLE_CUSTOMER");
    }
}