package com.xxx.xxx.service;

import com.xxx.xxx.domain.Person;
import com.xxx.xxx.domain.PersonRequest;
import com.xxx.xxx.utils.SalaryCalculator;
import com.xxx.xxx.utils.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TimeUnit.class, SalaryCalculator.class})
@SuppressStaticInitializationFor({"com.xxx.xxx.utils.SalaryCalculator"})
public class PersonServiceTest {
    @InjectMocks
    private PersonService personService;

    @Test
    public void should_return_none_person_when_name_not_eq_James() {
        // given
        PersonRequest request = new PersonRequest("none");

        // when
        Person person = personService.find(request);

        // then
        assertThat(person.getFirstName()).isEqualTo("None");
        assertThat(person.getLastName()).isEqualTo("None");
        assertThat(person.getSalary()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void should_return_James_person_when_name_eq_James() throws Exception {
        // given
        BigDecimal salary = BigDecimal.valueOf(20);
        PersonRequest request = new PersonRequest("James");
        PowerMockito.mockStatic(SalaryCalculator.class);
        PowerMockito.when(SalaryCalculator.calculate(eq(BigDecimal.TEN)))
                .thenReturn(salary);
        PowerMockito.mockStatic(TimeUnit.class);
        PowerMockito.doNothing().when(TimeUnit.class, "sleep", 5000l);
        // when
        Person person = personService.find(request);
        // then
        assertThat(person.getFirstName()).isEqualTo("Merson");
        assertThat(person.getLastName()).isEqualTo("James");
        assertThat(person.getSalary()).isEqualTo(salary);
    }
}