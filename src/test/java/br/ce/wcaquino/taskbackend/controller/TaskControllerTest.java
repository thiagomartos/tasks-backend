/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ce.wcaquino.taskbackend.controller;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;
import java.time.LocalDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author marto
 */
public class TaskControllerTest {

    @Mock
    private TaskRepo taskRepo;

    @InjectMocks
    private TaskController controller;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void naoDeveSalvarSemDescricao() {
        Task todo = new Task();
        todo.setDueDate(LocalDate.now());

        try {
            controller.save(todo);
            Assert.fail();
        } catch (ValidationException ex) {
            Assert.assertEquals("Fill the task description", ex.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarSemData() {
        Task todo = new Task();
        todo.setTask("descricao");

        try {
            controller.save(todo);
            Assert.fail();
        } catch (ValidationException ex) {
            Assert.assertEquals("Fill the due date", ex.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarComDataPassada() {
        Task todo = new Task();
        todo.setDueDate(LocalDate.of(2010, 01, 01));
        todo.setTask("descricao");

        try {
            controller.save(todo);
            Assert.fail();
        } catch (ValidationException ex) {
            Assert.assertEquals("Due date must not be in past", ex.getMessage());
        }
    }

    @Test
    public void deveSalvarComSucesso() throws ValidationException {
        Task todo = new Task();
        todo.setDueDate(LocalDate.now());
        todo.setTask("descricao");

        controller.save(todo);
        Mockito.verify(taskRepo).save(todo);
    }

}
