package com.ddrozd.projektsilownia.Controllers.Admin;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.ddrozd.projektsilownia.Auth.UserDetailsImpl;
import com.ddrozd.projektsilownia.Entities.Client;
import com.ddrozd.projektsilownia.Entities.Employee;
import com.ddrozd.projektsilownia.Entities.Order;
import com.ddrozd.projektsilownia.Entities.Pass;
import com.ddrozd.projektsilownia.Entities.User;
import com.ddrozd.projektsilownia.Forms.AddOrderForm;
import com.ddrozd.projektsilownia.Services.ClientService;
import com.ddrozd.projektsilownia.Services.EmployeeService;
import com.ddrozd.projektsilownia.Services.OrderService;
import com.ddrozd.projektsilownia.Services.PassService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@PreAuthorize("hasRole('Admin') or hasRole('Employee')")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PassService passService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/admin/orders")
    public String listOfOrders(Model model) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);

        return "admin/orders/list";
    }

    @GetMapping("/admin/orders/{id}")
    public String orderDetails(@PathVariable(required = false) long id, Model model,
            RedirectAttributes redirectAttributes) {

        Optional<Order> order = orderService.findById(id);

        if (!order.isPresent()) {
            redirectAttributes.addFlashAttribute("errorStatus", "Zamówienie o id " + id + " nie istnieje.");
            return "redirect:/admin/orders";
        }

        model.addAttribute("order", order.get());
        return "admin/orders/show";
    }

    @DeleteMapping("/admin/orders/{id}/delete")
    public String deleteOrder(@PathVariable(required = false) Long id, Model model,
            RedirectAttributes redirectAttributes) {
        Optional<Order> order = orderService.findById(id);

        if (!order.isPresent()) {
            redirectAttributes.addFlashAttribute("errorStatus", "Zamówienie o id " + id + " nie istnieje.");
            return "redirect:/admin/orders";
        }

        Order _order = order.get();

        orderService.delete(_order);

        redirectAttributes.addFlashAttribute("successStatus", "Zamówienie o id " + id + " zostało usunięte.");
        return "redirect:/admin/orders";
    }

    @GetMapping("/admin/orders/add")
    public String createOrder(AddOrderForm addOrderForm, Model model) {
        List<Pass> passes = passService.findAll();
        List<Client> clients = clientService.findAll();

        model.addAttribute("passes", passes);
        model.addAttribute("clients", clients);
        return "admin/orders/add";
    }

    @PostMapping("/admin/orders/add")
    public ModelAndView createOrder(@Valid AddOrderForm addOrderForm, BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {

        ModelAndView mv = new ModelAndView();

        if (result.hasErrors()) {
            mv.setViewName("/admin/orders/add");
            return mv;
        }

        Client client = clientService.findById(addOrderForm.getClientId()).get();
        Pass pass = passService.findById(addOrderForm.getPassId()).get();

        UserDetailsImpl udi = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = udi.getUser();
        Optional<Employee> employee = employeeService.findByUserId(user.getId());

        if(employee.isPresent()) 
            orderService.createOrderWithCurrentDate(client, employee.get(), pass);
        else
            orderService.createOrderWithCurrentDate(client, pass);

        redirectAttributes.addFlashAttribute("successStatus", "Nowe zamówienie dodane!");
        return mv;
    }

}
