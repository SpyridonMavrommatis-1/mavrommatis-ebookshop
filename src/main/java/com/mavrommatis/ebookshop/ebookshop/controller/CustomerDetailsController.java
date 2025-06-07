package com.mavrommatis.ebookshop.ebookshop.controller;

import com.mavrommatis.ebookshop.ebookshop.entity.CustomerDetails;
import com.mavrommatis.ebookshop.ebookshop.service.CustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * MVC Controller for managing CustomerDetails via HTML views.
 * Handles form interactions and page navigation.
 */
@Controller
@RequestMapping("/customerdetails")
public class CustomerDetailsController {

    private final CustomerDetailsService customerDetailsService;

    /**
     * Constructor-based injection of CustomerDetailsService.
     *
     * @param customerDetailsService the service layer for CustomerDetails
     */
    @Autowired
    public CustomerDetailsController(CustomerDetailsService customerDetailsService) {
        this.customerDetailsService = customerDetailsService;
    }

    /**
     * Displays a list of all CustomerDetails.
     *
     * @param model the model to pass data to the view
     * @return the HTML view showing all customer details
     */
    @GetMapping
    public String listCustomerDetails(Model model) {
        model.addAttribute("customerDetailsList", customerDetailsService.findAll());
        return "customer-details/list";
    }

    /**
     * Displays a form to create a new CustomerDetails entry.
     *
     * @param model the model to pass data to the view
     * @return the form view for creating a new customer
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("customerDetails", new CustomerDetails());
        return "customer-details/form";
    }

    /**
     * Saves a new CustomerDetails entry or updates an existing one.
     *
     * @param customerDetails the data submitted from the form
     * @return redirect to the list view after saving
     */
    @PostMapping("/save")
    public String saveCustomerDetails(@ModelAttribute("customerDetails") CustomerDetails customerDetails) {
        customerDetailsService.save(customerDetails);
        return "redirect:/customerdetails";
    }

    /**
     * Displays a form to edit an existing CustomerDetails entry.
     *
     * @param id    the ID of the customer to edit
     * @param model the model to pass data to the view
     * @return the form view for editing
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        CustomerDetails customerDetails = customerDetailsService.findById(id)
                .orElseThrow(() -> new RuntimeException("CustomerDetails not found with id: " + id));
        model.addAttribute("customerDetails", customerDetails);
        return "customer-details/form";
    }

    /**
     * Deletes a CustomerDetails entry by ID.
     *
     * @param id the ID of the customer to delete
     * @return redirect to the list view after deletion
     */
    @GetMapping("/delete/{id}")
    public String deleteCustomerDetails(@PathVariable int id) {
        customerDetailsService.deleteById(id);
        return "redirect:/customerdetails";
    }
}
