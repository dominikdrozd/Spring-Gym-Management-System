package com.ddrozd.projektsilownia.Controllers.Admin;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.ddrozd.projektsilownia.Entities.Address;
import com.ddrozd.projektsilownia.Entities.Client;
import com.ddrozd.projektsilownia.Entities.User;
import com.ddrozd.projektsilownia.Exceptions.UserExistsException;
import com.ddrozd.projektsilownia.Forms.ClientEditForm;
import com.ddrozd.projektsilownia.Forms.ClientRegisterForm;
import com.ddrozd.projektsilownia.Services.AddressService;
import com.ddrozd.projektsilownia.Services.ClientService;
import com.ddrozd.projektsilownia.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@PreAuthorize("hasRole('Admin') or hasRole('Employee')")
public class ClientController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AddressService addressService;

    @GetMapping("/admin/clients")
    public String listOfClients(Model model) {

        List<Client> clients = clientService.findAll();
        model.addAttribute("clients", clients);

        return "admin/clients/list";
    }

    @GetMapping("/admin/clients/{id}")
    public String clientDetails(@PathVariable(required = false) Long id, Model model,
            RedirectAttributes redirectAttributes) {
        Optional<Client> client = clientService.findById(id);

        if (!client.isPresent()) {
            redirectAttributes.addFlashAttribute("errorStatus", "Klient o id " + id + " nie istnieje.");
            return "redirect:/admin/clients";
        }
        model.addAttribute("client", client.get());
        model.addAttribute("orders", client.get().getOrders());
        return "admin/clients/show";
    }

    @GetMapping("/admin/clients/{id}/edit")
    public String editClient(@PathVariable(required = false) Long id, ClientEditForm clientEditForm, Model model,
            RedirectAttributes redirectAttributes) {
        Optional<Client> client = clientService.findById(id);

        if (!client.isPresent()) {
            redirectAttributes.addFlashAttribute("errorStatus", "Klient o id " + id + " nie istnieje.");
            return "redirect:/admin/clients";
        }

        Client _client = client.get();

        clientEditForm.setName(_client.getName());
        clientEditForm.setSurname(_client.getSurname());
        clientEditForm.setPhone(_client.getPhone());
        clientEditForm.setAddress(_client.getAddress().getAddress());
        clientEditForm.setCity(_client.getAddress().getCity());
        clientEditForm.setPostcode(_client.getAddress().getPostcode());

        model.addAttribute("client", _client);

        return "admin/clients/edit";
    }

    @PutMapping("/admin/clients/{id}/edit")
    public String saveClient(@PathVariable(required = false) Long id, @Valid ClientEditForm clientEditForm,
            BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        Optional<Client> client = clientService.findById(id);

        if (result.hasErrors()) {
            model.addAttribute("client", client.get());
            return "/admin/clients/edit";
        }

        if (!client.isPresent()) {
            redirectAttributes.addFlashAttribute("errorStatus", "Klient o id " + id + " nie istnieje.");
            return "redirect:/admin/clients";
        }

        Client _client = client.get();

        Address address = addressService.getAddressOrRegister(clientEditForm.getAddress(), clientEditForm.getCity(),
                clientEditForm.getPostcode());
        clientService.updateClient(_client, address, clientEditForm.getName(), clientEditForm.getSurname(),
                clientEditForm.getPhone());

        redirectAttributes.addFlashAttribute("successStatus", "Klient o id " + id + " został zapisany.");
        return "redirect:/admin/clients";
    }

    @DeleteMapping("/admin/clients/{id}/delete")
    public String deleteClient(@PathVariable(required = false) Long id, Model model,
            RedirectAttributes redirectAttributes) {
        Optional<Client> client = clientService.findById(id);

        if (!client.isPresent()) {
            redirectAttributes.addFlashAttribute("errorStatus", "Klient o id " + id + " nie istnieje.");
            return "redirect:/admin/clients";
        }

        Client _client = client.get();
        Address _address = _client.getAddress();

        clientService.delete(_client);
        addressService.deleteIfNotUsed(_address);

        redirectAttributes.addFlashAttribute("successStatus", "Klient o id " + id + " został usunięty.");
        return "redirect:/admin/clients";
    }

    @GetMapping("/admin/clients/add")
    public String createClient(ClientRegisterForm userRegisterForm) {
        return "admin/clients/add";
    }

    @PostMapping("/admin/clients/add")
    public ModelAndView createClient(@Valid ClientRegisterForm userRegisterForm, BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView();

        if (result.hasErrors()) {
            mv.setViewName("/admin/clients/add");
            return mv;
        }

        try {
            User user = userService.registerUser(
                    userRegisterForm.getEmail(),
                    userRegisterForm.getPassword(),
                    "Client");

            Address address = addressService.getAddressOrRegister(
                    userRegisterForm.getAddress(),
                    userRegisterForm.getCity(),
                    userRegisterForm.getPostcode());

            Client client = clientService.registerClient(
                    user,
                    address,
                    userRegisterForm.getName(),
                    userRegisterForm.getSurname(),
                    userRegisterForm.getPhone());

            model.addAttribute("client", client);
        } catch (UserExistsException ex) {
            mv.setViewName("admin/clients/add");
            return mv;
        }

        redirectAttributes.addFlashAttribute("successStatus", "Nowy klient dodany!");
        mv.setViewName("redirect:/admin/clients");
        return mv;
    }

}
