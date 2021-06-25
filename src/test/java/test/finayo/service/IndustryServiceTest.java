package test.unicorn.studio.service;

import com.unicorn.studio.entity.Industry;
import com.unicorn.studio.dao.IndustryRepository;
import com.unicorn.studio.exception.NotFoundException;
import com.unicorn.studio.service.CompanyServiceImp;
import com.unicorn.studio.service.UtilityServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class IndustryServiceTest {
    @Mock
    private IndustryRepository industryRepository;

    @InjectMocks
    private UtilityServiceImp utilityService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetAllIndustries() {
        // Given
        List<Industry> employerList = new ArrayList<>();
        Industry employer = new Industry();

        // When
        when(industryRepository.findAll()).thenReturn(employerList);

        // Then
        assertEquals(employerList, utilityService.getIndustries());
        verify(industryRepository, times(1)).findAll();
    }

    @Test
    public void shouldSaveIndustry() {
        // Given
        Industry employer = new Industry("Financial Services","Internet");

        // When
        when(industryRepository.findById(1L)).thenReturn(Optional.of(employer));

        // Then
        utilityService.saveIndustry(employer);
        assertEquals(employer, utilityService.getIndustry(1L));
        verify(industryRepository, times(1)).save(employer);
    }

    @Test
    public void shouldSaveIndustryThrowsException() {
        // Given
        Industry employer = new Industry();

        // When
        when(industryRepository.save(employer)).thenThrow(new NullPointerException(""));

        // Then
        assertThrows(NullPointerException.class, () -> {utilityService.saveIndustry(employer);});
        verify(industryRepository, times(1)).save(employer);
    }

    @Test
    public void shouldGetIndustry() {
        // Given
        Industry employer = new Industry("Financial Services","Internet");

        // When
        when(industryRepository.findById(1L)).thenReturn(Optional.of(employer));

        // Then
        utilityService.saveIndustry(employer);
        assertEquals(employer, utilityService.getIndustry(1L));
        verify(industryRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldGetIndustryThrowsException() {
        // Given
        Industry employer = new Industry("Financial Services","Internet");

        // When
        when(industryRepository.findById(1L)).thenThrow(new NotFoundException("Did not find employer id: 1"));

        // Then
        Exception exception = assertThrows(NotFoundException.class, () -> {utilityService.getIndustry(1L);});
        assertEquals("Did not find employer id: 1", exception.getMessage());
        verify(industryRepository, times(1)).findById(1L);
    }

    @Test
    public void shouldEditIndustry() {
        // Given
        Industry employer = new Industry("Financial Services","Internet");

        // When
        when(industryRepository.findById(1L)).thenReturn(Optional.of(employer));

        // Then
        utilityService.saveIndustry(employer);
        employer.setName("New York");
        utilityService.saveIndustry(employer);
        assertEquals(employer, utilityService.getIndustry(1L));
        verify(industryRepository, times(2)).save(employer);
    }

    @Test
    public void shouldEditIndustryThrowsException() {
        // Given
        Industry employer = new Industry("Financial Services","Internet");

        // When
        utilityService.saveIndustry(employer);
        when(industryRepository.save(employer)).thenThrow(new NullPointerException(""));

        // Then
        employer.setName(null);
        assertThrows(NullPointerException.class, ()->{utilityService.saveIndustry(employer);});
        verify(industryRepository, times(2)).save(employer);
    }

    @Test
    public void shouldDeleteIndustry() {
        // Given
        Industry employer = new Industry("Financial Services","Internet");

        // When
        when(industryRepository.findById(1L)).thenThrow(new NullPointerException());

        // Then
        industryRepository.deleteById(1L);
        assertThrows(NullPointerException.class, ()->{utilityService.getIndustry(1L);});
        verify(industryRepository, times(1)).deleteById(1L);
    }

    @Test
    public void shouldDeleteIndustryThrowsException() {
        // Given
        Industry employer = new Industry("Financial Services","Internet");

        // When
        when(industryRepository.findById(1L)).thenThrow(new NotFoundException("Did not find employer id: 1"));

        // Then
        Exception exception = assertThrows(NotFoundException.class, ()->{utilityService.deleteIndustry(1L);});
        assertEquals("Did not find employer id: 1", exception.getMessage());
    }
}
