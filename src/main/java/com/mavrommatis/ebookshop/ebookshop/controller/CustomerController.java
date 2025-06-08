package com.mavrommatis.ebookshop.ebookshop.controller;

import com.mavrommatis.ebookshop.ebookshop.entity.Customer;
import com.mavrommatis.ebookshop.ebookshop.entity.CustomerDetails;
import com.mavrommatis.ebookshop.ebookshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MVC Controller for managing {@link Customer} entities using Thymeleaf views.
 * Handles CRUD operations and batch processing.
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
    public String findAllCustomers(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customer-list";
    }

    /**
     * Displays details of a specific customer.
     *
     * @param id the ID of the customer
     * @param model the Spring model
     * @return the view name displaying the details
     */
    @GetMapping("/find/{id}")
    public String findById(@PathVariable int id, Model model) {
        Customer customer = customerService.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        model.addAttribute("customer", customer);
        return "customer-by-id";
    }

    /**
     * Displays a form for creating a new customer.
     *
     * @param model the Spring MVC model to pass data to the view
     * @return the view name with the form
     */
    @GetMapping("/find/create-new")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer-form";
    }

    /**
     * Handles form submission to create a new customer.
     *
     * @param customer the customer submitted from the form
     * @return redirect to the list view
     */
    @PostMapping
    public String createCustomer(@ModelAttribute("customer") Customer customer) {
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
    @GetMapping("/get-edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Customer customer = customerService.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        model.addAttribute("customer", customer);
        return "customer-form";
    }

    /**
     * Updates an existing customer.
     *
     * @param id       the ID of the customer
     * @param customer the updated customer
     * @return redirect to the list view
     */
    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable int id, @ModelAttribute("customer") Customer customer) {
        customer.setCustomerId(id);
        CustomerDetails details = customer.getCustomerDetails();
        if (details != null) {
            details.setCustomer(customer);
        }
        customerService.save(customer);
        return "redirect:/customers";
    }

    /**
     * Displays a form for batch creation of customers.
     *
     * @param model the Spring model with empty customers
     * @return the name of the HTML view for batch form
     */
    @GetMapping("/batch-create-many")
    public String showBatchSaveForm(Model model) {
        model.addAttribute("customerList", List.of(new Customer(), new Customer()));
        return "customer-batch-form";
    }

    /**
     * Saves multiple customers from form data.
     *
     * @param customers the list of customers
     * @return redirect to the list view
     */
    @PostMapping("/batch-save-all")
    public String saveAllCustomers(@ModelAttribute("customerList") List<Customer> customers) {
        for (Customer customer : customers) {
            CustomerDetails details = customer.getCustomerDetails();
            if (details != null) {
                details.setCustomer(customer);
            }
        }
        customerService.saveAll(customers);
        return "redirect:/customers";
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
        return "redirect:/customer";
    }

    /**
     * Displays a form to select multiple customers for deletion.
     *
     * @param model the Spring model with all current customers
     * @return the name of the HTML view for batch deletion
     */
    @GetMapping("/batch-show-all")
    public String showBatchDeleteForm(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customer-batch-show-delete-all";
    }

    /**
     * Deletes multiple customers by their IDs.
     *
     * @param ids the list of customer IDs to delete
     * @return redirect to the list view
     */
    @PostMapping("/batch-delete")
    public String deleteAllCustomers(@RequestParam List<Integer> ids) {
        customerService.deleteAllById(ids);
        return "redirect:/customers";
    }
}
