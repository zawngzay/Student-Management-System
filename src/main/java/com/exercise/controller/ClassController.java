package com.exercise.controller;



import java.util.List;

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
import com.exercise.dto.ClassRequestDTO;
import com.exercise.dto.ClassResponseDTO;
import com.exercise.model.ClassBean;

@Controller
public class ClassController {

	
	@Autowired
	private ClassDAO classdao;
	@RequestMapping(value = "/classSetup", method = RequestMethod.GET)
	public ModelAndView setupClass(@RequestParam(name="message",required=false) String message,ModelMap model) {
		model.addAttribute("msg",message);
		return new ModelAndView("BUD003", "class", new ClassBean());
	}

	@RequestMapping(value="/addClass",method=RequestMethod.POST)
	public String addClass(@ModelAttribute("class") @Validated ClassBean classBean,BindingResult bs, ModelMap model) {
		if(bs.hasErrors())
	{
		return "BUD003";
	}
		ClassRequestDTO dto = new ClassRequestDTO();
		dto.setId(classBean.getId());
		dto.setName(classBean.getName());
		String message=null;
		List<ClassResponseDTO> list = classdao.select(dto);
		if (list.size() != 0) {
			model.addAttribute("bean", classBean);
			model.addAttribute("err", "Class has been already exist!");
			return "BUD003";
		}else {
			int res = classdao.insert(dto);
			if (res > 0)
				message= "Insert successful";
			else
				message="Insert fail";
		}
		return "redirect:/classSetup?message="+message;
		
	}
}
