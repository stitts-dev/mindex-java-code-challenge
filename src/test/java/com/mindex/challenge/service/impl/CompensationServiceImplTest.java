package com.mindex.challenge.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import  org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String employeeUrl;
    private String employeeIdUrl;
    private String compensationUrl;
    private String compensationIdUrl;

    @Autowired
    private CompensationService compensationService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}";
        compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";
        compensationUrl = "http://localhost:" + port + "/compensation";

    }

    @Test
    public void Test()
    {
        Employee manager = new Employee();
        manager.setFirstName("Michael");
        manager.setLastName("Richards");
        manager.setDepartment("SDET");
        manager.setPosition("Lead");

        Compensation comp = new Compensation();
        comp.setEffectiveDate(new Date());
        comp.setSalary(450000);

        Employee john = restTemplate.postForEntity(employeeUrl, manager, Employee.class).getBody();
        assertNotNull(john.getEmployeeId());

        Employee shouldBeJohn = restTemplate
                .getForEntity(employeeIdUrl, Employee.class, john.getEmployeeId()).getBody();
        assertEquals(john.getEmployeeId(), shouldBeJohn.getEmployeeId());
        comp.setEmployee(shouldBeJohn);

        Compensation johnCompensation = restTemplate.postForEntity(compensationUrl, comp, Compensation.class).getBody();
        assertEquals(johnCompensation.getSalary(), comp.getSalary());
        System.out.println(johnCompensation.getSalary());

        Compensation getJohnCompensation = restTemplate.getForEntity(compensationIdUrl,
                Compensation.class, johnCompensation.getEmployee().getEmployeeId()).getBody();

        assertEquals(getJohnCompensation.getEmployee().getEmployeeId(), johnCompensation.getEmployee().getEmployeeId());
        assertEquals(getJohnCompensation.getSalary(), comp.getSalary());
    }


}