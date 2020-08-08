package com.unicorn.studio.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.unicorn.studio.dao.*;
import com.unicorn.studio.entity.*;
import com.unicorn.studio.exception.UserExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UnicornServiceImp implements UnicornService {
	// Inject DAO
	@Autowired
	private IndustryRepository industryRepository;
	
	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private CompanyMetricsRepository companyMetricsRepository;
	
	@Autowired
	private FundingRepository fundingRepository;

	@Autowired
	private InvestmentRepository investmentRepository;
	
	@Autowired
	private InvestorRepository investorRepository;

	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;


	public UnicornServiceImp(IndustryRepository industryRepository, CompanyRepository companyRepository, CompanyMetricsRepository companyMetricsRepository, FundingRepository fundingRepository, InvestmentRepository investmentRepository, InvestorRepository investorRepository, TeamRepository teamRepository, UserRepository userRepository) {
		this.industryRepository = industryRepository;
		this.companyRepository = companyRepository;
		this.companyMetricsRepository = companyMetricsRepository;
		this.fundingRepository = fundingRepository;
		this.investmentRepository = investmentRepository;
		this.investorRepository = investorRepository;
		this.teamRepository = teamRepository;
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public List<Industry> getIndustries() {
		return industryRepository.findAll();
	}

	@Override
	@Transactional
	public void saveIndustry(Industry industry) {
		industryRepository.save(industry);
	}

	@Override
	@Transactional
	public Industry getIndustry(long id) {
		Optional<Industry> result = industryRepository.findById(id);
		Industry industry = null;
		if (result.isPresent()) {
			industry = result.get();
		}
		else {
			throw new RuntimeException("Did not find club id: " + id);
		}
		return industry;
	}

	@Override
	@Transactional
	public void deleteIndustry(long id) {
		industryRepository.deleteById(id);
	}
	
	@Override
	@Transactional
	public List<Company> getCompanies() {
		return companyRepository.findAll();
	}

	@Override
	@Transactional
	public void saveCompany(Company company) {
		companyRepository.save(company);
	}

	@Override
	@Transactional
	public Company getCompany(long id) {
		Optional<Company> result = companyRepository.findById(id);
		Company company = null;
		if (result.isPresent()) {
			company = result.get();
		}
		else {
			throw new RuntimeException("Did not find company id: " + id);
		}
		return company;
	}

	@Override
	@Transactional
	public void deleteCompany(long id) {
		companyRepository.deleteById(id);
	}

	@Override
	@Transactional
	public List<CompanyMetrics> getCompanyMetrics() {
		return companyMetricsRepository.findAll();
	}

	@Override
	@Transactional
	public void saveCompanyMetrics(CompanyMetrics companyMetrics) {
		companyMetricsRepository.save(companyMetrics);
	}

	@Override
	@Transactional
	public CompanyMetrics getCompanyMetrics(long id) {
		Optional<CompanyMetrics> result = companyMetricsRepository.findById(id);
		CompanyMetrics companyMetrics = null;
		if (result.isPresent()) {
			companyMetrics = result.get();
		}
		else {
			throw new RuntimeException("Did not find companyMetrics id: " + id);
		}
		return companyMetrics;
	}

	@Override
	@Transactional
	public void deleteCompanyMetrics(long id) {
		companyRepository.deleteById(id);
	}
	
	@Override
	@Transactional
	public List<Funding> getFundings() {
		return fundingRepository.findAll();
	}

	@Override
	@Transactional
	public void saveFunding(Funding funding) {
		fundingRepository.save(funding);
	}

	@Override
	@Transactional
	public Funding getFunding(long id) {
		Optional<Funding> result = fundingRepository.findById(id);
		Funding funding = null;
		if (result.isPresent()) {
			funding = result.get();
		}
		else {
			throw new RuntimeException("Did not find funding id: " + id);
		}
		return funding;
	}

	@Override
	@Transactional
	public void deleteFunding(long id) {
		fundingRepository.deleteById(id);
	}

	@Override
	@Transactional
	public List<Investment> getInvestments() {
		return investmentRepository.findAll();
	}

	@Override
	@Transactional
	public void saveInvestment(Investment investment) {
		investmentRepository.save(investment);
	}

	@Override
	@Transactional
	public Investment getInvestment(long id) {
		Optional<Investment> result = investmentRepository.findById(id);
		Investment investment = null;
		if (result.isPresent()) {
			investment = result.get();
		}
		else {
			throw new RuntimeException("Did not find investment id: " + id);
		}
		return investment;
	}

	@Override
	@Transactional
	public void deleteInvestment(long id) {
		companyRepository.deleteById(id);
	}
	
	@Override
	@Transactional
	public List<Investor> getInvestors() {
		return investorRepository.findAll();
	}

	@Override
	@Transactional
	public void saveInvestor(Investor investor) {
		investorRepository.save(investor);
	}

	@Override
	@Transactional
	public Investor getInvestor(long id) {
		Optional<Investor> result = investorRepository.findById(id);
		Investor investor = null;
		if (result.isPresent()) {
			investor = result.get();
		}
		else {
			throw new RuntimeException("Did not find investor id: " + id);
		}
		return investor;
	}

	@Override
	@Transactional
	public void deleteInvestor(long id) {
		investorRepository.deleteById(id);
	}

	@Override
	@Transactional
	public List<Team> getTeams() {
		return teamRepository.findAll();
	}

	@Override
	@Transactional
	public void saveTeam(Team team) {
		teamRepository.save(team);
	}

	@Override
	@Transactional
	public Team getTeam(long id) {
		Optional<Team> result = teamRepository.findById(id);
		Team team = null;
		if (result.isPresent()) {
			team = result.get();
		}
		else {
			throw new RuntimeException("Did not find team id: " + id);
		}
		return team;
	}

	@Override
	@Transactional
	public void deleteTeam(long id) {
		teamRepository.deleteById(id);
	}

	@Override
	@Transactional
	public List<User> getUsers() {
		List<User> user = userRepository.findAll();
		return userRepository.findAll();
	}

	@Override
	@Transactional
	public void saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setUserRole(user.getUserRole());
		User isUser = userRepository.findUserByEmail(user.getEmail());
		if (isUser != null) {
			throw new UserExistsException("Account with this email id already exists");
		}
		userRepository.save(user);
	}

	@Override
	@Transactional
	public User getUser(long id) {
		Optional<User> result = userRepository.findById(id);
		User user = null;
		if (result.isPresent()) {
			user = result.get();
		}
		else {
			throw new RuntimeException("Did not find user id: " + id);
		}
		return user;
	}

	@Override
	@Transactional
	public void deleteUser(long id) {
		userRepository.deleteById(id);
	}

	@Override
	@Transactional
	public User getUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}

}
