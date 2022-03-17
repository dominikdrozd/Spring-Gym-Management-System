package com.ddrozd.projektsilownia.Controllers.Admin;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.ddrozd.projektsilownia.Entities.Address;
import com.ddrozd.projektsilownia.Entities.Gym;
import com.ddrozd.projektsilownia.Forms.GymRegisterForm;
import com.ddrozd.projektsilownia.Services.AddressService;
import com.ddrozd.projektsilownia.Services.GymService;

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
public class GymController {

    @Autowired
    private GymService gymService;

    @Autowired
    private AddressService addressService;

    @GetMapping("/admin/gyms")
    public String listOfGyms(Model model) {

        List<Gym> gyms = gymService.findAll();
        model.addAttribute("gyms", gyms);

        return "admin/gyms/list";
    }

    @GetMapping("/admin/gyms/{id}")
    public String gymDetails(@PathVariable(required = false) long id, Model model,
            RedirectAttributes redirectAttributes) {
        Optional<Gym> gym = gymService.findById(id);

        if (!gym.isPresent()) {
            redirectAttributes.addFlashAttribute("errorStatus", "Siłownia o id " + id + " nie istnieje.");
            return "redirect:/admin/gyms";
        }
        model.addAttribute("gym", gym.get());
        model.addAttribute("gymEmployees", gym.get().getEmployees());
        return "admin/gyms/show";
    }

    @GetMapping("/admin/gyms/add")
    public String createGym(GymRegisterForm gymRegisterForm) {
        return "admin/gyms/add";
    }

    @PostMapping("/admin/gyms/add")
    public ModelAndView createGym(@Valid GymRegisterForm gymRegisterForm, BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView();

        if (result.hasErrors()) {
            mv.setViewName("/admin/gyms/add");
            return mv;
        }

        try {
            Address address = addressService.getAddressOrRegister(
                    gymRegisterForm.getAddress(),
                    gymRegisterForm.getCity(),
                    gymRegisterForm.getPostcode()
            );

            Gym gym = gymService.registerGym(address, gymRegisterForm.getName(), gymRegisterForm.getPhone());

            model.addAttribute("gym", gym);
        } catch (Exception ex) {
            mv.setViewName("/admin/gyms/add");
            return mv;
        }

        redirectAttributes.addFlashAttribute("successStatus", "Nowa siłownia dodana!");
        mv.setViewName("redirect:/admin/gyms");
        return mv;
    }

    @GetMapping("/admin/gyms/{id}/edit")
    public String editGym(@PathVariable(required = false) Long id, GymRegisterForm gymRegisterForm, Model model,
            RedirectAttributes redirectAttributes) {
        Optional<Gym> gym = gymService.findById(id);

        if (!gym.isPresent()) {
            redirectAttributes.addFlashAttribute("errorStatus", "Siłownia o id " + id + " nie istnieje.");
            return "redirect:/admin/gyms";
        }

        Gym _gym = gym.get();

        gymRegisterForm.setName(_gym.getName());
        gymRegisterForm.setPhone(_gym.getPhone());
        gymRegisterForm.setAddress(_gym.getAddress().getAddress());
        gymRegisterForm.setCity(_gym.getAddress().getCity());
        gymRegisterForm.setPostcode(_gym.getAddress().getPostcode());

        model.addAttribute("gym", _gym);

        return "admin/gyms/edit";
    }

    @PutMapping("/admin/gyms/{id}/edit")
    public String saveGym(@PathVariable(required = false) Long id, @Valid GymRegisterForm gymRegisteForm,
            BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        Optional<Gym> gym = gymService.findById(id);

        if (result.hasErrors()) {
            model.addAttribute("gym", gym.get());
            return "/admin/gyms/edit";
        }

        if (!gym.isPresent()) {
            redirectAttributes.addFlashAttribute("errorStatus", "Pracownik o id " + id + " nie istnieje.");
            return "redirect:/admin/gyms";
        }

        Gym _gym = gym.get();

        Address address = addressService.getAddressOrRegister(gymRegisteForm.getAddress(), gymRegisteForm.getCity(),
                gymRegisteForm.getPostcode());
        gymService.updateGym(_gym, address, gymRegisteForm.getName(), gymRegisteForm.getPhone());

        redirectAttributes.addFlashAttribute("successStatus", "Pracownik o id " + id + " został zapisany.");
        return "redirect:/admin/gyms";
    }

    @DeleteMapping("/admin/gyms/{id}/delete")
    public String deleteGym(@PathVariable(required = false) Long id, Model model,
            RedirectAttributes redirectAttributes) {
        Optional<Gym> gym = gymService.findById(id);

        if (!gym.isPresent()) {
            redirectAttributes.addFlashAttribute("errorStatus", "Siłownia o id " + id + " nie istnieje.");
            return "redirect:/admin/gyms";
        }

        Gym _gym = gym.get();
        Address _address = _gym.getAddress();

        gymService.delete(_gym);
        addressService.deleteIfNotUsed(_address);

        redirectAttributes.addFlashAttribute("successStatus", "Siłownia o id " + id + " została usunięta.");
        return "redirect:/admin/gyms";
    }

}
