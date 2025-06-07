package com.mavrommatis.ebookshop.ebookshop.controller;

import com.mavrommatis.ebookshop.ebookshop.entity.Customer;
import com.mavrommatis.ebookshop.ebookshop.entity.CustomerDetails;
import com.mavrommatis.ebookshop.ebookshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * MVC Controller for handling customer-related web pages.
 * Uses Thymeleaf (or other view technologies) to render HTML views.
 */
@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    /**
     * Constructor injection of CustomerService.
     *
     * @param customerService the service used for customer operations
     */
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Displays a list of all customers.
     *
     * @param model the Spring MVC model to pass data to the view
     * @return the view name displaying the list
     */
    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customer/list"; // View: src/main/resources/templates/customer/list.html
    }

    /**
     * Displays a form for creating a new customer.
     *
     * @param model the Spring MVC model to pass data to the view
     * @return the view name with the form
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/form"; // View: customer/form.html
    }

    /**
     * Handles form submission to create a new customer.
     *
     * @param customer the customer submitted from the form
     * @return redirect to the list view
     */
    @PostMapping
    public String saveCustomer(@ModelAttribute Customer customer) {
        CustomerDetails details = customer.getCustomerDetails();
        if (details != null) {
            details.setCustomer(customer); // Maintain bidirectional relationship
        }
        customerService.save(customer);
        return "redirect:/customers";
    }

    /**
     * Displays a form to edit an existing customer.
     *
     * @param id    the ID of the customer
     * @param model the model to populate the form
     * @return the form view with pre-filled customer data
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Customer customer = customerService.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        model.addAttribute("customer", customer);
        return "customer/form";
    }

    /**
     * Deletes a customer by ID.
     *
     * @param id the ID of the customer to delete
     * @return redirect to the list view
     */
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable int id) {
        customerService.deleteById(id);
        return "redirect:/customers";
    }
}
