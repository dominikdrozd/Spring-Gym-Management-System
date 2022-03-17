package com.ddrozd.projektsilownia.Controllers.Admin;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.ddrozd.projektsilownia.Entities.Pass;
import com.ddrozd.projektsilownia.Forms.PassRegisterForm;
import com.ddrozd.projektsilownia.Services.PassService;

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
public class PassController {

    @Autowired
    private PassService passService;

    @GetMapping("/admin/passes")
    public String listOfPasses(Model model) {

        List<Pass> passes = passService.findAll();
        model.addAttribute("passes", passes);

        return "admin/passes/list";
    }

    @GetMapping("/admin/passes/{id}")
    public String passDetails(@PathVariable(required = false) Long id, Model model,
            RedirectAttributes redirectAttributes) {
        Optional<Pass> pass = passService.findById(id);

        if (!pass.isPresent()) {
            redirectAttributes.addFlashAttribute("errorStatus", "Karnet o id " + id + " nie istnieje.");
            return "redirect:/admin/passes";
        }
        model.addAttribute("pass", pass.get());
        model.addAttribute("orders", pass.get().getOrders());
        return "admin/passes/show";
    }

    @GetMapping("/admin/passes/add")
    public String createPass(PassRegisterForm passRegisterForm) {
        return "admin/passes/add";
    }

    @PostMapping("/admin/passes/add")
    public ModelAndView createPass(@Valid PassRegisterForm passRegisterForm, BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView();

        if (result.hasErrors()) {
            mv.setViewName("/admin/passes/add");
            return mv;
        }

        try {
            Pass pass = passService.registerPass(passRegisterForm.getName(), passRegisterForm.getPrice(),
                    passRegisterForm.getExpiryTime());
            model.addAttribute("pass", pass);
        } catch (Exception ex) {
            mv.setViewName("/admin/passes/add");
            return mv;
        }

        redirectAttributes.addFlashAttribute("successStatus", "Nowy karnet dodany!");
        mv.setViewName("redirect:/admin/passes");
        return mv;
    }

    @GetMapping("/admin/passes/{id}/edit")
    public String editPass(@PathVariable(required = false) Long id, PassRegisterForm passRegisterForm, Model model,
            RedirectAttributes redirectAttributes) {
        Optional<Pass> pass = passService.findById(id);

        if (!pass.isPresent()) {
            redirectAttributes.addFlashAttribute("errorStatus", "Karnet o id " + id + " nie istnieje.");
            return "redirect:/admin/passes";
        }

        Pass _pass = pass.get();

        passRegisterForm.setName(_pass.getName());
        passRegisterForm.setPrice(_pass.getPrice());
        passRegisterForm.setExpiryTime(_pass.getExpiryTime());

        model.addAttribute("pass", _pass);
        return "admin/passes/edit";
    }

    @PutMapping("/admin/passes/{id}/edit")
    public String savePass(@PathVariable(required = false) Long id, @Valid PassRegisterForm passRegisterForm,
            BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        Optional<Pass> pass = passService.findById(id);

        if (result.hasErrors()) {
            model.addAttribute("pass", pass.get());
            return "/admin/passes/edit";
        }

        if (!pass.isPresent()) {
            redirectAttributes.addFlashAttribute("errorStatus", "Karnet o id " + id + " nie istnieje.");
            return "redirect:/admin/passes";
        }

        Pass _pass = pass.get();

        passService.update(_pass, passRegisterForm.getName(), passRegisterForm.getPrice(),
                passRegisterForm.getExpiryTime());

        redirectAttributes.addFlashAttribute("successStatus", "Karnet o id " + id + " został zapisany.");
        return "redirect:/admin/passes";
    }

    @DeleteMapping("/admin/passes/{id}/delete")
    public String deletePass(@PathVariable(required = false) Long id, Model model,
            RedirectAttributes redirectAttributes) {
        Optional<Pass> pass = passService.findById(id);

        if (!pass.isPresent()) {
            redirectAttributes.addFlashAttribute("errorStatus", "Siłownia o id " + id + " nie istnieje.");
            return "redirect:/admin/passes";
        }

        Pass _pass = pass.get();

        passService.delete(_pass);

        redirectAttributes.addFlashAttribute("successStatus", "Siłownia o id " + id + " została usunięta.");
        return "redirect:/admin/passes";
    }

}
