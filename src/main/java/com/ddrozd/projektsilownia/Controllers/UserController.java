package com.ddrozd.projektsilownia.Controllers;

import javax.validation.Valid;

import com.ddrozd.projektsilownia.Entities.Address;
import com.ddrozd.projektsilownia.Entities.Client;
import com.ddrozd.projektsilownia.Entities.User;
import com.ddrozd.projektsilownia.Exceptions.UserExistsException;
import com.ddrozd.projektsilownia.Forms.ClientRegisterForm;
import com.ddrozd.projektsilownia.Services.AddressService;
import com.ddrozd.projektsilownia.Services.ClientService;
import com.ddrozd.projektsilownia.Services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AddressService addressService;

    @GetMapping("/register")
    @PreAuthorize("hasRole('ANONYMOUS')")
    public String registerForm(ClientRegisterForm clientRegisterForm) {
        return "users/register";
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ANONYMOUS')")
    public ModelAndView processRegister(@Valid ClientRegisterForm clientRegisterForm, BindingResult result, Model model)
            throws Exception {
        ModelAndView mv = new ModelAndView();

        if (result.hasErrors()) {
            mv.setViewName("users/register");
            return mv;
        }

        try {
            User user = userService.registerUser(
                    clientRegisterForm.getEmail(),
                    clientRegisterForm.getPassword(),
                    "Client");

            Address address = addressService.getAddressOrRegister(
                    clientRegisterForm.getAddress(),
                    clientRegisterForm.getCity(),
                    clientRegisterForm.getPostcode());

            Client client = clientService.registerClient(
                    user,
                    address,
                    clientRegisterForm.getName(),
                    clientRegisterForm.getSurname(),
                    clientRegisterForm.getPhone());

            model.addAttribute("client", client);
        } catch (UserExistsException ex) {
            mv.setViewName("users/register");
            return mv;
        }

        mv.setViewName("users/register_success");
        return mv;
    }

}
