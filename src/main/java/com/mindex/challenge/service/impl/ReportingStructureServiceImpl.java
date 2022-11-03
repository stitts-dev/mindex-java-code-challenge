package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    private int numberOfReports;

    @Autowired
    private EmployeeRepository employeeRepository;

    private void getNumOfReports(Employee employee) {

        ArrayDeque<Employee> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        queue.add(employee);

        while (!queue.isEmpty()) {

            Employee manager = queue.poll();
            String manager_id = manager.getEmployeeId();

            if (!visited.contains(manager_id)) {

                visited.add(manager_id);
                List<Employee> direct_reports = manager.getDirectReports();

                if(direct_reports!=null){
                    queue.addAll(direct_reports);
                }
                numberOfReports++;
            }
        }
    }

    @Override
    public ReportingStructure read(String id) throws RuntimeException {
        numberOfReports = -1;
        Employee employee = employeeRepository.findByEmployeeId(id);
        if (employee == null) {
            throw new RuntimeException("Cannot find employeeId: " + id);
        }
        ReportingStructure rs = new ReportingStructure();
        getNumOfReports(employee);
        rs.setNumberOfReports(numberOfReports);
        rs.setEmployee(employee);
        return rs;
    }
}