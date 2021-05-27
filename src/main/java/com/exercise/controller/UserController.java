package com.exercise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.exercise.dao.UserDAO;
import com.exercise.dto.UserRequestDTO;
import com.exercise.dto.UserResponseDTO;
import com.exercise.model.UserBean;

@Controller
public class UserController {

	@Autowired
	private UserDAO userdao;

	@RequestMapping(value = "/userSearchSetup", method = RequestMethod.GET)
	public String setupUserSearch(@RequestParam(name = "message", required = false) String message,ModelMap model) {
		model.addAttribute("msg",message);
		model.addAttribute("userSearch", new UserBean());
		return "USR001";
		
	}

	@RequestMapping(value = "/userSearch", method = RequestMethod.POST)
	public String userSearch(ModelMap model, @ModelAttribute("userSearch") UserBean user) {
		UserRequestDTO dto = new UserRequestDTO();
		dto.setId(user.getId());
		dto.setName(user.getName());
		List<UserResponseDTO> list = userdao.select(dto);
		if (list.size() == 0)
			model.addAttribute("msg", "User not found!");
		else
			model.addAttribute("userlist", list);
		return "USR001";
	}

	@RequestMapping(value = "/userAddSetUp", method = RequestMethod.GET)
	public ModelAndView userAddSetUp() {
		return new ModelAndView("USR002", "userAdd", new UserBean());
	}

	@RequestMapping(value = "/userAdd", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("userAdd") @Validated UserBean userBean, BindingResult bs, ModelMap model) {
		if (bs.hasErrors()) {
			return "USR002";
		}
		if (userBean.getPassword().equals(userBean.getConfirm())) {
			model.addAttribute("bean", userBean);
			UserRequestDTO dto = new UserRequestDTO();
			dto.setId(userBean.getId());
			dto.setName(userBean.getName());
			dto.setPassword(userBean.getPassword());
			List<UserResponseDTO> list = userdao.select(dto);
			if (list.size() != 0) {
				model.addAttribute("err", "UserId has been already exist!");
				return "USR002";
			} else {
				int res = userdao.insert(dto);
				if (res > 0) {
					String message="Insert successful";
					return "redirect:/userSearchSetup?message="+message;
				}
				else {
					String message="Insert fail";
					return "redirect:/userSearchSetup?message="+message;
					
					}
			}
		} else {
			model.addAttribute("err", "Password are not match");
			model.addAttribute("bean", userBean);
			return "USR002";
		}
	}

	@RequestMapping(value = "/setupUpdateUser", method = RequestMethod.GET)
	public String setupUpdateUser(@RequestParam(name = "id", required = false) String UserId,ModelMap model) {
		UserRequestDTO dto = new UserRequestDTO();
		dto.setId(UserId);
		model.addAttribute("bean",userdao.select(dto).get(0));
		return "USR002-01";
		
	}

	@RequestMapping(value = "/userUpdate", method = RequestMethod.POST)
	public String updatebook(@ModelAttribute("bean") @Validated UserBean user, BindingResult bs, ModelMap model) {
		if (bs.hasErrors()) {
			return "USR002-01";
		}

		if (user.getPassword().equals(user.getConfirm())) {

			UserRequestDTO dto = new UserRequestDTO();
			dto.setId(user.getId());
			dto.setName(user.getName());
			dto.setPassword(user.getPassword());
			int res = userdao.update(dto);
			if (res > 0) {
				String message="Update successful";
				return "redirect:/userSearchSetup?message="+message;
			}
			else {
				String message="Update fail";
				return "redirect:/userSearchSetup?message="+message;
			}
		} else {
			model.addAttribute("err", "Password are not match");
			model.addAttribute("bean", user);
			return "USR002-01";
		}

	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.GET)
	public String deleteBook(@RequestParam(name = "id", required = false) String id, ModelMap model) {
		UserRequestDTO dto = new UserRequestDTO();
		dto.setId(id);
		int res = userdao.delete(dto);
		String message=null;
		if (res > 0) 
			 message="Delete Successfuly";
		else 
			 message="Delete Failed!!";
		return "redirect:/userSearchSetup?message="+message;
		

	}

}
