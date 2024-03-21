package com.example.StudentManagementSystem.controller;

import com.example.StudentManagementSystem.entity.Student;
import com.example.StudentManagementSystem.service.framework.StudentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/student")
    public String listOfStudent(Model model)
    {
        model.addAttribute("student" , studentService.getAllStudents());
        return "student";
    }

    @GetMapping("/student/new")
    public String createNewStudent(Model model)
    {
        Student student = new Student();
        model.addAttribute("student" , student);
        return "create_student";
    }

    @PostMapping("/student")
    public String addStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create_student";
        }

        studentService.addNewStudent(student);
        return "redirect:/student";
    }

    @GetMapping("/student/edit/{id}")
    public String editStudentForm(@PathVariable Long id , Model model)
    {
        model.addAttribute("student" , studentService.getStudentById(id));
        return "edit_student";
    }

    @PostMapping("/student/{id}")
    public String updateStudent(@PathVariable Long id , @Valid @ModelAttribute("student") Student student ,
                                BindingResult bindingResult , Model model)
    {
        if (bindingResult.hasErrors()) {
            model.addAttribute("student", student);
            return "edit_student";
        }

        Student selectedStudent = studentService.getStudentById(id);
        selectedStudent.setFirstName(student.getFirstName());
        selectedStudent.setLastName(student.getLastName());
        selectedStudent.setEmail(student.getEmail());

        studentService.updateStudent(selectedStudent);

        return "redirect:/student";
    }

    @GetMapping("/student/{id}")
    public String deleteStudentById(@PathVariable Long id)
    {
        studentService.deleteStudentById(id);
        return "redirect:/student";
    }
}
