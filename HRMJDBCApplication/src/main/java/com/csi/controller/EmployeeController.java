package com.csi.controller;

import com.csi.model.Employee;
import com.csi.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeServiceImpl;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody Employee employee) {

        log.info("###########Trying to save data from Employee: " + employee.getEmpName());
        employeeServiceImpl.signUp(employee);
        return ResponseEntity.ok("SignUp Done Successfully");
    }

    @GetMapping("/signin/{empEmailId}/{empPassword}")
    public ResponseEntity<Boolean> signIn(@PathVariable String empEmailId, @PathVariable String empPassword) {
        return ResponseEntity.ok(employeeServiceImpl.signIn(empEmailId, empPassword));
    }

    @GetMapping("/findbyid/{empId}")
    public ResponseEntity<Employee> findById(@PathVariable int empId) {
        return ResponseEntity.ok(employeeServiceImpl.findById(empId));
    }

    @GetMapping("/findbyname/{empName}")
    public ResponseEntity<List<Employee>> findByName(@PathVariable String empName) {
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().filter(emp -> emp.getEmpName().equals(empName)).toList());
    }

    @GetMapping("/findbycontactnumber/{empContactNumber}")
    public ResponseEntity<Employee> findByContactNumber(@PathVariable long empContactNumber) {
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().filter(emp -> emp.getEmpContactNumber() == empContactNumber).toList().get(0));
    }

    @GetMapping("/findbyuid/{empUID}")
    public ResponseEntity<Employee> findByUID(@PathVariable long empUID) {
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().filter(emp -> emp.getEmpUID() == empUID).toList().get(0));
    }

    @GetMapping("/findbyanyinput/{input}")
    public ResponseEntity<List<Employee>> findByAnyInput(@PathVariable String input) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().filter(emp -> emp.getEmpName().equals(input)
                || String.valueOf(emp.getEmpId()).equals(input)
                || String.valueOf(emp.getEmpContactNumber()).equals(input)
                || String.valueOf(emp.getEmpUID()).equals(input)
                || emp.getEmpEmailId().equals(input)
                || simpleDateFormat.format(emp.getEmpDOB()).equals(input)).toList());
    }

    @GetMapping("/findbydob/{empDOB}")
    public ResponseEntity<List<Employee>> findByDOB(@PathVariable String empDOB) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().filter(emp ->
                simpleDateFormat.format(emp.getEmpDOB()).equals(empDOB)).toList());
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(employeeServiceImpl.findAll());
    }

    @GetMapping("/sortbyid")
    public ResponseEntity<List<Employee>> sortById() {
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().sorted(Comparator.comparingInt(Employee::getEmpId)).toList());
    }

    @GetMapping("/sortbyname")
    public ResponseEntity<List<Employee>> sortByName() {
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().sorted(Comparator.comparing(Employee::getEmpName)).toList());

    }

    @GetMapping("/sortbydob")
    public ResponseEntity<List<Employee>> sortByDOB() {
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().sorted(Comparator.comparing(Employee::getEmpDOB)).toList());

    }

    @GetMapping("/filterbysalary/{empSalary}")
    public ResponseEntity<List<Employee>> filterBySalary(@PathVariable double empSalary) {
        return ResponseEntity.ok(employeeServiceImpl.findAll().stream().filter(emp -> emp.getEmpSalary() >= empSalary).toList());
    }

    @GetMapping("/loaneligibility/{empId}")
    public ResponseEntity<String> loanEligibility(@PathVariable int empId) {
        String msg = "";

        if (employeeServiceImpl.findById(empId) != null
                && employeeServiceImpl.findById(empId).getEmpId() == empId
                && employeeServiceImpl.findById(empId).getEmpSalary() >= 30000) {
            msg = "ELIGIBLE FOR LOAN";

        } else {
            msg = "NOT ELIGIBLE FOR LOAN";
        }

        return ResponseEntity.ok(msg);
    }

    @PutMapping("/updatedatabyid/{empId}")
    public ResponseEntity<String> updateDataById(@PathVariable int empId, @RequestBody Employee employee) {
        employeeServiceImpl.updateData(empId, employee);

        return ResponseEntity.ok("Data Updated Successfully");
    }

    @DeleteMapping("/deletebyid/{empId}")
    public ResponseEntity<String> deleteById(@PathVariable int empId) {
        employeeServiceImpl.deleteById(empId);
        return ResponseEntity.ok("Data Deleted Successfully");
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<String> deleteAll() {
        employeeServiceImpl.deleteAll();
        return ResponseEntity.ok("All Data Deleted Successfully");
    }


}
