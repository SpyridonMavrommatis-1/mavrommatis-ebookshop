package com.mavrommatis.ebookshop.ebookshop.service.details;

import com.mavrommatis.ebookshop.ebookshop.dao.BookDetailsRepository;
import com.mavrommatis.ebookshop.ebookshop.dto.details.BookDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.details.BookDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.exception.book.BookDetailsNotFoundException;
import com.mavrommatis.ebookshop.ebookshop.mapper.BookMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookDetailsServiceImpl implements BookDetailsService {

    private final BookDetailsRepository repo;
    private final BookMapper mapper;

    public BookDetailsServiceImpl(BookDetailsRepository repo, BookMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public List<BookDetailsDTO> findAll() {
        return repo.findAll().stream()
                .map(mapper::bookDetailsEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDetailsDTO findById(Integer id) {
        BookDetailsEntity entity = repo.findById(id)
                .orElseThrow(() -> new BookDetailsNotFoundException(id));
        return mapper.bookDetailsEntityToDto(entity);
    }

    @Override
    @Transactional
    public BookDetailsDTO save(BookDetailsDTO dto) {
        BookDetailsEntity entity = mapper.bookDetailsDtoToEntity(dto);
        BookDetailsEntity saved = repo.save(entity);
        return mapper.bookDetailsEntityToDto(saved);
    }

    @Override
    @Transactional
    public List<BookDetailsDTO> saveAll(List<BookDetailsDTO> dtos) {
        List<BookDetailsEntity> entities = dtos.stream()
                .map(mapper::bookDetailsDtoToEntity)
                .collect(Collectors.toList());
        List<BookDetailsEntity> saved = repo.saveAll(entities);
        return saved.stream()
                .map(mapper::bookDetailsEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        if (!repo.existsById(id)) {
            throw new BookDetailsNotFoundException(id);
        }
        repo.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllById(List<Integer> ids) {
        for (Integer id : ids) {
            if (!repo.existsById(id)) {
                throw new BookDetailsNotFoundException(id);
            }
        }
        repo.deleteAllById(ids);
    }
}