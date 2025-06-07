package com.mavrommatis.ebookshop.ebookshop.controller;

import com.mavrommatis.ebookshop.ebookshop.entity.BookDetails;
import com.mavrommatis.ebookshop.ebookshop.service.BookDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing {@link BookDetails} entities using Thymeleaf views.
 * Provides endpoints for displaying, creating, editing, and deleting book details.
 */
@Controller
@RequestMapping("/bookdetails")
public class BookDetailsController {

    private final BookDetailsService bookDetailsService;

    /**
     * Constructor injection for BookDetailsService.
     *
     * @param bookDetailsService the service layer for BookDetails operations
     */
    @Autowired
    public BookDetailsController(BookDetailsService bookDetailsService) {
        this.bookDetailsService = bookDetailsService;
    }

    /**
     * Displays a list of all BookDetails.
     *
     * @param model the Spring model to pass data to the view
     * @return the view name for the list page
     */
    @GetMapping
    public String listBookDetails(Model model) {
        List<BookDetails> bookDetailsList = bookDetailsService.findAll();
        model.addAttribute("bookDetailsList", bookDetailsList);
        return "bookdetails-list";
    }

    /**
     * Displays the details of a single BookDetails entry.
     *
     * @param id the ID of the BookDetails
     * @param model the Spring model to pass data to the view
     * @return the view name for the detail page
     */
    @GetMapping("/{id}")
    public String showBookDetails(@PathVariable int id, Model model) {
        BookDetails details = bookDetailsService.findById(id)
                .orElseThrow(() -> new RuntimeException("BookDetails not found with id: " + id));
        model.addAttribute("bookDetails", details);
        return "bookdetails-view";
    }

    /**
     * Displays the form to create a new BookDetails entry.
     *
     * @param model the Spring model
     * @return the view name for the form
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("bookDetails", new BookDetails());
        return "bookdetails-form";
    }

    /**
     * Handles the form submission for creating a new BookDetails entry.
     *
     * @param bookDetails the BookDetails object from the form
     * @return redirect to the list view
     */
    @PostMapping
    public String saveBookDetails(@ModelAttribute("bookDetails") BookDetails bookDetails) {
        bookDetailsService.save(bookDetails);
        return "redirect:/bookdetails";
    }

    /**
     * Displays the form to edit an existing BookDetails entry.
     *
     * @param id the ID of the BookDetails to edit
     * @param model the Spring model
     * @return the view name for the form
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        BookDetails details = bookDetailsService.findById(id)
                .orElseThrow(() -> new RuntimeException("BookDetails not found with id: " + id));
        model.addAttribute("bookDetails", details);
        return "bookdetails-form";
    }

    /**
     * Handles the form submission to update an existing BookDetails entry.
     *
     * @param id the ID of the BookDetails
     * @param bookDetails the updated BookDetails object
     * @return redirect to the list view
     */
    @PostMapping("/update/{id}")
    public String updateBookDetails(@PathVariable int id, @ModelAttribute("bookDetails") BookDetails bookDetails) {
        bookDetails.setBookId(id);
        bookDetailsService.save(bookDetails);
        return "redirect:/bookdetails";
    }

    /**
     * Deletes a single BookDetails entry by ID.
     *
     * @param id the ID of the BookDetails to delete
     * @return redirect to the list view
     */
    @GetMapping("/delete/{id}")
    public String deleteBookDetails(@PathVariable int id) {
        bookDetailsService.deleteById(id);
        return "redirect:/bookdetails";
    }

    /**
     * Displays the batch delete form with all BookDetails entries.
     *
     * @param model the Spring model
     * @return the view name for the batch delete form
     */
    @GetMapping("/batch-delete")
    public String showBatchDeleteForm(Model model) {
        List<BookDetails> bookDetailsList = bookDetailsService.findAll();
        model.addAttribute("bookDetailsList", bookDetailsList);
        return "bookdetails-batch-delete";
    }

    /**
     * Handles the batch deletion of selected BookDetails entries.
     *
     * @param ids the list of IDs to delete
     * @return redirect to the list view
     */
    @PostMapping("/batch-delete")
    public String deleteSelectedBookDetails(@RequestParam List<Integer> ids) {
        bookDetailsService.deleteAllById(ids);
        return "redirect:/bookdetails";
    }
}
