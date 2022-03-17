package com.ddrozd.projektsilownia.Controllers.Admin;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.ddrozd.projektsilownia.Entities.Address;
import com.ddrozd.projektsilownia.Entities.Employee;
import com.ddrozd.projektsilownia.Entities.Gym;
import com.ddrozd.projektsilownia.Entities.User;
import com.ddrozd.projektsilownia.Exceptions.UserExistsException;
import com.ddrozd.projektsilownia.Forms.AddEmployeeToGymForm;
import com.ddrozd.projektsilownia.Forms.EmployeeEditForm;
import com.ddrozd.projektsilownia.Forms.EmployeeRegisterForm;
import com.ddrozd.projektsilownia.Services.AddressService;
import com.ddrozd.projektsilownia.Services.EmployeeService;
import com.ddrozd.projektsilownia.Services.GymService;
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
public class EmployeeController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private GymService gymService;

    @GetMapping("/admin/employees")
    public String listOfClients(Model model) {

        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);

        return "admin/employees/list";
    }

    @GetMapping("/admin/employees/{id}")
    public String employeeDetails(@PathVariable(required = false) Long id, AddEmployeeToGymForm addEmployeeToGymForm,
            Model model, RedirectAttributes redirectAttributes) {
        Optional<Employee> employee = employeeService.findById(id);

        if (!employee.isPresent()) {
            redirectAttributes.addFlashAttribute("errorStatus", "Pracownik o id " + id + " nie istnieje.");
            return "redirect:/admin/employees";
        }

        Employee _employee = employee.get();

        List<Gym> gyms = gymService.findAvailableGyms(_employee);

        model.addAttribute("gyms", gyms);
        model.addAttribute("employee", _employee);
        model.addAttribute("employeeGym", _employee.getGyms());
        model.addAttribute("orders", _employee.getOrders());
        return "admin/employees/show";
    }

    @GetMapping("/admin/employees/add")
    public String createClient(EmployeeRegisterForm employeeRegisterForm) {
        return "admin/employees/add";
    }

    @PostMapping("/admin/employees/add")
    public ModelAndView createClient(@Valid EmployeeRegisterForm employeeRegisterForm, BindingResult result,
            Model model, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView();

        if (result.hasErrors()) {
            mv.setViewName("/admin/employees/add");
            return mv;
        }

        try {
            User user = userService.registerUser(
                    employeeRegisterForm.getEmail(),
                    employeeRegisterForm.getPassword(),
                    "Employee");

            Address address = addressService.getAddressOrRegister(
                    employeeRegisterForm.getAddress(),
                    employeeRegisterForm.getCity(),
                    employeeRegisterForm.getPostcode());

            Employee employee = employeeService.registerEmployee(
                    user,
                    address,
                    employeeRegisterForm.getName(),
                    employeeRegisterForm.getSurname(),
                    employeeRegisterForm.getPhone(),
                    employeeRegisterForm.getPosition());

            model.addAttribute("employee", employee);
        } catch (UserExistsException ex) {
            mv.setViewName("/admin/employees/add");
            return mv;
        }

        redirectAttributes.addFlashAttribute("successStatus", "Nowy pracownik dodany!");
        mv.setViewName("redirect:/admin/employees");
        return mv;
    }

    @GetMapping("/admin/employees/{id}/edit")
    public String editEmployee(@PathVariable(required = false) Long id, EmployeeEditForm employeeEditForm, Model model,
            RedirectAttributes redirectAttributes) {
        Optional<Employee> employee = employeeService.findById(id);

        if (!employee.isPresent()) {
            redirectAttributes.addFlashAttribute("errorStatus", "Pracownik o id " + id + " nie istnieje.");
            return "redirect:/admin/employees";
        }

        Employee _employee = employee.get();

        employeeEditForm.setName(_employee.getName());
        employeeEditForm.setSurname(_employee.getSurname());
        employeeEditForm.setPhone(_employee.getPhone());
        employeeEditForm.setAddress(_employee.getAddress().getAddress());
        employeeEditForm.setCity(_employee.getAddress().getCity());
        employeeEditForm.setPostcode(_employee.getAddress().getPostcode());
        employeeEditForm.setPosition(_employee.getPosition());

        model.addAttribute("employee", _employee);

        return "admin/employees/edit";
    }

    @PutMapping("/admin/employees/{id}/edit")
    public String saveEmployee(@PathVariable(required = false) Long id, @Valid EmployeeEditForm employeeEditForm,
            BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        Optional<Employee> employee = employeeService.findById(id);

        if (result.hasErrors()) {
            model.addAttribute("employee", employee.get());
            return "/admin/employees/edit";
        }

        if (!employee.isPresent()) {
            redirectAttributes.addFlashAttribute("errorStatus", "Pracownik o id " + id + " nie istnieje.");
            return "redirect:/admin/employees";
        }

        Employee _employee = employee.get();

        Address address = addressService.getAddressOrRegister(employeeEditForm.getAddress(), employeeEditForm.getCity(),
                employeeEditForm.getPostcode());
        employeeService.updateEmployee(_employee, address, employeeEditForm.getName(), employeeEditForm.getSurname(),
                employeeEditForm.getPhone(), employeeEditForm.getPosition());

        redirectAttributes.addFlashAttribute("successStatus", "Pracownik o id " + id + " został zapisany.");
        return "redirect:/admin/employees";
    }

    @DeleteMapping("/admin/employees/{id}/delete")
    public String deleteEmployee(@PathVariable(required = false) Long id, Model model,
            RedirectAttributes redirectAttributes) {
        Optional<Employee> employee = employeeService.findById(id);

        if (!employee.isPresent()) {
            redirectAttributes.addFlashAttribute("errorStatus", "Pracownik o id " + id + " nie istnieje.");
            return "redirect:/admin/employees";
        }

        Employee _employee = employee.get();
        Address _address = _employee.getAddress();

        employeeService.delete(_employee);
        addressService.deleteIfNotUsed(_address);

        redirectAttributes.addFlashAttribute("successStatus", "Pracownik o id " + id + " został usunięty.");
        return "redirect:/admin/employees";
    }

    @PostMapping("/admin/employees/{id}/addGym")
    public String addEmployeeGym(@PathVariable(required = false) Long id,
            @Valid AddEmployeeToGymForm addEmployeeToGymForm, Model model, RedirectAttributes redirectAttributes) {
        Optional<Employee> employee = employeeService.findById(id);

        if (!employee.isPresent()) {
            redirectAttributes.addFlashAttribute("errorStatus", "Pracownik o id " + id + " nie istnieje.");
            return "redirect:/admin/employees";
        }

        Employee _employee = employee.get();
        Optional<Gym> gym = gymService.findById(addEmployeeToGymForm.getGymId());

        if (!gym.isPresent()) {
            redirectAttributes.addFlashAttribute("errorStatus",
                    "Siłownia o id " + addEmployeeToGymForm.getGymId() + " nie istnieje.");
            return "redirect:/admin/employees/" + _employee.getId();
        }

        Gym _gym = gym.get();
        boolean added = gymService.addEmployeeIfNotAlreadyIn(_gym, _employee);

        if (added) {
            redirectAttributes.addFlashAttribute("successStatus",
                    "Pracownik o id " + id + " został dodany do siłowni.");
        } else {
            redirectAttributes.addFlashAttribute("errorStatus",
                    "Pracownik o id " + id + " pracuje już w siłowni " + addEmployeeToGymForm.getGymId() + ".");
        }

        return "redirect:/admin/employees/" + _employee.getId();
    }

    @DeleteMapping("/admin/employees/{id}/deleteGym/{gymId}")
    public String deleteEmployeeGym(@PathVariable(required = false) Long id, @PathVariable(required = false) Long gymId,
            Model model, RedirectAttributes redirectAttributes) {
        Optional<Employee> employee = employeeService.findById(id);

        if (!employee.isPresent()) {
            redirectAttributes.addFlashAttribute("errorStatus", "Pracownik o id " + id + " nie istnieje.");
            return "redirect:/admin/employees";
        }

        Employee _employee = employee.get();
        Optional<Gym> gym = gymService.findById(gymId);

        if (!gym.isPresent()) {
            redirectAttributes.addFlashAttribute("errorStatus", "Siłownia o id " + gymId + " nie istnieje.");
            return "redirect:/admin/employees/" + _employee.getId();
        }

        Gym _gym = gym.get();
        gymService.removeEmployee(_gym, _employee);

        redirectAttributes.addFlashAttribute("successStatus",
                "Pomyślnie usunięto zatrudnienie w siłowni o id " + gymId);
        return "redirect:/admin/employees/" + _employee.getId();
    }

}
