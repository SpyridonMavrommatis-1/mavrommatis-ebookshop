package com.mavrommatis.ebookshop.ebookshop.controller;

import com.mavrommatis.ebookshop.ebookshop.entity.AuthorDetails;
import com.mavrommatis.ebookshop.ebookshop.service.AuthorDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MVC Controller for managing AuthorDetails using HTML views.
 */
@Controller
@RequestMapping("/authordetails")
public class AuthorDetailsController {

    private final AuthorDetailsService authorDetailsService;

    /**
     * Constructor for dependency injection of AuthorDetailsService.
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
     * @param model the Spring model
     * @return the name of the HTML view
     */
    @GetMapping
    public String listAuthorDetails(Model model) {
        List<AuthorDetails> authorDetailsList = authorDetailsService.findAll();
        model.addAttribute("authorDetailsList", authorDetailsList);
        return "authordetails-list";
    }

    /**
     * Displays a form to create a new AuthorDetails.
     *
     * @param model the Spring model
     * @return the name of the HTML form view
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("authorDetails", new AuthorDetails());
        return "authordetails-form";
    }

    /**
     * Saves a new AuthorDetails entry.
     *
     * @param authorDetails the submitted form data
     * @return redirection to the listing page
     */
    @PostMapping
    public String saveAuthorDetails(@ModelAttribute("authorDetails") AuthorDetails authorDetails) {
        authorDetailsService.save(authorDetails);
        return "redirect:/authordetails";
    }

    /**
     * Displays a form to update an existing AuthorDetails entry.
     *
     * @param id    the ID of the entry to update
     * @param model the Spring model
     * @return the name of the HTML form view
     */
    @GetMapping("/update")
    public String showUpdateForm(@RequestParam("id") int id, Model model) {
        AuthorDetails authorDetails = authorDetailsService.findById(id)
                .orElseThrow(() -> new RuntimeException("AuthorDetails not found with id: " + id));
        model.addAttribute("authorDetails", authorDetails);
        return "authordetails-form";
    }

    /**
     * Deletes an AuthorDetails entry by ID.
     *
     * @param id the ID to delete
     * @return redirection to the listing page
     */
    @GetMapping("/delete")
    public String deleteAuthorDetails(@RequestParam("id") int id) {
        authorDetailsService.deleteById(id);
        return "redirect:/authordetails";
    }
}
