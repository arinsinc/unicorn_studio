package test.unicorn.studio.service;

import com.unicorn.studio.dao.CompanyRepository;
import com.unicorn.studio.dao.InvestorRepository;
import com.unicorn.studio.dao.StartupProgramRepository;
import com.unicorn.studio.entity.City;
import com.unicorn.studio.entity.Company;
import com.unicorn.studio.service.SearchServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {
    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private InvestorRepository investorRepository;

    @Mock
    private StartupProgramRepository startupProgramRepository;

    @InjectMocks
    private SearchServiceImp searchService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSearchCompaniesByKeyword() {

    }

}
