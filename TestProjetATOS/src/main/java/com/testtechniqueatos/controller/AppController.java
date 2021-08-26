package com.testtechniqueatos.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.testtechniqueatos.model.User;
import com.testtechniqueatos.repository.UserRepository;

@Controller
public class AppController {

	@Autowired
	private UserRepository userRepo;

	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}

	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());

		return "signup_form";
	}

	@PostMapping("/signup")
	public String processRegister(@Valid User user, BindingResult bindingResult, Model model) {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
		String inputEmail = user.getEmail();
		User userExist = userRepo.findByEmail(inputEmail);
		if ( userExist !=null) {
			model.addAttribute("username_error", "true");
		}
		
		if (!validate(inputEmail)) {
			model.addAttribute("username_wrong", "true");
		}
		
		LocalDate currentDate = new LocalDate();
		LocalDate birthDate = new LocalDate(user.getBirthdate());
		int calculatedAge = calculateAgeWithJodaTime(birthDate, currentDate);
		
		
		if (calculatedAge<18) {
			model.addAttribute("birthdate_error", "true");
		}
		
		if (bindingResult.hasErrors() || (calculatedAge<18) || userExist!=null || !validate(inputEmail)) {
			return "signup_form";
		}


		userRepo.save(user);

		return "register_success";
	}

	@GetMapping("/users")
	public String listUsers(Model model) {
		List<User> listUsers = userRepo.findAll();
		model.addAttribute("listUsers", listUsers);

		return "users";
	}

	@GetMapping("users/id/{id}")
	public ResponseEntity<User> getById(@PathVariable long id) throws Exception {
		Optional<User> user = Optional.of(userRepo.findById(id).get());
		if (user.isPresent()) {
			return new ResponseEntity<>(user.get(), HttpStatus.OK);
		} else {
			throw new Exception();
		}
	}

	// @GetMapping("users/{email}")
	@RequestMapping(value = "users/email/{email}", method = RequestMethod.GET)
	public ResponseEntity<List<Optional<User>>> getByEmail(@PathVariable String email) {

		List<User> listUsers = userRepo.findAll();
		List<Optional<User>> listUsersFinal = new ArrayList<Optional<User>>();
		
		Optional<User> user = null;
		for (User u : listUsers) {
			
			
			if (u.getEmail()!= null && u.getEmail().equalsIgnoreCase(email)) {

				user = Optional.of(userRepo.findById(u.getId()).get());
				listUsersFinal.add(user);
				
			}else {
				continue;
			}

		}
		return new ResponseEntity<List<Optional<User>>>(listUsersFinal, HttpStatus.OK);
	}
	
	@RequestMapping(value = "users/birthdate/{birthdate}", method = RequestMethod.GET)
	public ResponseEntity<List<Optional<User>>> getByBirthdate(@PathVariable String birthdate) {

		List<User> listUsers = userRepo.findAll();
		List<Optional<User>> listUsersFinal = new ArrayList<Optional<User>>();
		
		Optional<User> user = null;
		for (User u : listUsers) {
			
			
			if (u.getBirthdate()!= null && u.getBirthdate().equalsIgnoreCase(birthdate)) {

				user = Optional.of(userRepo.findById(u.getId()).get());
				listUsersFinal.add(user);
				
			}else {
				continue;
			}

		}
		return new ResponseEntity<List<Optional<User>>>(listUsersFinal, HttpStatus.OK);
	}
	
	
	public int calculateAgeWithJodaTime(
			  org.joda.time.LocalDate birthDate,
			  org.joda.time.LocalDate currentDate) {
			    // validate inputs ...
			    Years age = Years.yearsBetween(birthDate, currentDate);
			    return age.getYears();   
			}
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

		public static boolean validate(String emailStr) {
		        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		        return matcher.find();
		}
}
