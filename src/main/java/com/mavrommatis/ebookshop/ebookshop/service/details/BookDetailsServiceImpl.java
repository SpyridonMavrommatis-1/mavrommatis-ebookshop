package com.mavrommatis.ebookshop.ebookshop.service.details;

import com.mavrommatis.ebookshop.ebookshop.dao.details.BookDetailsRepository;
import com.mavrommatis.ebookshop.ebookshop.dto.details.BookDetailsDTO;
import com.mavrommatis.ebookshop.ebookshop.entity.details.BookDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.exception.book.BookDetailsNotFoundException;
import com.mavrommatis.ebookshop.ebookshop.mapper.BookMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing BookDetails entities.
 * <p>
 * Provides CRUD operations for book details and handles conversions
 * between {@link BookDetailsDTO} and {@link BookDetailsEntity} using {@link BookMapper}.
 * </p>
 */
@Service
public class BookDetailsServiceImpl implements BookDetailsService {

    /**
     * Repository to manage book details data persistence.
     */
    private final BookDetailsRepository repo;

    /**
     * Mapper to transform between DTOs and entities.
     */
    private final BookMapper mapper;

    /**
     * Constructs the service with the required repository and mapper.
     *
     * @param repo   the book details repository
     * @param mapper the book mapper for data transformation
     */
    public BookDetailsServiceImpl(BookDetailsRepository repo, BookMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    /**
     * Retrieves all book details.
     *
     * @return list of book details as DTOs
     */
    @Override
    public List<BookDetailsDTO> findAll() {
        return repo.findAll().stream()
                .map(mapper::bookDetailsEntityToDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves book details by ID.
     *
     * @param id the ID of the book details
     * @return corresponding BookDetailsDTO
     * @throws BookDetailsNotFoundException if not found
     */
    @Override
    public BookDetailsDTO findById(Integer id) {
        BookDetailsEntity entity = repo.findById(id)
                .orElseThrow(() -> new BookDetailsNotFoundException(id));
        return mapper.bookDetailsEntityToDto(entity);
    }

    /**
     * Saves a new book details entry.
     *
     * @param dto the book details to save
     * @return the saved BookDetailsDTO
     */
    @Override
    @Transactional
    public BookDetailsDTO save(BookDetailsDTO dto) {
        BookDetailsEntity entity = mapper.bookDetailsDtoToEntity(dto);
        BookDetailsEntity saved = repo.save(entity);
        return mapper.bookDetailsEntityToDto(saved);
    }

    /**
     * Saves multiple book details entries.
     *
     * @param dtos list of book details DTOs
     * @return list of saved DTOs
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
     * Deletes book details by ID.
     *
     * @param id the ID to delete
     * @throws BookDetailsNotFoundException if the ID does not exist
     */
    @Override
    public void deleteById(Integer id) {
        if (!repo.existsById(id)) {
            throw new BookDetailsNotFoundException(id);
        }
        repo.deleteById(id);
    }

    /**
     * Deletes multiple book details entries by ID.
     *
     * @param ids list of IDs to delete
     * @throws BookDetailsNotFoundException if any ID does not exist
     */
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