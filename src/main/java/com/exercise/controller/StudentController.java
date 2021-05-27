package com.exercise.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.exercise.dao.ClassDAO;
import com.exercise.dao.StudentDAO;
import com.exercise.dto.ClassRequestDTO;
import com.exercise.dto.ClassResponseDTO;
import com.exercise.dto.StudentRequestDTO;
import com.exercise.dto.StudentResponseDTO;
import com.exercise.model.StudentBean;

@Controller
public class StudentController {
	@Autowired
	private StudentDAO studao;

	@Autowired
	private ClassDAO classdao;

	@RequestMapping(value = "/studentSearch", method = RequestMethod.GET)
	public ModelAndView setupStudentSearch(@RequestParam(name = "message", required = false) String message,
			ModelMap model) {
		model.addAttribute("msg", message);
		return new ModelAndView("BUD001", "stuSearch", new StudentBean());
	}

	@RequestMapping(value = "/searchStudent", method = RequestMethod.POST)
	public String studentSearch(ModelMap model, @ModelAttribute("stuSearch") StudentBean stu) {
		StudentRequestDTO dto = new StudentRequestDTO();
		dto.setStudentId(stu.getId());
		dto.setStudentName(stu.getName());
		dto.setClassName(stu.getClassName());
		List<StudentResponseDTO> list = studao.select(dto);
		if (list.size() == 0)
			model.addAttribute("msg", "Student not found!");
		else
			model.addAttribute("stulist", list);
		return "BUD001";

	}

	@RequestMapping(value = "/addStudentsetup", method = RequestMethod.GET)
	public String addStudentsetup(ModelMap model,HttpSession session) {
		ClassRequestDTO dto = new ClassRequestDTO();
		dto.setId("");
		dto.setName("");
		List<ClassResponseDTO> list = classdao.select(dto);
		session.setAttribute("classlist",list);
		model.addAttribute("stu", new StudentBean());
		return "BUD002";
	}

	@RequestMapping(value = "/addStudent", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("stu") @Validated StudentBean stuBean, BindingResult bs, ModelMap model) {
		if (bs.hasErrors()) {
			
			return "BUD002";
		}
		if (!stuBean.getDay().equals("Day") && !stuBean.getMonth().equals("Month")
				&& !stuBean.getYear().equals("Year")) {
			String month = stuBean.getMonth();
			String day = stuBean.getDay();
			StudentRequestDTO dto = new StudentRequestDTO();
			dto.setStudentId(stuBean.getId());
			dto.setStudentName(stuBean.getName());
			dto.setStatus(stuBean.getStatus());
			dto.setClassName(stuBean.getClassName());
			dto.setRegisterDate(stuBean.getYear() + "-" + month + "-" + day);

			List<StudentResponseDTO> list1 = studao.select(dto);
			String message = null;
			if (list1.size() != 0) {
				model.addAttribute("bean", stuBean);
				model.addAttribute("err", "StudentId has been already exist!");
			}

			else {
				int res = studao.insert(dto);
				if (res > 0)
					message = "Insert Sucessfully";
				else
					message = "Insert fail";
			}
			return "redirect:/studentSearch?message=" + message;
		} else {
			model.addAttribute("err", "Insert fail..Date Must Not be null!!!");
			return "BUD002";

		}
	}

	@RequestMapping(value = "/updateStudent", method = RequestMethod.GET)
	public String setupUpdateStudent(@RequestParam(name = "StuName", required = false) String StuName, ModelMap model,HttpSession session) {

		ClassRequestDTO cdto = new ClassRequestDTO();
		cdto.setId("");
		cdto.setName("");
		List<ClassResponseDTO> list = classdao.select(cdto);
		session.setAttribute("classlist",list);
		StudentRequestDTO dto = new StudentRequestDTO();
		dto.setStudentName(StuName);
		dto.setStudentId("");
		dto.setClassName("");

		StudentResponseDTO res = studao.select(dto).get(0);
		String month = res.getRegisterDate().substring(5, 7);
		String day = res.getRegisterDate().substring(8, 10);
		StudentBean bean = new StudentBean();
		bean.setId(res.getStudentId());
		bean.setName(res.getStudentName());
		bean.setClassName(res.getClassName());
		bean.setYear(res.getRegisterDate().substring(0, 4));
		bean.setMonth(month);
		bean.setDay(day);
		bean.setStatus(res.getStatus());
		model.addAttribute("bean", bean);
		return "BUD002-01";

	}

	@RequestMapping(value = "/studentUpdate", method = RequestMethod.POST)
	public String updatebook(@ModelAttribute("bean") @Validated StudentBean stuBean, BindingResult bs, ModelMap model) {
		if (bs.hasErrors()) {
			return "BUD002-01";
		}
		if (!stuBean.getDay().equals("Day") && !stuBean.getMonth().equals("Month")
				&& !stuBean.getYear().equals("Year")) {
			StudentRequestDTO dto = new StudentRequestDTO();
			dto.setStudentId(stuBean.getId());
			dto.setStudentName(stuBean.getName());
			dto.setStatus(stuBean.getStatus());
			dto.setClassName(stuBean.getClassName());
			dto.setRegisterDate(stuBean.getYear() + "-" + stuBean.getMonth() + "-" + stuBean.getDay());
			int res = studao.update(dto);
			String message = null;
			if (res > 0)
				message = "Update successfully";
			else
				message = "Update fail";
			return "redirect:/studentSearch?message=" + message;
		} else {
			
			model.addAttribute("err", "Insert fail..Date Must Not be null!!!");
			return "BUD002-01";

		}

	}

	@RequestMapping(value = "/studentDelete", method = RequestMethod.POST)
	public String deleteBook(@ModelAttribute("bean") StudentBean stuBean, ModelMap model) {
		StudentRequestDTO dto = new StudentRequestDTO();
		dto.setStudentId(stuBean.getId());
		int res = studao.delete(dto);
		String message = null;
		if (res > 0)
			message = "Delete Successfuly";
		else
			message = "Delete Failed!!";
		return "redirect:/studentSearch?message=" + message;

	}

}
