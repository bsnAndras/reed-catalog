package com.bsnandras.reedcatalog.controllers.server_side;

import com.bsnandras.reedcatalog.models.Customer;
import com.bsnandras.reedcatalog.models.Order;
import com.bsnandras.reedcatalog.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    @GetMapping("/customer")
    public String getOrders(Model model, @RequestParam(name = "id") Long customerId) {
        List<Order> orderList = customerService.showOrderHistory(); //TODO: modify listing to list all orders of current customer only
        String customerName = customerService.getCustomer(customerId).getName();

        model.addAttribute("orderList", orderList);
        model.addAttribute("customer", customerName);

        return "customer";
    }
}
