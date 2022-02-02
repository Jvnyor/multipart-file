package com.Jvnyor.multipartfile.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.Jvnyor.multipartfile.model.Employee;
import com.Jvnyor.multipartfile.service.EmployeeService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class EmployeeController {

	private final EmployeeService employeeService;
	
	@GetMapping(value = "/employee")
    public String showEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employee/createEmployeeForm";
    }
	
	@RequestMapping(path = "/employee", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public String saveEmployee(@ModelAttribute Employee employee) {
	    employeeService.save(employee);
	    return "employee/success";
	}
	
	@RequestMapping(path = "/requestpart/employee", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Object> saveEmployee(@RequestPart Employee employee, @RequestPart MultipartFile document) {
	    employee.setDocument(document);
	    employeeService.save(employee);
	    return ResponseEntity.ok().build();
	}
	
	@RequestMapping(path = "/requestparam/employee", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<Object> saveEmployee(@RequestParam String name, @RequestPart MultipartFile document) {
	    Employee employee = new Employee(name, document);
	    employeeService.save(employee);
	    return ResponseEntity.ok().build();
	}
}
