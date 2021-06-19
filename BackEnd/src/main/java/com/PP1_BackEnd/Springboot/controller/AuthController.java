package com.PP1_BackEnd.Springboot.controller;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.PP1_BackEnd.Springboot.model.ERole;
import com.PP1_BackEnd.Springboot.model.Role;
import com.PP1_BackEnd.Springboot.model.User;
import com.PP1_BackEnd.Springboot.payload.request.LoginRequest;
import com.PP1_BackEnd.Springboot.payload.request.SignupRequest;
import com.PP1_BackEnd.Springboot.payload.response.JwtResponse;
import com.PP1_BackEnd.Springboot.payload.response.MessageResponse;
import com.PP1_BackEnd.Springboot.repository.RoleRepository;
import com.PP1_BackEnd.Springboot.repository.UserRepository;
import com.PP1_BackEnd.Springboot.security.jwt.JwtUtils;
import com.PP1_BackEnd.Springboot.security.services.UserDetailsImpl;
import com.PP1_BackEnd.Springboot.service.JobEmployerService;
import com.PP1_BackEnd.Springboot.service.ProfileService;
import com.PP1_BackEnd.Springboot.service.UserService;

/*
 * This controller is responsible for logging in and Registering the user or add an employee by Admin user.
 * The credentials are stored in the database with hashed passwords along with other details. 
 * Jwt is used for authentication and authorization of users. 
 */
@CrossOrigin(origins = "https://match-making-pp1.herokuapp.com")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	JobEmployerService employerService;

	@Autowired
	ProfileService profileService;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	UserService userService;

	@Autowired
	JwtUtils jwtUtils;

	/*
	 * this controller takes in user name and password 
	 * as the input parameter and logs in the user
	 * by validating the fields 
	 */
	@PostMapping("/signin")
	public ResponseEntity < ? > authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List < String > roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	/*
	 * controller responsible for user registration which takes in 
	 * all the parameters as provided in SignupRequest  
	 */
	@PostMapping("/signup")
	public ResponseEntity < ? > registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		/*
		 *  check for existing records with user name and email
		 *  The user name and email are to be unique with each records 
		 *  to avoid any duplicate records
		 */
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		String user_type = signUpRequest.getUser_type();
		Set < Role > roles = new HashSet < > ();

		/* 
		 * the first user of the application is set default role as admin 
		 * each role for a user is assigned based on the input
		 */
		if (userRepository.findAll().isEmpty() == true) {
			Role admin = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			signUpRequest.setUser_type("ADMIN");
			roles.add(admin);
		} 
		else if (user_type.equals("EMPLOYER")) {
			Role employer = roleRepository.findByName(ERole.ROLE_EMPLOYER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(employer);
		} 
		else {
			Role userRole = roleRepository.findByName(ERole.ROLE_JOB_SEEKER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			signUpRequest.setUser_type("JOB_SEEKER");
			roles.add(userRole);
		}

		/*
		 *  After assigning the user roles, a new user is created in the database
		 */
		User user = new User(signUpRequest.getUsername(), signUpRequest.getFirstname(), signUpRequest.getLastname(),
				signUpRequest.getEmail(), signUpRequest.getAddress(),
				signUpRequest.getPhone(), encoder.encode(signUpRequest.getPassword()), signUpRequest.getUser_type());

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	/*
	 * this controller takes in details required to 
	 * add another admin by the existing admin
	 */
	@PostMapping("/addAdmin")
	public ResponseEntity < ? > addAdmin(@Valid @RequestBody SignupRequest signUpRequest) {
		// adding another admin
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		Set < Role > roles = new HashSet < > ();

		/*
		 * the user role is set as Admin and the account for this user is stored in database
		 */
		Role admin = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		signUpRequest.setUser_type("ADMIN");
		roles.add(admin);

		/*
		 * creating a new admin user
		 */
		User user = new User(signUpRequest.getUsername(), signUpRequest.getFirstname(), signUpRequest.getLastname(),
				signUpRequest.getEmail(), signUpRequest.getAddress(),
				signUpRequest.getPhone(), encoder.encode(signUpRequest.getPassword()), signUpRequest.getUser_type());

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	/*
	 * this controller deletes an admin and its associated 
	 * records from all the tables
	 */
	@PostMapping("/deleteAdmin")
	public Boolean deleteAdmin(@Valid @RequestBody SignupRequest signUpRequest) {

		/*
		 * only delete an admin if there exists at least one admin in the database
		 */
		if (userRepository.existsByUsername(signUpRequest.getUsername()) && userService.getAdminExistanceCount() > 2 &&
				userService.getUserType(signUpRequest.getUsername()).equals("ADMIN")) {
			String username = signUpRequest.getUsername();
			/*
			 * delete all the records linked with the admin record
			 */
			profileService.deleteProfile(username);
			employerService.deleteEmployer(username);
			userService.deleteUser(username);
			return true;
		}
		return false;

	}

	/*
	 * controller returns the list of all admin stored in database
	 */
	@GetMapping("/viewAllAdmin")
	public List < User > getAllByAdmin() {
		return userService.getAllByAdmin();
	}

	/*
	 * controller returns the total number of admin stored in database
	 */
	@GetMapping("/getAdminCount")
	public int getCountAdmin() {
		return userRepository.getTotalAdminCount();
	}

	/*
	 * controller returns the total number of job seekers stored in database
	 */
	@GetMapping("/getEmployeeCount")
	public int getCountEmployee() {
		return userRepository.getTotalEmployeesCount();
	}

	/*
	 * controller returns the total number of employers stored in database
	 */
	@GetMapping("/getEmployerCount")
	public int getCountEmployer() {
		return userRepository.getTotalEmployersCount();
	}

}
