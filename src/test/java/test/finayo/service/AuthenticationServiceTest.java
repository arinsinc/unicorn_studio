package test.unicorn.studio.service;

import com.unicorn.studio.dao.UserRepository;
import com.unicorn.studio.entity.User;
import com.unicorn.studio.service.UserServiceImp;
import com.unicorn.studio.utils.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import test.unicorn.studio.utils.InitializeData;

@ExtendWith(SpringExtension.class)
public class AuthenticationServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImp userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldAuthenticateUser() {
    }
}
