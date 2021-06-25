package test.unicorn.studio.service;

import com.unicorn.studio.dao.EquityRepository;
import com.unicorn.studio.dao.InvestorRepository;
import com.unicorn.studio.entity.Company;
import com.unicorn.studio.entity.Equity;
import com.unicorn.studio.entity.Investor;
import com.unicorn.studio.exception.NotFoundException;
import com.unicorn.studio.projection.EquityProjection;
import com.unicorn.studio.projection.InvestorPortfolioProjection;
import com.unicorn.studio.service.InvestorServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class InvestorServiceTest {
    @Mock
    private EquityRepository equityRepository;

    @Mock
    private InvestorRepository investorRepository;

    @InjectMocks
    private InvestorServiceImp investorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldGetAllInvestors() {
        // Given
        List<InvestorPortfolioProjection> investorList = new ArrayList<>();
        Company company = InitializeData.initializeCompany();
        Investor investor = InitializeData.initializeInvestor();
        Pageable pageable = PageRequest.of(0,20);

        // When
        investorRepository.save(investor);
        when(investorRepository.findByCompanies_Id(1L)).thenReturn(Optional.of(investorList));

        // Then
        assertEquals(investorList, investorService.getInvestors(pageable));
        verify(investorRepository, times(1)).findByCompanies_Id(1L);
    }

    @Test
    public void shouldSaveInvestors() {
        // Given
        Investor investor = InitializeData.initializeInvestor();

        // When
        when(investorRepository.findById(1L)).thenReturn(Optional.of(investor));

        // Then
        investorService.saveInvestor(investor);
        assertEquals(investor, investorService.getInvestor("1"));
        verify(investorRepository, times(1)).save(investor);
    }

    @Test
    public void shouldSaveInvestorsThrowsException() {
        // Given
        Investor investor = new Investor();

        // When
        when(investorRepository.save(investor)).thenThrow(new NullPointerException(""));

        // Then
        assertThrows(NullPointerException.class, () -> {investorService.saveInvestor(investor);});
        verify(investorRepository, times(1)).save(investor);
    }

    @Test
    public void shouldGetInvestors() {
        // Given
        Investor investor = InitializeData.initializeInvestor();

        // When
        when(investorRepository.findById(1L)).thenReturn(Optional.of(investor));

        // Then
        investorService.saveInvestor(investor);
        assertEquals(investor, investorService.getInvestor("1"));
        verify(investorRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldGetInvestorsThrowsException() {
        // Given
        Investor investor = InitializeData.initializeInvestor();

        // When
        when(investorRepository.findById(1L)).thenThrow(new NotFoundException("Did not find investor id: 1"));

        // Then
        Exception exception = assertThrows(NotFoundException.class, () -> {investorService.getInvestor("1");});
        assertEquals("Did not find investor id: 1", exception.getMessage());
        verify(investorRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldEditInvestors() {
        // Given
        Investor investor = InitializeData.initializeInvestor();

        // When
        when(investorRepository.findById(1L)).thenReturn(Optional.of(investor));

        // Then
        investorService.saveInvestor(investor);
        investor.setEmail("ceo@tesla.com");
        investorService.saveInvestor(investor);
        assertEquals(investor, investorService.getInvestor("1"));
        verify(investorRepository, times(2)).save(investor);
    }

    @Test
    public void shouldEditInvestorsThrowsException() {
        // Given
        Investor investor = InitializeData.initializeInvestor();

        // When
        investorService.saveInvestor(investor);
        when(investorRepository.save(investor)).thenThrow(new NullPointerException(""));

        // Then
        investor.setFullName(null);
        assertThrows(NullPointerException.class, ()->{investorService.saveInvestor(investor);});
        verify(investorRepository, times(2)).save(investor);
    }

    @Test
    public void shouldDeleteInvestors() {
        // Given
        Investor investor = InitializeData.initializeInvestor();

        // When
        when(investorRepository.findById(1L)).thenThrow(new NullPointerException());

        // Then
        investorRepository.deleteById(1L);
        assertThrows(NullPointerException.class, ()->{investorService.getInvestor("1");});
        verify(investorRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldDeleteInvestorsThrowsException() {
        // Given
        Investor investor = InitializeData.initializeInvestor();

        // When
        when(investorRepository.findById(1L)).thenThrow(new NotFoundException("Did not find investor id: 1"));

        // Then
        Exception exception = assertThrows(NotFoundException.class, ()->{investorService.deleteInvestor("1");});
        assertEquals("Did not find investor id: 1", exception.getMessage());
    }

    @Test
    public void shouldGetAllEquities() {
        // Given
        List<EquityProjection> equityList = new ArrayList<>();
        Investor investor = InitializeData.initializeInvestor();
        Equity equity = InitializeData.initializeEquity();


        // When
        equity.setInvestor(investor);
        equityRepository.save(equity);
        when(equityRepository.findByInvestorId((1L))).thenReturn(equityList);

        // Then
        assertEquals(equityList, investorService.getEquities("1"));
        verify(equityRepository, times(1)).findByInvestorId(1L);
    }

    @Test
    public void shouldSaveEquity() {
        // Given
        Equity equity = InitializeData.initializeEquity();

        // When
        when(equityRepository.findById(1L)).thenReturn(Optional.of(equity));

        // Then
        investorService.saveEquity(equity);
        assertEquals(equity, investorService.getEquity("1"));
        verify(equityRepository, times(1)).save(equity);
    }

    @Test
    public void shouldSaveEquityThrowsException() {
        // Given
        Equity equity = new Equity();

        // When
        when(equityRepository.save(equity)).thenThrow(new NullPointerException(""));

        // Then
        assertThrows(NullPointerException.class, () -> {investorService.saveEquity(equity);});
        verify(equityRepository, times(1)).save(equity);
    }

    @Test
    public void shouldGetEquity() {
        // Given
        Equity equity = InitializeData.initializeEquity();

        // When
        when(equityRepository.findById(1L)).thenReturn(Optional.of(equity));

        // Then
        investorService.saveEquity(equity);
        assertEquals(equity, investorService.getEquity("1"));
        verify(equityRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldGetEquityThrowsException() {
        // Given
        Equity equity = InitializeData.initializeEquity();

        // When
        when(equityRepository.findById(1L)).thenThrow(new NotFoundException("Did not find equity id: 1"));

        // Then
        Exception exception = assertThrows(NotFoundException.class, () -> {investorService.getEquity("1");});
        assertEquals("Did not find equity id: 1", exception.getMessage());
        verify(equityRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldEditEquity() {
        // Given
        Equity equity = InitializeData.initializeEquity();

        // When
        when(equityRepository.findById(1L)).thenReturn(Optional.of(equity));

        // Then
        investorService.saveEquity(equity);
        equity.setShare(15.00f);
        investorService.saveEquity(equity);
        assertEquals(equity, investorService.getEquity("1"));
        verify(equityRepository, times(2)).save(equity);
    }

    @Test
    public void shouldEditEquityThrowsException() {
        // Given
        Equity equity = InitializeData.initializeEquity();

        // When
        investorService.saveEquity(equity);
        when(equityRepository.save(equity)).thenThrow(new NullPointerException(""));

        // Then
        equity.setAllottedIn(null);
        assertThrows(NullPointerException.class, ()->{investorService.saveEquity(equity);});
        verify(equityRepository, times(2)).save(equity);
    }

    @Test
    public void shouldDeleteEquity() {
        // Given
        Equity equity = InitializeData.initializeEquity();

        // When
        when(equityRepository.findById(1L)).thenThrow(new NullPointerException());

        // Then
        equityRepository.deleteById(1L);
        assertThrows(NullPointerException.class, ()->{investorService.getEquity("1");});
        verify(equityRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldDeleteEquityThrowsException() {
        // Given
        Equity equity = InitializeData.initializeEquity();

        // When
        when(equityRepository.findById(1L)).thenThrow(new NotFoundException("Did not find equity id: 1"));

        // Then
        Exception exception = assertThrows(NotFoundException.class, ()->{investorService.deleteEquity("1");});
        assertEquals("Did not find equity id: 1", exception.getMessage());
    }
}
