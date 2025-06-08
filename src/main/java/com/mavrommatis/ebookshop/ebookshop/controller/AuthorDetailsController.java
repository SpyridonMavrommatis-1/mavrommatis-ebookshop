package com.mavrommatis.ebookshop.ebookshop.controller;

import com.mavrommatis.ebookshop.ebookshop.entity.AuthorDetails;
import com.mavrommatis.ebookshop.ebookshop.service.AuthorDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MVC Controller for managing {@link AuthorDetails} using HTML views.
 * Handles CRUD and batch operations with Thymeleaf integration.
 */
@Controller
@RequestMapping("/author-details")
public class AuthorDetailsController {

    private final AuthorDetailsService authorDetailsService;

    /**
     * Constructor-based dependency injection for AuthorDetailsService.
     *
     * @param authorDetailsService the service to manage AuthorDetails
     */
    @Autowired
    public AuthorDetailsController(AuthorDetailsService authorDetailsService) {
        this.authorDetailsService = authorDetailsService;
    }

    /**
     * Displays a list of all AuthorDetails entries.
     *
     * @param model the Spring model to pass data to the view
     * @return the name of the HTML view for listing AuthorDetails
     */
    @GetMapping
    public String findAll(Model model) {
        List<AuthorDetails> authorDetailsList = authorDetailsService.findAll();
        model.addAttribute("authorDetailsList", authorDetailsList);
        return "author-details-list";
    }

    /**
     * Displays details of a specific AuthorDetails entry by ID.
     *
     * @param id    the ID of the AuthorDetails to retrieve
     * @param model the Spring model to pass data to the view
     * @return the name of the HTML view showing the details
     */
    @GetMapping("/find/{id}")
    public String findById(@PathVariable int id, Model model) {
        AuthorDetails details = authorDetailsService.findById(id)
                .orElseThrow(() -> new RuntimeException("AuthorDetails not found with id: " + id));
        model.addAttribute("authorDetails", details);
        return "author-details-by-id";
    }

    /**
     * Displays a form to create a new AuthorDetails entry.
     *
     * @param model the Spring model to pass a new AuthorDetails object
     * @return the name of the HTML form view
     */
    @GetMapping("/find/create-new")
    public String showCreateForm(Model model) {
        model.addAttribute("authorDetails", new AuthorDetails());
        return "author-details-form";
    }

    /**
     * Saves a new AuthorDetails entry submitted from a form.
     *
     * @param authorDetails the form-bound AuthorDetails object
     * @return redirection to the list of AuthorDetails
     */
    @PostMapping
    public String create(@ModelAttribute("authorDetails") AuthorDetails authorDetails) {
        authorDetailsService.save(authorDetails);
        return "redirect:/author-details";
    }

    /**
     * Displays a form to edit an existing AuthorDetails entry.
     *
     * @param id    the ID of the entry to edit
     * @param model the Spring model to pass the AuthorDetails object
     * @return the name of the HTML form view pre-filled with data
     */
    @GetMapping("/get-edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        AuthorDetails details = authorDetailsService.findById(id)
                .orElseThrow(() -> new RuntimeException("AuthorDetails not found with id: " + id));
        model.addAttribute("authorDetails", details);
        return "author-details-form";
    }

    /**
     * Updates an existing AuthorDetails entry with submitted form data.
     *
     * @param id            the ID of the AuthorDetails entry to update
     * @param authorDetails the updated AuthorDetails object
     * @return redirection to the list view
     */
    @PostMapping("/update/{id}")
    public String update(@PathVariable int id, @ModelAttribute("authorDetails") AuthorDetails authorDetails) {
        authorDetails.setAuthorId(id);
        authorDetailsService.save(authorDetails);
        return "redirect:/author-details";
    }

    /**
     * Displays a form for batch creation of AuthorDetails entries.
     *
     * @param model the Spring model with an example list
     * @return the name of the HTML view for batch form
     */
    @GetMapping("/batch-save")
    public String showBatchForm(Model model) {
        model.addAttribute("authorDetailsList", List.of(new AuthorDetails(), new AuthorDetails()));
        return "author-details-batch-form";
    }

    /**
     * Saves multiple AuthorDetails entries from a form.
     *
     * @param authorDetailsList the list of AuthorDetails objects
     * @return redirection to the list view
     */
    @PostMapping("/batch-save-all")
    public String saveAll(@ModelAttribute("authorDetailsList") List<AuthorDetails> authorDetailsList) {
        authorDetailsService.saveAll(authorDetailsList);
        return "redirect:/author-details";
    }

    /**
     * Deletes an AuthorDetails entry by ID.
     *
     * @param id the ID of the AuthorDetails to delete
     * @return redirection to the list view
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        authorDetailsService.deleteById(id);
        return "redirect:/author-details";
    }


    /**
     * Displays a form to select multiple AuthorDetails entries for deletion.
     *
     * @param model the Spring model with all current entries
     * @return the name of the HTML view for batch deletion
     */
    @GetMapping("/batch-show-all")
    public String showBatchDeleteForm(Model model) {
        List<AuthorDetails> authorDetailsList = authorDetailsService.findAll();
        model.addAttribute("authorDetailsList", authorDetailsList);
        return "author-details-batch-select";
    }

    /**
     * Deletes multiple AuthorDetails entries by their IDs.
     *
     * @param ids the list of IDs to delete
     * @return redirection to the list view
     */
    @PostMapping("/batch-delete-all")
    public String deleteAllAuthorDetails(@RequestParam List<Integer> ids) {
        authorDetailsService.deleteAllById(ids);
        return "redirect:/author-details";
    }
}
