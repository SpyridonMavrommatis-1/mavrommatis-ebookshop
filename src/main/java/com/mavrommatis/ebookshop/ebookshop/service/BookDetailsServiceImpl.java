package com.mavrommatis.ebookshop.ebookshop.service;

import com.mavrommatis.ebookshop.ebookshop.dao.BookDetailsRepository;
import com.mavrommatis.ebookshop.ebookshop.dto.BookDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.BookDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.mapper.BookMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for {@link BookDetailsService}, providing CRUD
 * operations for book details metadata and mapping via {@link BookMapper}.
 */
@Service
public class BookDetailsServiceImpl implements BookDetailsService {

    private final BookDetailsRepository repo;
    private final BookMapper mapper;

    /**
     * Constructs a new BookDetailsServiceImpl with required dependencies.
     *
     * @param repo   the repository used for BookDetails persistence operations
     * @param mapper the mapper for converting between DTOs and entities
     */
    public BookDetailsServiceImpl(BookDetailsRepository repo, BookMapper mapper) {
        this.repo   = repo;
        this.mapper = mapper;
    }

    /**
     * Retrieves all book details records.
     *
     * @return a list of {@link BookDetailsDTO} for every record in the database
     */
    @Override
    public List<BookDetailsDTO> findAll() {
        return repo.findAll().stream()
                .map(mapper::bookDetailsEntityToDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific book details record by its identifier.
     *
     * @param id the identifier of the book details
     * @return the corresponding {@link BookDetailsDTO}
     * @throws RuntimeException if no record is found for the given id
     */
    @Override
    public BookDetailsDTO findById(Integer id) {
        BookDetailsEntity entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("BookDetails not found: " + id));
        return mapper.bookDetailsEntityToDto(entity);
    }

    /**
     * Creates or updates a book details record.
     * <p>
     * If a record with the same primary key exists, it will be updated;
     * otherwise a new record is inserted.
     * </p>
     *
     * @param dto the {@link BookDetailsDTO} containing data to persist
     * @return the persisted {@link BookDetailsDTO}
     */
    @Override
    @Transactional
    public BookDetailsDTO save(BookDetailsDTO dto) {
        BookDetailsEntity entity = mapper.bookDetailsDtoToEntity(dto);
        BookDetailsEntity saved = repo.save(entity);
        return mapper.bookDetailsEntityToDto(saved);
    }

    /**
     * Creates or updates multiple book details records in batch.
     *
     * @param dtos a list of {@link BookDetailsDTO} to persist
     * @return a list of persisted {@link BookDetailsDTO}
     */
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

    /**
     * Deletes a specific book details record by its identifier.
     *
     * @param id the identifier of the record to delete
     * @throws RuntimeException if no record exists for the given id
     */
    @Override
    public void deleteById(Integer id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("BookDetails not found: " + id);
        }
        repo.deleteById(id);
    }

    /**
     * Deletes multiple book details records by their identifiers.
     *
     * @param ids a list of identifiers for records to delete
     * @throws RuntimeException if any provided id does not match an existing record
     */
    @Override
    @Transactional
    public void deleteAllById(List<Integer> ids) {
        for (Integer id : ids) {
            if (!repo.existsById(id)) {
                throw new RuntimeException("BookDetails not found: " + id);
            }
        }
        repo.deleteAllById(ids);
    }
}
