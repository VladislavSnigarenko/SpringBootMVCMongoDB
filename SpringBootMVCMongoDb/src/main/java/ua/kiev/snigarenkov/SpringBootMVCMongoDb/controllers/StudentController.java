package ua.kiev.snigarenkov.SpringBootMVCMongoDb.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.kiev.snigarenkov.SpringBootMVCMongoDb.models.Student;
import ua.kiev.snigarenkov.SpringBootMVCMongoDb.services.StudentService;

@Controller
public class StudentController {

    static final int ITEMS_PER_PAGE = 6; // так же лучше использовать spring @Value в такиз случаях

	@Autowired
	StudentService studentService;	
	
	@RequestMapping
	public String index(){
		return "index";
	}
	
	@RequestMapping("about")
	public String about(){
		return "about";
	}
	
	@RequestMapping("list")
	public String showAll(@RequestParam(required = false, defaultValue = "1") Integer page, Model model){
		PageRequest pageRequest = PageRequest.of(page - 1, ITEMS_PER_PAGE, Sort.Direction.ASC, "firstName"); 
		List<Student> students = studentService.findAll(pageRequest);
		model.addAttribute("students", students);
	    Long countStudents = studentService.count();
        model.addAttribute("totalPages", getPageCount(countStudents));

	    model.addAttribute("onPageItems", students.size());
	    model.addAttribute("totalItems", countStudents);
	    model.addAttribute("startPage", 1);
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", getPageCount(countStudents)); // дубликат с линии 42
		
		return "list-student";
	}
	
	@RequestMapping("add")
	public String add(Model model){
		model.addAttribute("student", new Student());
		return "add-student";
	}
	
	@RequestMapping("search")
	public String search(Model model, String keyword) {
		if (keyword.isEmpty()) {
			return "redirect:/list"; // "redirect:/list" повторяется, можно вынести в переменную
		} else {
			model.addAttribute("students", studentService.findByKeyword(keyword));
		}
		return "list-student";
	}

	@PostMapping("save")
	public String greetingSubmit(Student student, Model model) {
		studentService.save(student);
		return "redirect:/list";
	}

	@RequestMapping("update/{id}")
	String update(@PathVariable String id, Model model) {
		if(studentService.existsById(id)) {
			model.addAttribute("student", studentService.findById(id));
			return "add-student";
		}else {
			return "redirect:/list";
		}
	}

	@RequestMapping("delete/{id}")
	String delete(@PathVariable String id) {
		if(studentService.existsById(id)) {
			studentService.deleteById(id);
		}
		return "redirect:/list";
	}

    private long getPageCount(long totalCount) {
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }
	
}
