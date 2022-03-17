package com.ddrozd.projektsilownia.Services;

import java.util.List;
import java.util.Optional;

import com.ddrozd.projektsilownia.Entities.Address;
import com.ddrozd.projektsilownia.Entities.Employee;
import com.ddrozd.projektsilownia.Entities.User;
import com.ddrozd.projektsilownia.Repositories.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private GymService gymService;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }    

    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee registerEmployee(User user, Address address, String name, String surname, String phone, String position) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setSurname(surname);
        employee.setPhone(phone);
        employee.setPosition(position);

        employee.setUser(user);
        employee.setAddress(address);

        employeeRepository.save(employee);

        return employee;
    }

    public int countWhereAddressId(long addressId) {
        return employeeRepository.countByAddressId(addressId);
    }

    public Employee updateEmployee(Employee employee, Address address, String name, String surname, String phone, String position) {
        employee.setAddress(address);
        employee.setName(name);
        employee.setSurname(surname);
        employee.setPhone(phone);
        employee.setPosition(position);

        save(employee);

        return employee;
    }

    public void delete(Employee employee) {
        gymService.deleteEmployeeFromGyms(employee);
        employeeRepository.delete(employee);
    }

    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    public Optional<Employee> findByUserId(Long id) {
        return employeeRepository.findByUserId(id);
    }
    
}
