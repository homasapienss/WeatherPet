package edu.homasapienss.weather;

import edu.homasapienss.weather.config.WebMvcConfig;
import edu.homasapienss.weather.models.User;
import edu.homasapienss.weather.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(classes = {
        TestConfig.class,
        WebMvcConfig.class
})
@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthenticationControllerIntegrationTest {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private UserRepository userRepository;
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    @Order(1)
    @DisplayName("Регистрация нового пользователя должна создать новую запись в бд")
    @Transactional
    void register_ShouldCreateUser() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .param("login", "ivan123")
                        .param("password", "qwerty"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        Optional<User> created = userRepository.getByLogin("ivan123");
        Assertions.assertTrue(created.isPresent());
        Assertions.assertNotEquals("qwerty", created.get().getPassword());
    }

    @Test
    @Order(2)
    @DisplayName("Повторная регистрация с тем же логином должна вернуть ошибку")
    @Transactional
    void signUp_DuplicateUser_ShouldFail() throws Exception {
        mockMvc.perform(post("/auth/register")
                        .param("login", "alex")
                        .param("password", "12345"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        mockMvc.perform(post("/auth/register")
                        .param("login", "alex")
                        .param("password", "12345"))
                .andExpect(status().is4xxClientError());
    }
}
