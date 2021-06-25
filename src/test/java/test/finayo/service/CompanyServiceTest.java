package test.unicorn.studio.service;

import com.unicorn.studio.dao.*;
import com.unicorn.studio.entity.*;
import com.unicorn.studio.exception.NotFoundException;
import com.unicorn.studio.projection.*;
import com.unicorn.studio.service.CompanyServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import test.unicorn.studio.utils.InitializeData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CompanyServiceTest {
    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private CompanyMetricsRepository companyMetricsRepository;

    @Mock
    private FundingRepository fundingRepository;

    @InjectMocks
    private CompanyServiceImp companyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldGetAllCompanies() {
        // Given
        List<CompanyPortfolioProjection> companyList = new ArrayList<>();
        Company company = new Company();
        Pageable pageable = PageRequest.of(0,20);

        // When
        when(companyService.getCompanies(pageable)).thenReturn(companyList);

        // Then
        assertEquals(companyList, companyService.getCompanies(pageable));
        verify(companyRepository, times(1)).findAll();
    }

    @Test
    public void shouldSaveCompany() {
        // Given
        Company company = InitializeData.initializeCompany();

        // When
        companyService.saveCompany(company);

        // Then
        ArgumentCaptor<Company> companyArgumentCaptor = ArgumentCaptor.forClass(Company.class);
        verify(companyRepository, times(1)).save(companyArgumentCaptor.capture());
        assertEquals(company, companyArgumentCaptor.getValue());
    }

    @Test
    public void shouldSaveCompanyThrowsException() {
        // Given
        Company company = new Company();

        // When
        when(companyRepository.save(company)).thenThrow(new NullPointerException(""));

        // Then
        assertThrows(NullPointerException.class, () -> {companyService.saveCompany(company);});
        verify(companyRepository, times(1)).save(company);
    }

    @Test
    public void shouldGetCompany() {
        // Given
        Company company = InitializeData.initializeCompany();

        // When
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        // Then
        companyService.saveCompany(company);
        assertEquals(company, companyService.getCompany("1"));
        verify(companyRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldGetCompanyThrowsException() {
        // Given
        Company company = InitializeData.initializeCompany();

        // When
        when(companyRepository.findById(1L)).thenThrow(new NotFoundException("Did not find company id: 1"));

        // Then
        Exception exception = assertThrows(NotFoundException.class, () -> {companyService.getCompany("1");});
        assertEquals("Did not find company id: 1", exception.getMessage());
        verify(companyRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldEditCompany() {
        // Given
        Company company = InitializeData.initializeCompany();

        // When
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));

        // Then
        companyService.saveCompany(company);
        company.setName("New York");
        companyService.saveCompany(company);
        assertEquals(company, companyService.getCompany("1"));
        verify(companyRepository, times(2)).save(company);
    }

    @Test
    public void shouldEditCompanyThrowsException() {
        // Given
        Company company = InitializeData.initializeCompany();

        // When
        companyService.saveCompany(company);
        when(companyRepository.save(company)).thenThrow(new NullPointerException(""));

        // Then
        company.setName(null);
        assertThrows(NullPointerException.class, ()->{companyService.saveCompany(company);});
        verify(companyRepository, times(2)).save(company);
    }

    @Test
    public void shouldDeleteCompany() {
        // Given
        Company company = InitializeData.initializeCompany();

        // When
        when(companyRepository.findById(1L)).thenThrow(new NullPointerException());

        // Then
        companyRepository.deleteById(1L);
        assertThrows(NullPointerException.class, ()->{companyService.getCompany("1");});
        verify(companyRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldDeleteCompanyThrowsException() {
        // Given
        Company company = InitializeData.initializeCompany();

        // When
        when(companyRepository.findById(1L)).thenThrow(new NotFoundException("Did not find company id: 1"));

        // Then
        Exception exception = assertThrows(NotFoundException.class, ()->{companyService.deleteCompany("1");});
        assertEquals("Did not find company id: 1", exception.getMessage());
    }

    @Test
    public void shouldGetAllCompanyMetrics() {
        // Given
        List<CompanyMetricsProjection> companyMetricsList = new ArrayList<>();
        Company company = InitializeData.initializeCompany();
        CompanyMetrics companyMetrics = InitializeData.initializeCompanyMetrics();


        // When
        companyMetricsRepository.save(companyMetrics);
        when(companyMetricsRepository.findByCompanyId(1L)).thenReturn(companyMetricsList);

        // Then
        assertEquals(companyMetricsList, companyService.getAllCompanyMetrics("1"));
        verify(companyMetricsRepository, times(1)).findByCompanyId(1L);
    }

    @Test
    public void shouldSaveCompanyMetrics() {
        // Given
        CompanyMetrics companyMetrics = InitializeData.initializeCompanyMetrics();

        // When
        when(companyMetricsRepository.findById(1L)).thenReturn(Optional.of(companyMetrics));

        // Then
        companyService.saveCompanyMetrics(companyMetrics);
        assertEquals(companyMetrics, companyService.getCompanyMetrics("1"));
        verify(companyMetricsRepository, times(1)).save(companyMetrics);
    }

    @Test
    public void shouldSaveCompanyMetricsThrowsException() {
        // Given
        CompanyMetrics companyMetrics = new CompanyMetrics();

        // When
        when(companyMetricsRepository.save(companyMetrics)).thenThrow(new NullPointerException(""));

        // Then
        assertThrows(NullPointerException.class, () -> {companyService.saveCompanyMetrics(companyMetrics);});
        verify(companyMetricsRepository, times(1)).save(companyMetrics);
    }

    @Test
    public void shouldGetCompanyMetrics() {
        // Given
        CompanyMetrics companyMetrics = InitializeData.initializeCompanyMetrics();

        // When
        when(companyMetricsRepository.findById(1L)).thenReturn(Optional.of(companyMetrics));

        // Then
        companyService.saveCompanyMetrics(companyMetrics);
        assertEquals(companyMetrics, companyService.getCompanyMetrics("1"));
        verify(companyMetricsRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldGetCompanyMetricsThrowsException() {
        // Given
        CompanyMetrics companyMetrics = InitializeData.initializeCompanyMetrics();

        // When
        when(companyMetricsRepository.findById(1L)).thenThrow(new NotFoundException("Did not find company metrics id: 1"));

        // Then
        Exception exception = assertThrows(NotFoundException.class, () -> {companyService.getCompanyMetrics("1");});
        assertEquals("Did not find company metrics id: 1", exception.getMessage());
        verify(companyMetricsRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldEditCompanyMetrics() {
        // Given
        CompanyMetrics companyMetrics = InitializeData.initializeCompanyMetrics();

        // When
        when(companyMetricsRepository.findById(1L)).thenReturn(Optional.of(companyMetrics));

        // Then
        companyService.saveCompanyMetrics(companyMetrics);
        companyMetrics.setRevenue(500000);
        companyService.saveCompanyMetrics(companyMetrics);
        assertEquals(companyMetrics, companyService.getCompanyMetrics("1"));
        verify(companyMetricsRepository, times(2)).save(companyMetrics);
    }

    @Test
    public void shouldEditCompanyMetricsThrowsException() {
        // Given
        CompanyMetrics companyMetrics = InitializeData.initializeCompanyMetrics();

        // When
        companyService.saveCompanyMetrics(companyMetrics);
        when(companyMetricsRepository.save(companyMetrics)).thenThrow(new NullPointerException(""));

        // Then
        companyMetrics.setDurationMonth(null);
        assertThrows(NullPointerException.class, ()->{companyService.saveCompanyMetrics(companyMetrics);});
        verify(companyMetricsRepository, times(2)).save(companyMetrics);
    }

    @Test
    public void shouldDeleteCompanyMetrics() {
        // Given
        CompanyMetrics companyMetrics = InitializeData.initializeCompanyMetrics();

        // When
        when(companyMetricsRepository.findById(1L)).thenThrow(new NullPointerException());

        // Then
        companyMetricsRepository.deleteById(1L);
        assertThrows(NullPointerException.class, ()->{companyService.getCompanyMetrics("1");});
        verify(companyMetricsRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldDeleteCompanyMetricsThrowsException() {
        // Given
        CompanyMetrics companyMetrics = InitializeData.initializeCompanyMetrics();

        // When
        when(companyMetricsRepository.findById(1L)).thenThrow(new NotFoundException("Did not find company metrics id: 1"));

        // Then
        Exception exception = assertThrows(NotFoundException.class, ()->{companyService.deleteCompanyMetrics("1");});
        assertEquals("Did not find company metrics id: 1", exception.getMessage());
    }



    @Test
    public void shouldGetAllFunding() {
        // Given
        List<FundingProjection> fundingList = new ArrayList<>();
        Company company = InitializeData.initializeCompany();
        Funding funding = InitializeData.initializeFunding();


        // When
        fundingRepository.save(funding);
        when(fundingRepository.findByCompanyId(1L)).thenReturn(fundingList);

        // Then
        assertEquals(fundingList, companyService.getFundingList("1"));
        verify(fundingRepository, times(1)).findByCompanyId(1L);
    }

    @Test
    public void shouldSaveFunding() {
        // Given
        Funding funding = InitializeData.initializeFunding();

        // When
        when(fundingRepository.findById(1L)).thenReturn(Optional.of(funding));

        // Then
        companyService.saveFunding(funding);
        assertEquals(funding, companyService.getFunding("1"));
        verify(fundingRepository, times(1)).save(funding);
    }

    @Test
    public void shouldSaveFundingThrowsException() {
        // Given
        Funding funding = new Funding();

        // When
        when(fundingRepository.save(funding)).thenThrow(new NullPointerException(""));

        // Then
        assertThrows(NullPointerException.class, () -> {companyService.saveFunding(funding);});
        verify(fundingRepository, times(1)).save(funding);
    }

    @Test
    public void shouldGetFunding() {
        // Given
        Funding funding = InitializeData.initializeFunding();

        // When
        when(fundingRepository.findById(1L)).thenReturn(Optional.of(funding));

        // Then
        companyService.saveFunding(funding);
        assertEquals(funding, companyService.getFunding("1"));
        verify(fundingRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldGetFundingThrowsException() {
        // Given
        Funding funding = InitializeData.initializeFunding();

        // When
        when(fundingRepository.findById(1L)).thenThrow(new NotFoundException("Did not find funding id: 1"));

        // Then
        Exception exception = assertThrows(NotFoundException.class, () -> {companyService.getFunding("1");});
        assertEquals("Did not find funding id: 1", exception.getMessage());
        verify(fundingRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldEditFunding() {
        // Given
        Funding funding = InitializeData.initializeFunding();

        // When
        when(fundingRepository.findById(1L)).thenReturn(Optional.of(funding));

        // Then
        companyService.saveFunding(funding);
        funding.setAmount(2500000);
        companyService.saveFunding(funding);
        assertEquals(funding, companyService.getFunding("1"));
        verify(fundingRepository, times(2)).save(funding);
    }

    @Test
    public void shouldEditFundingThrowsException() {
        // Given
        Funding funding = InitializeData.initializeFunding();

        // When
        companyService.saveFunding(funding);
        when(fundingRepository.save(funding)).thenThrow(new NullPointerException(""));

        // Then
        funding.setFundingType(null);
        assertThrows(NullPointerException.class, ()->{companyService.saveFunding(funding);});
        verify(fundingRepository, times(2)).save(funding);
    }

    @Test
    public void shouldDeleteFunding() {
        // Given
        Funding funding = InitializeData.initializeFunding();

        // When
        when(fundingRepository.findById(1L)).thenThrow(new NullPointerException());

        // Then
        fundingRepository.deleteById(1L);
        assertThrows(NullPointerException.class, ()->{companyService.getFunding("1");});
        verify(fundingRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldDeleteFundingThrowsException() {
        // Given
        Funding funding = InitializeData.initializeFunding();

        // When
        when(fundingRepository.findById(1L)).thenThrow(new NotFoundException("Did not find funding id: 1"));

        // Then
        Exception exception = assertThrows(NotFoundException.class, ()->{companyService.deleteFunding("1");});
        assertEquals("Did not find funding id: 1", exception.getMessage());
    }
}
