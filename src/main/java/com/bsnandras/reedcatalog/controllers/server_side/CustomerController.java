package com.bsnandras.reedcatalog.controllers.server_side;

import com.bsnandras.reedcatalog.models.Customer;
import com.bsnandras.reedcatalog.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;
    @GetMapping("/profile")
    public String renderCustomerPage(Model model, @RequestParam(name = "id") Long customerId) {

        model.addAttribute("customer", customerService.getCustomerPageData(customerId));

        return "customer";
    }

    @GetMapping("/customer-list")
    public String getAllCustomers(Model model) {
        List<Customer> customerList = customerService.showAllCustomers();

        model.addAttribute("customerList", customerList);

        return "customer-list";
    }
}
