package com.ddrozd.projektsilownia.Controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.ddrozd.projektsilownia.Auth.UserDetailsImpl;
import com.ddrozd.projektsilownia.Entities.Client;
import com.ddrozd.projektsilownia.Entities.Gym;
import com.ddrozd.projektsilownia.Entities.Order;
import com.ddrozd.projektsilownia.Entities.Pass;
import com.ddrozd.projektsilownia.Entities.User;
import com.ddrozd.projektsilownia.Forms.BuyPassForm;
import com.ddrozd.projektsilownia.Services.ClientService;
import com.ddrozd.projektsilownia.Services.GymService;
import com.ddrozd.projektsilownia.Services.OrderService;
import com.ddrozd.projektsilownia.Services.PassService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @Autowired
    private GymService gymService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private PassService passService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/about-us")
    public String aboutUs(Model model) {
        List<Gym> gyms = gymService.findAll();
        model.addAttribute("gyms", gyms);
        return "about";
    }

    @GetMapping("/my-pass")
    @PreAuthorize("hasRole('Client') or hasRole('Employee') or hasRole('Admin')")
    public String myPass(Model model) {
        UserDetailsImpl udi = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = udi.getUser();
        Optional<Client> client = clientService.findByUserId(user.getId());
        List<Order> orders = orderService.findActiveOrderByClient(client.get());

        model.addAttribute("orders", orders);
        return "mypass";
    }

    @GetMapping("/buy-pass")
    @PreAuthorize("hasRole('Client') or hasRole('Employee') or hasRole('Admin')")
    public String buyPass(BuyPassForm buyPassForm, Model model) {
        List<Pass> passes = passService.findAll();

        UserDetailsImpl udi = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = udi.getUser();
        Optional<Client> client = clientService.findByUserId(user.getId());
        List<Order> order = orderService.findActiveOrderByClient(client.get());

        model.addAttribute("passes", passes);
        model.addAttribute("active", (boolean)(order.size() > 0));
        return "buy";
    }

    @PostMapping("/buy-pass")
    @PreAuthorize("hasRole('Client') or hasRole('Employee') or hasRole('Admin')")
    public String buyPassPost(@Valid BuyPassForm buyPassForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "buy";
        }

        UserDetailsImpl udi = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = udi.getUser();

        Optional<Client> client = clientService.findByUserId(user.getId());
        Optional<Pass> pass = passService.findById(buyPassForm.getPassId());

        List<Order> order = orderService.findActiveOrderByClient(client.get());

        if(order.size() < 1)
            orderService.createOrderWithCurrentDate(client.get(), pass.get());

        return "redirect:/buy-pass";
    }

}
