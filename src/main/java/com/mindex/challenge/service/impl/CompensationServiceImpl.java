package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CompensationServiceImpl implements CompensationService {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CompensationRepository compensationRepository;

    //this function would get the employee object from the comp repo
    // and assign the compensation to that employee
    @Override
    public Compensation create(Compensation compensation) {

        Employee employee = employeeService.create(compensation.getEmployee());
        compensation.getEmployee().setEmployeeId(employee.getEmployeeId());
        Date newEffectiveDate = compensation.getEffectiveDate();
        compensationRepository.insert(compensation);
        return compensation;
    }

    @Override
    public Compensation read(String eid) {
        Compensation comp = compensationRepository.findByEmployee(employeeService.read(eid));
        if(eid.equals(comp.getEmployee().getEmployeeId())){
            return comp;
        }
        return null;
    }

    @Override
    public List<Compensation> read() {
        List<Compensation> compensationList = compensationRepository.findAll();
        return compensationList;
    }
}
