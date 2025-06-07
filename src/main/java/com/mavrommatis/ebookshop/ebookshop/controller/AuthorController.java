package com.mavrommatis.ebookshop.ebookshop.controller;

import com.mavrommatis.ebookshop.ebookshop.entity.Author;
import com.mavrommatis.ebookshop.ebookshop.entity.AuthorDetails;
import com.mavrommatis.ebookshop.ebookshop.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Web controller for managing {@link Author} entities via Thymeleaf views.
 * Mirrors the structure of the REST controller, adapted for HTML pages.
 */
@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    /**
     * Constructor-based dependency injection for AuthorService.
     *
     * @param authorService the service layer for author-related operations
     */
    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Retrieves all authors and shows them in a list view.
     *
     * @param model the Spring model
     * @return the name of the HTML view
     */
    @GetMapping
    public String findAll(Model model) {
        List<Author> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        return "author-list";
    }

    /**
     * Retrieves an author by ID and shows the detail view.
     *
     * @param id the ID of the author
     * @param model the Spring model
     * @return the name of the HTML view
     */
    @GetMapping("/find/{id}")
    public String findById(@PathVariable int id, Model model) {
        Author author = authorService.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        model.addAttribute("author", author);
        return "author-details";
    }

    /**
     * Shows the form to create a new author.
     *
     * @param model the Spring model
     * @return the name of the HTML view
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("author", new Author());
        return "author-form";
    }

    /**
     * Creates a new author from form data.
     *
     * @param author the author object
     * @return redirection to the author list
     */
    @PostMapping
    public String createAuthor(@ModelAttribute("author") Author author) {
        AuthorDetails details = author.getAuthorDetails();
        if (details != null) {
            details.setAuthor(author); // Maintain the bidirectional relationship
        }
        authorService.save(author);
        return "redirect:/authors";
    }

    /**
     * Shows the form to update an existing author.
     *
     * @param id the ID of the author
     * @param model the Spring model
     * @return the name of the HTML view
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable int id, Model model) {
        Author author = authorService.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
        model.addAttribute("author", author);
        return "author-form";
    }

    /**
     * Updates an existing author from form data.
     *
     * @param id the ID of the author
     * @param author the updated author object
     * @return redirection to the author list
     */
    @PostMapping("/update/{id}")
    public String updateAuthor(@PathVariable int id, @ModelAttribute("author") Author author) {
        author.setAuthorId(id);
        AuthorDetails details = author.getAuthorDetails();
        if (details != null) {
            details.setAuthor(author);
        }
        authorService.save(author);
        return "redirect:/authors";
    }

    /**
     * Deletes an author by ID.
     *
     * @param id the ID of the author
     * @return redirection to the author list
     */
    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable int id) {
        authorService.deleteById(id);
        return "redirect:/authors";
    }

    /**
     * Shows a form to add multiple authors.
     *
     * @param model the Spring model
     * @return the name of the HTML view
     */
    @GetMapping("/batch-save")
    public String showBatchSaveForm(Model model) {
        model.addAttribute("authorList", List.of(new Author(), new Author())); // Example with 2 empty rows
        return "author-batch-form";
    }

    /**
     * Saves multiple authors from form data.
     *
     * @param authors the list of authors
     * @return redirection to the author list
     */
    @PostMapping("/batch-save")
    public String saveAllAuthors(@ModelAttribute("authorList") List<Author> authors) {
        for (Author author : authors) {
            AuthorDetails details = author.getAuthorDetails();
            if (details != null) {
                details.setAuthor(author);
            }
        }
        authorService.saveAll(authors);
        return "redirect:/authors";
    }

    /**
     * Shows a form to select multiple authors for deletion.
     *
     * @param model the Spring model
     * @return the name of the HTML view
     */
    @GetMapping("/batch-delete")
    public String showBatchDeleteForm(Model model) {
        List<Author> authors = authorService.findAll();
        model.addAttribute("authors", authors);
        return "author-batch-delete";
    }

    /**
     * Deletes multiple authors by their IDs.
     *
     * @param ids the list of IDs
     * @return redirection to the author list
     */
    @PostMapping("/batch-delete")
    public String deleteAllAuthors(@RequestParam List<Integer> ids) {
        authorService.deleteAllById(ids);
        return "redirect:/authors";
    }
}
