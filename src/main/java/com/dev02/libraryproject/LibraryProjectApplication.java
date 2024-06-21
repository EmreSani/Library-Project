package com.dev02.libraryproject;

import com.dev02.libraryproject.entity.concretes.user.Role;
import com.dev02.libraryproject.entity.enums.RoleType;
import com.dev02.libraryproject.payload.request.user.UserRequest;
import com.dev02.libraryproject.repository.user.UserRoleRepository;
import com.dev02.libraryproject.service.user.UserRoleService;
import com.dev02.libraryproject.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class LibraryProjectApplication implements CommandLineRunner {

	private final UserRoleService userRoleService;
	private final UserRoleRepository userRoleRepository;

	private final UserService userService;

	public LibraryProjectApplication(UserRoleService userRoleService, UserRoleRepository userRoleRepository, UserService userService) {
		this.userRoleService = userRoleService;
		this.userRoleRepository = userRoleRepository;
		this.userService = userService;
	}
	public static void main(String[] args) {
		SpringApplication.run(LibraryProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		//Role tablomu dolduracağım ama önce içi bos mu diye kontrol edeceğim
		if(userRoleService.getAllUserRole().isEmpty()){
			Role admin = new Role();
			admin.setRoleType(RoleType.ADMIN);
			admin.setName("Admin");
			userRoleRepository.save(admin);

			Role employee = new Role();
			employee.setRoleType(RoleType.EMPLOYEE);
			employee.setName("Employee");
			userRoleRepository.save(employee);

			Role member = new Role();
			member.setRoleType(RoleType.MEMBER);
			member.setName("Member");
			userRoleRepository.save(member);


		}

		//Built_in Admin oluşturuluyor eger sistemde Admin yoksa
		if(userService.countAllAdmins()==0){

			UserRequest adminRequest = new UserRequest();
			adminRequest.setEmail("admin@admin.com");
			adminRequest.setPassword("123456");
			adminRequest.setFirstName("Ahmet");
			adminRequest.setLastName("Dikbayir");
			adminRequest.setPhone("111-111-1111");
			adminRequest.setBirthDate(LocalDate.of(1980,2,2));
			adminRequest.setAddress("Mehmet Akif Ersoy mahallesi Yenimahalle Ankara");
			userService.saveUser(adminRequest, "Admin");
		}
	}
}
