package test.unicorn.studio.controller;

import com.unicorn.studio.controller.UserController;
import com.unicorn.studio.dao.UserRepository;
import com.unicorn.studio.entity.Role;
import com.unicorn.studio.entity.User;
import com.unicorn.studio.projection.UserProjection;
import com.unicorn.studio.service.UserService;
import com.unicorn.studio.service.UserServiceImp;
import com.unicorn.studio.utils.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import test.unicorn.studio.utils.InitializeData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@Import(UserController.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {SpringBootApplication.class})
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserValidator userValidator;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userService).build();
    }

    @Test
    public void shouldGetUsersReturns200() throws Exception {
        User user = InitializeData.initializeUser();
        List<User> userList = new ArrayList<>();
        userList.add(user);

        when(userService.getUsers()).thenReturn(userList);
        userService.saveUser(user);

        mockMvc.perform(get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetUserReturn200() throws Exception {
        User user = InitializeData.initializeUser();
        UserProjection result = null;

        when(userService.getUser("1")).thenReturn(result);
        userService.saveUser(user);

        mockMvc.perform(get("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetUserReturn404() throws Exception {
        User user = new User();
        UserProjection result = null;

        when(userService.getUser("1")).thenReturn(result);

        mockMvc.perform(get("/api/v1/users/11251")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldSaveUserReturn201() throws Exception {
        User user = InitializeData.initializeUser();
        UserProjection result = null;

        when(userService.getUser("1")).thenReturn(result);

        mockMvc.perform(post("/api/v1/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldSaveUserReturn400() throws Exception {
        User user = new User();
        UserProjection result = null;

        when(userService.getUser("1")).thenReturn(result);

        mockMvc.perform(post("/api/v1/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldEditUserReturn200() throws Exception {
        User user = InitializeData.initializeUser();
        UserProjection result = null;

        when(userService.getUser("1")).thenReturn(result);
        user.setFullName("Elon Musk");

        mockMvc.perform(put("/api/v1/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldEditUserReturn400() throws Exception {
        User user = new User();
        UserProjection result = null;

        when(userService.getUser("1")).thenReturn(result);

        mockMvc.perform(put("/api/v1/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldDeleteUserReturn200() throws Exception {
        User user = InitializeData.initializeUser();
        UserProjection result = null;

        when(userService.getUser("1")).thenReturn(result);
        userService.saveUser(user);

        mockMvc.perform(delete("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteUserReturn404() throws Exception {
        User user = new User();
        UserProjection result = null;

        when(userService.getUser("1")).thenReturn(result);

        mockMvc.perform(delete("/api/v1/users/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldUserEmailConfirmedReturn200() throws Exception {
        User user = InitializeData.initializeUser();
        Role role = new Role("EMPLOYER");
        userService.saveRole(role);
        user.setRole(userService.getRoleByName("EMPLOYER"));

        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);
        userService.saveUser(user);
        user = userService.getUserByEmail(user.getEmail());

        mockMvc.perform(post("/api/v1/email-confirmation/")
                .param("email",user.getEmail())
                .param("token",user.getConfirmationToken()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUserEmailConfirmedReturn400() throws Exception {
        User user = InitializeData.initializeUser();

        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);
        userService.saveUser(user);

        mockMvc.perform(post("/api/v1/email-confirmation/")
                .param("email",user.getEmail())
                .param("token","sample"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldUserPasswordResetReturn201() throws Exception {
        User user = InitializeData.initializeUser();

        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);
        userService.saveUser(user);

        mockMvc.perform(post("/api/v1/reset-password/")
                .param("email",user.getEmail()))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldUserPasswordResetReturn400() throws Exception {
        User user = InitializeData.initializeUser();

        when(userService.getUserByEmail(user.getEmail())).thenReturn(user);
        userService.saveUser(user);

        mockMvc.perform(post("/api/v1/reset-password/")
                .param("email","asdf"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldPasswordResetConfirmedReturn200() throws Exception {
        User user = InitializeData.initializeUser();
        UserProjection result = null;

        when(userService.getUser("1")).thenReturn(result);
        userService.saveUser(user);
        result = userService.getUser("1");

        mockMvc.perform(post("/api/v1/reset-password-verified/")
                .param("email",user.getEmail())
                .param("token",user.getPasswordResetToken()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldPasswordResetConfirmedReturn400() throws Exception {
        User user = InitializeData.initializeUser();
        UserProjection result = null;

        when(userService.getUser("1")).thenReturn(result);
        userService.saveUser(user);
        result = userService.getUser("1");

        mockMvc.perform(post("/api/v1/reset-password-verified/")
                .param("email",user.getEmail())
                .param("token","asdf"))
                .andExpect(status().is4xxClientError());
    }
}
