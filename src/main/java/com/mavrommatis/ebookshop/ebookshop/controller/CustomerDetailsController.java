package com.mavrommatis.ebookshop.ebookshop.controller;

import com.mavrommatis.ebookshop.ebookshop.entity.CustomerDetailsEntity;
import com.mavrommatis.ebookshop.ebookshop.service.CustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MVC Controller for managing {@link CustomerDetailsEntity} using HTML views.
 * Handles CRUD and batch operations with Thymeleaf integration.
 */
@Controller
@RequestMapping("/customer-details")
public class CustomerDetailsController {

    private final CustomerDetailsService customerDetailsService;

    /**
     * Constructor-based dependency injection for CustomerDetailsService.
     *
     * @param customerDetailsService the service to manage CustomerDetails
     */
    @Autowired
    public CustomerDetailsController(CustomerDetailsService customerDetailsService) {
        this.customerDetailsService = customerDetailsService;
    }

    /**
     * Displays a list of all CustomerDetails entries.
     *
     * @param model the Spring model to pass data to the view
     * @return the name of the HTML view for listing CustomerDetails
     */
    @GetMapping
    public String findAll(Model model) {
        List<CustomerDetailsEntity> customerDetailsList = customerDetailsService.findAll();
        model.addAttribute("customerDetailsList", customerDetailsList);
        return "customer-details-list";
    }

    /**
     * Displays details of a specific CustomerDetails entry by ID.
     *
     * @param id    the ID of the CustomerDetails to retrieve
     * @param model the Spring model to pass data to the view
     * @return the name of the HTML view showing the details
     */
    @GetMapping("/find/{id}")
    public String findById(@PathVariable int id, Model model) {
        CustomerDetailsEntity details = customerDetailsService.findById(id)
                .orElseThrow(() -> new RuntimeException("CustomerDetails not found with id: " + id));
        model.addAttribute("customerDetails", details);
        return "customer-details-by-id";
    }

    /**
     * Displays a form to create a new CustomerDetails entry.
     *
     * @param model the Spring model to pass a new CustomerDetails object
     * @return the name of the HTML form view
     */
    @GetMapping("/find/create-new")
    public String showCreateForm(Model model) {
        model.addAttribute("customerDetails", new CustomerDetailsEntity());
        return "customer-details-form";
    }

    /**
     * Saves a new CustomerDetails entry submitted from a form.
     *
     * @param customerDetails the form-bound CustomerDetails object
     * @return redirection to the list of CustomerDetails
     */
    @PostMapping
    public String create(@ModelAttribute("customerDetails") CustomerDetailsEntity customerDetails) {
        customerDetailsService.save(customerDetails);
        return "redirect:/customer-details";
    }

    /**
     * Displays a form to edit an existing CustomerDetails entry.
     *
     * @param id    the ID of the entry to edit
     * @param model the Spring model to pass the CustomerDetails object
     * @return the name of the HTML form view pre-filled with data
     */
    @GetMapping("/get-edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        CustomerDetailsEntity details = customerDetailsService.findById(id)
                .orElseThrow(() -> new RuntimeException("CustomerDetails not found with id: " + id));
        model.addAttribute("customerDetails", details);
        return "customer-details-form";
    }

    /**
     * Updates an existing CustomerDetails entry with submitted form data.
     *
     * @param id              the ID of the CustomerDetails entry to update
     * @param customerDetails the updated CustomerDetails object
     * @return redirection to the list view
     */
    @PostMapping("/update/{id}")
    public String update(@PathVariable int id, @ModelAttribute("customerDetails") CustomerDetailsEntity customerDetails) {
        customerDetails.setCustomerId(id);
        customerDetailsService.save(customerDetails);
        return "redirect:/customer-details";
    }

    /**
     * Displays a form for batch creation of CustomerDetails entries.
     *
     * @param model the Spring model with an example list
     * @return the name of the HTML view for batch form
     */
    @GetMapping("/batch-save")
    public String showBatchForm(Model model) {
        model.addAttribute("customerDetailsList", List.of(new CustomerDetailsEntity(), new CustomerDetailsEntity()));
        return "customer-details-batch-form";
    }

    /**
     * Saves multiple CustomerDetails entries from a form.
     *
     * @param customerDetailsList the list of CustomerDetails objects
     * @return redirection to the list view
     */
    @PostMapping("/batch-save-all")
    public String saveAll(@ModelAttribute("customerDetailsList") List<CustomerDetailsEntity> customerDetailsList) {
        customerDetailsService.saveAll(customerDetailsList);
        return "redirect:/customer-details";
    }

    /**
     * Deletes a CustomerDetails entry by ID.
     *
     * @param id the ID of the CustomerDetails to delete
     * @return redirection to the list view
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        customerDetailsService.deleteById(id);
        return "redirect:/customer-details";
    }

    /**
     * Displays a form to select multiple CustomerDetails entries for deletion.
     *
     * @param model the Spring model with all current entries
     * @return the name of the HTML view for batch deletion
     */
    @GetMapping("/batch-show-all")
    public String showBatchDeleteForm(Model model) {
        List<CustomerDetailsEntity> customerDetailsList = customerDetailsService.findAll();
        model.addAttribute("customerDetailsList", customerDetailsList);
        return "customer-details-batch-select";
    }

    /**
     * Deletes multiple CustomerDetails entries by their IDs.
     *
     * @param ids the list of IDs to delete
     * @return redirection to the list view
     */
    @PostMapping("/batch-delete-all")
    public String deleteAllCustomerDetails(@RequestParam List<Integer> ids) {
        customerDetailsService.deleteAllById(ids);
        return "redirect:/customer-details";
    }
}
