package com.mavrommatis.ebookshop.ebookshop.controller;

import com.mavrommatis.ebookshop.ebookshop.entity.BookDetails;
import com.mavrommatis.ebookshop.ebookshop.service.BookDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MVC Controller for managing {@link BookDetails} using HTML views.
 * Handles CRUD and batch operations with Thymeleaf integration.
 */
@Controller
@RequestMapping("/book-details")
public class BookDetailsController {

    private final BookDetailsService bookDetailsService;

    /**
     * Constructor-based dependency injection for BookDetailsService.
     *
     * @param bookDetailsService the service to manage BookDetails
     */
    @Autowired
    public BookDetailsController(BookDetailsService bookDetailsService) {
        this.bookDetailsService = bookDetailsService;
    }

    /**
     * Displays a list of all BookDetails entries.
     *
     * @param model the Spring model to pass data to the view
     * @return the name of the HTML view for listing BookDetails
     */
    @GetMapping
    public String findAll(Model model) {
        List<BookDetails> bookDetailsList = bookDetailsService.findAll();
        model.addAttribute("bookDetailsList", bookDetailsList);
        return "book-details-list";
    }

    /**
     * Displays details of a specific BookDetails entry by ID.
     *
     * @param id    the ID of the BookDetails to retrieve
     * @param model the Spring model to pass data to the view
     * @return the name of the HTML view showing the details
     */
    @GetMapping("/find/{id}")
    public String findById(@PathVariable int id, Model model) {
        BookDetails details = bookDetailsService.findById(id)
                .orElseThrow(() -> new RuntimeException("BookDetails not found with id: " + id));
        model.addAttribute("bookDetails", details);
        return "book-details-by-id";
    }

    /**
     * Displays a form to create a new BookDetails entry.
     *
     * @param model the Spring model to pass a new BookDetails object
     * @return the name of the HTML form view
     */
    @GetMapping("/find/create-new")
    public String showCreateForm(Model model) {
        model.addAttribute("bookDetails", new BookDetails());
        return "book-details-form";
    }

    /**
     * Saves a new BookDetails entry submitted from a form.
     *
     * @param bookDetails the form-bound BookDetails object
     * @return redirection to the list of BookDetails
     */
    @PostMapping
    public String create(@ModelAttribute("bookDetails") BookDetails bookDetails) {
        bookDetailsService.save(bookDetails);
        return "redirect:/book-details";
    }

    /**
     * Displays a form to edit an existing BookDetails entry.
     *
     * @param id    the ID of the entry to edit
     * @param model the Spring model to pass the BookDetails object
     * @return the name of the HTML form view pre-filled with data
     */
    @GetMapping("/get-edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        BookDetails details = bookDetailsService.findById(id)
                .orElseThrow(() -> new RuntimeException("BookDetails not found with id: " + id));
        model.addAttribute("bookDetails", details);
        return "book-details-form";
    }

    /**
     * Updates an existing BookDetails entry with submitted form data.
     *
     * @param id          the ID of the BookDetails entry to update
     * @param bookDetails the updated BookDetails object
     * @return redirection to the list view
     */
    @PostMapping("/update/{id}")
    public String update(@PathVariable int id, @ModelAttribute("bookDetails") BookDetails bookDetails) {
        bookDetails.setBookId(id);
        bookDetailsService.save(bookDetails);
        return "redirect:/book-details";
    }


    /**
     * Displays a form for batch creation of BookDetails entries.
     *
     * @param model the Spring model with an example list
     * @return the name of the HTML view for batch form
     */
    @GetMapping("/batch-save")
    public String showBatchForm(Model model) {
        model.addAttribute("bookDetailsList", List.of(new BookDetails(), new BookDetails()));
        return "book-details-batch-form";
    }

    /**
     * Saves multiple BookDetails entries from a form.
     *
     * @param bookDetailsList the list of BookDetails objects
     * @return redirection to the list view
     */
    @PostMapping("/batch-save-all")
    public String saveAll(@ModelAttribute("bookDetailsList") List<BookDetails> bookDetailsList) {
        bookDetailsService.saveAll(bookDetailsList);
        return "redirect:/book-details";
    }

    /**
     * Deletes a BookDetails entry by ID.
     *
     * @param id the ID of the BookDetails to delete
     * @return redirection to the list view
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        bookDetailsService.deleteById(id);
        return "redirect:/book-details";
    }

    /**
     * Displays a form to select multiple BookDetails entries for deletion.
     *
     * @param model the Spring model with all current entries
     * @return the name of the HTML view for batch deletion
     */
    @GetMapping("/batch-show-all")
    public String showBatchDeleteForm(Model model) {
        List<BookDetails> bookDetailsList = bookDetailsService.findAll();
        model.addAttribute("bookDetailsList", bookDetailsList);
        return "book-details-batch-select";
    }

    /**
     * Deletes multiple BookDetails entries by their IDs.
     *
     * @param ids the list of IDs to delete
     * @return redirection to the list view
     */
    @PostMapping("/batch-delete-all")
    public String deleteAllBookDetails(@RequestParam List<Integer> ids) {
        bookDetailsService.deleteAllById(ids);
        return "redirect:/book-details";
    }
}
