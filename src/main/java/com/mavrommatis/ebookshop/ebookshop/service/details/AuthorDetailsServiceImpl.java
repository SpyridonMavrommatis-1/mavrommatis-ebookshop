package com.mavrommatis.ebookshop.ebookshop.service.details;

import com.mavrommatis.ebookshop.ebookshop.dao.AuthorDetailsRepository;
import com.mavrommatis.ebookshop.ebookshop.dto.details.AuthorDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.details.AuthorDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.exception.author.AuthorDetailsNotFoundException;
import com.mavrommatis.ebookshop.ebookshop.mapper.AuthorMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorDetailsServiceImpl implements AuthorDetailsService {

    private final AuthorDetailsRepository repository;
    private final AuthorMapper mapper;

    public AuthorDetailsServiceImpl(AuthorDetailsRepository repository, AuthorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<AuthorDetailsDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDetailsDTO findById(Integer authorId) {
        AuthorDetailsEntity entity = repository.findById(authorId)
                .orElseThrow(() -> new AuthorDetailsNotFoundException(authorId));
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public AuthorDetailsDTO save(AuthorDetailsDTO dto) {
        AuthorDetailsEntity entity = mapper.toEntity(dto);
        AuthorDetailsEntity saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public List<AuthorDetailsDTO> saveAll(List<AuthorDetailsDTO> dtos) {
        List<AuthorDetailsEntity> entities = dtos.stream()
                .map(mapper::toEntity)
                .collect(Collectors.toList());

        List<AuthorDetailsEntity> saved = repository.saveAll(entities);
        return saved.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer authorId) {
        if (!repository.existsById(authorId)) {
            throw new AuthorDetailsNotFoundException(authorId);
        }
        repository.deleteById(authorId);
    }

    @Override
    @Transactional
    public void deleteAllById(List<Integer> authorIds) {
        for (Integer id : authorIds) {
            if (!repository.existsById(id)) {
                throw new AuthorDetailsNotFoundException(id);
            }
        }
        repository.deleteAllById(authorIds);
    }
}