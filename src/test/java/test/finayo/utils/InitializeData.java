package test.unicorn.studio.utils;

import com.unicorn.studio.entity.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InitializeData {
    public static City initializeLocation() {
        Country country = new Country("Netherlands","NL","EU");
        State state = new State("North Holland");
        state.setCountry(country);
        City city = new City("Amsterdam");
        city.setState(state);
        return city;
    }

    public static Industry initializeIndustry() {
        Industry industry = new Industry("Internet",null);
        return industry;
    }

    public static Company initializeCompany() {
        Company company = new Company("Github","Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean m",
                "https://github.com","2008","PRIVATE","500",initializeLocation(), initializeIndustry());
        return company;
    }

    public static CompanyMetrics initializeCompanyMetrics() {
        CompanyMetrics companyMetrics = new CompanyMetrics(1000000,'€',"Jun,2021");
        return companyMetrics;
    }

    public static Equity initializeEquity() {
        Equity equity = new Equity(25.00f, "2021 Q2", null, null);
        return equity;
    }

    public static Funding initializeFunding() {
        Investor investor = initializeInvestor();
        Funding funding = new Funding(1000000,'€',"Series A", LocalDateTime.now(),investor,initializeCompany());
        return funding;
    }

    public static Investor initializeInvestor() {
        List<Industry> industries = new ArrayList<>();
        industries.add(initializeIndustry());
        Investor investor = new Investor("Warren Buffet","warren@buffet.com","Investor","Long time investor",LocalDateTime.now(), industries);
        return investor;
    }

    public static Plan initializePlan() {
        Plan plan = new Plan("Business","DCBS01","$100");
        return plan;
    }

    public static Subscription initializeSubscription() {
        LocalDate start_date = LocalDate.of(2020,04,01);
        LocalDate end_date = LocalDate.of(2020,04,30);
        Subscription subscription = new Subscription("2020-04-01","2020-04-30");
        return subscription;
    }

    public static User initializeUser() {
        User user = new User("Bill Gates","bill@gates.com","asdf123",true,null,null,null,null,null,null,null,null);
        return user;
    }
}
