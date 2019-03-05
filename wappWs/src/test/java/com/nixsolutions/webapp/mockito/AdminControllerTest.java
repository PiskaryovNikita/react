package com.nixsolutions.webapp.mockito;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import com.nixsolutions.webapp.controller.AdminController;
import com.nixsolutions.webapp.model.Role;
import com.nixsolutions.webapp.model.User;
import com.nixsolutions.webapp.service.RoleService;
import com.nixsolutions.webapp.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AdminControllerTest {
	@Mock
	private UserService userDao;
	@Mock
	private RoleService roleDao;
	@Mock
	private Validator userValidator;
	@Mock
	private Principal mockPrincipal;
	@InjectMocks
	private AdminController controller;
	private MockMvc mockMvc;

	@Before
	public void setup() throws SQLException {
		initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		when(userDao.findById(anyLong())).thenReturn(new User());
		when(userDao.findByLogin("userLogin")).thenReturn(new User(new Role(1l, "User")));
		when(userDao.findByLogin("adminLogin")).thenReturn(new User(new Role(2l, "Admin")));
		when(userDao.findAll()).thenReturn(new ArrayList<>());
		when(roleDao.findByName(any())).thenReturn(new Role());
		when(roleDao.findAll()).thenReturn(new ArrayList<>());
	}

	@Test
	public void testGetUserHomePage() throws Exception {
		when(mockPrincipal.getName()).thenReturn("userLogin");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/home").principal(mockPrincipal);
		mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("UserHomePage"));
		verify(userDao, times(1)).findByLogin("userLogin");
	}

	@Test
	public void testGetAdminHomePage() throws Exception {
		when(mockPrincipal.getName()).thenReturn("adminLogin");
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/home").principal(mockPrincipal);
		mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("AdminHomePage"))
				.andExpect(model().attributeExists("users"));
		InOrder inOrder = inOrder(userDao);
		inOrder.verify(userDao, times(1)).findByLogin("adminLogin");
		inOrder.verify(userDao, times(1)).findAll();
	}

	@Test
	public void testGetEditPage() throws Exception {
		mockMvc.perform(get("/adminEdit").param("userId", 1l + ""))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("AdminEditPage"))
				.andExpect(model().attributeExists("user", "roles"));
		InOrder inOrder = inOrder(userDao, roleDao);
		inOrder.verify(userDao, times(1)).findById(1l);
		inOrder.verify(roleDao, times(1)).findAll();
	}

	@Test
	public void testPostInvalidUserEdit() throws Exception {
		mockMvc.perform(post("/adminEdit").param("userId", "<error>").param("login", "login1")
				.param("password", "password1").param("email", "email1").param("firstName", "userFirstName")
				.param("lastName", "userLastName").param("birthday", "").param("role", "User"))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("AdminEditPage"))
				.andExpect(model().attributeExists("user", "roles"))
				.andExpect(model().attributeHasFieldErrors("user", "userId"))
				.andExpect(model().attributeHasFieldErrors("user", "birthday"));
		InOrder inOrder = inOrder(roleDao);
		inOrder.verify(roleDao, times(1)).findByName(any());
		inOrder.verify(roleDao, times(1)).findAll();
	}

	@Test
	public void testPostValidUserEdit() throws Exception {
		mockMvc.perform(post("/adminEdit").param("userId", "2").param("login", "someLogin")
				.param("password", "somePassword").param("email", "cemail@c").param("firstName", "cuserfn")
				.param("lastName", "cuserln").param("birthday", "1999-12-12").param("role", "User"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/home"))
				.andExpect(model().errorCount(0));
		InOrder inOrder = inOrder(userDao, roleDao);
		inOrder.verify(roleDao, times(1)).findByName(any());
		inOrder.verify(userDao, times(1)).update(any(User.class));
	}

	@Test
	public void testGetAddPage() throws Exception {
		mockMvc.perform(get("/adminAdd"))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("AdminAddPage"))
				.andExpect(model().attributeExists("user", "roles"));
		verify(roleDao, times(1)).findAll();
	}

	@Test
	public void testPostInvalidUserAdd() throws Exception {
		mockMvc.perform(post("/adminAdd").param("login", "login1")
				.param("password", "password1").param("email", "email1").param("firstName", "userFirstName")
				.param("lastName", "userLastName").param("birthday", "").param("role", "User"))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("AdminAddPage"))
				.andExpect(model().attributeExists("user", "roles"))
				.andExpect(model().attributeHasFieldErrors("user", "birthday"));
		InOrder inOrder = inOrder(userValidator, roleDao);
		inOrder.verify(userValidator, times(1)).validate(any(User.class), any(BindingResult.class));
		inOrder.verify(roleDao, times(1)).findByName(any());
		inOrder.verify(roleDao, times(1)).findAll();
	}

	@Test
	public void testPostValidUserAdd() throws Exception {
		mockMvc.perform(post("/adminAdd").param("login", "someLogin")
				.param("password", "somePassword").param("email", "cemail@c").param("firstName", "cuserfn")
				.param("lastName", "cuserln").param("birthday", "1999-12-12").param("role", "User"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/home"))
				.andExpect(model().errorCount(0));
		InOrder inOrder = inOrder(userValidator, roleDao, userDao);
		inOrder.verify(userValidator, times(1)).validate(any(User.class), any(BindingResult.class));
		inOrder.verify(roleDao, times(1)).findByName(any());
		inOrder.verify(userDao, times(1)).create(any(User.class));
	}
	
	@Test
	public void testWrongUrl() throws Exception {
		mockMvc.perform(get("/xxx"))
		.andExpect(status().isNotFound());
	}
}
