package com.csi.service;

import com.csi.model.Employee;

import java.util.List;

public interface EmployeeService {

    public void signUp(Employee employee);

    public boolean signIn(String empEmailId, String empPassword);

    public Employee findById(int empId);

    public List<Employee> findAll();

    public void updateData(int empId, Employee employee);

    public void deleteById(int empId);

    public void deleteAll();
}
