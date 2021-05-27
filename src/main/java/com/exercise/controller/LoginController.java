package com.exercise.controller;



import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.exercise.dao.UserDAO;
import com.exercise.dto.UserRequestDTO;
import com.exercise.dto.UserResponseDTO;
import com.exercise.model.LoginBean;

@Controller
public class LoginController {
	@Autowired
	private UserDAO userDao;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String loginUser(ModelMap model) {
		
		model.addAttribute("newUser",new LoginBean());
		return "LGN001";
	}
	@RequestMapping(value="/welcome", method=RequestMethod.GET)
	public String welcomeUser() {
		return "M00001";
	}
	
	@RequestMapping(value="/userLogin",method=RequestMethod.POST)
	public String userLogin(ModelMap model,@ModelAttribute("login") LoginBean login,HttpSession session) {
		
		if(login.getUserId().equals("") || login.getPassword().equals("")) {
			model.addAttribute("err", "Feilds must not be null");
			return "LGN001";
		}else {
			UserRequestDTO dto=new UserRequestDTO();
			dto.setId(login.getUserId());
			List<UserResponseDTO> list = userDao.select(dto);
			if (list.size() == 0) {
				model.addAttribute("err", "User not found!");
				return "LGN001";
			}else if (login.getPassword().equals(list.get(0).getPassword())) {
				
				session.setAttribute("date",new Date());
				session.setAttribute("sesUser", list.get(0));
				return "M00001";
			} else {
				model.addAttribute("err", "Password is incorrect!");
				return "LGN001";
			}
			
		}
	}
	
	@RequestMapping(value="/logout" ,method=RequestMethod.POST)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		session.invalidate();
		return "redirect:/";
	}
	
	

}















