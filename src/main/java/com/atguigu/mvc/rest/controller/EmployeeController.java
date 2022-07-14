package com.atguigu.mvc.rest.controller;

import com.atguigu.mvc.rest.bean.Employee;
import com.atguigu.mvc.rest.dao.EmployeeDAO;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/restful")
public class EmployeeController {

  @Autowired
  private EmployeeDAO employeeDAO;

  @GetMapping(value = "/employee")
  public String getAllEmployee(Model model) {
    Collection<Employee> employeeList = employeeDAO.getAll();
    model.addAttribute("employeeList", employeeList);
    return "employee_list";
  }

  @GetMapping(value = "/employee/{id}")
  public String getEmployeeById(Model model, @PathVariable("id") Integer id) {
    Employee employee = employeeDAO.get(id);
    model.addAttribute("employee", employee);
    return "employee_update";
  }

  @DeleteMapping(value = "/employee/{id}")
  public String deleteEmployee(@PathVariable("id") Integer id) {
    employeeDAO.delete(id);
    return "redirect:/restful/employee";
  }

  @PostMapping(value = "/employee")
  public String addEmployee(Employee employee) {
    employeeDAO.save(employee);
    return "redirect:/restful/employee";
  }

  @PutMapping(value = "/employee")
  public String updateEmployee(Employee employee) {
    employeeDAO.save(employee);
    return "redirect:/restful/employee";
  }
}
