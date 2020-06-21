package com.unicorn.studio.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.unicorn.studio.dao.ClubRepository;
import com.unicorn.studio.dao.CompanyRepository;
import com.unicorn.studio.dao.FundingRepository;
import com.unicorn.studio.dao.InvestorRepository;
import com.unicorn.studio.dao.UserRepository;
import com.unicorn.studio.entity.Club;
import com.unicorn.studio.entity.Company;
import com.unicorn.studio.entity.Funding;
import com.unicorn.studio.entity.Investor;
import com.unicorn.studio.entity.User;

@Service
public class UnicornServiceImp implements UnicornService {
	// Inject DAO
	@Autowired
	private ClubRepository clubRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private FundingRepository fundingRepository;
	
	@Autowired
	private InvestorRepository investorRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	
	
	@Autowired
	public UnicornServiceImp(ClubRepository clubRepository, CompanyRepository companyRepository,
			FundingRepository fundingRepository, InvestorRepository investorRepository, UserRepository userRepository) {
		this.clubRepository = clubRepository;
		this.companyRepository = companyRepository;
		this.fundingRepository = fundingRepository;
		this.investorRepository = investorRepository;
		this.userRepository = userRepository;
	}
	
	@Override
	@Transactional
	public List<Club> getClubs() {
		return clubRepository.findAll();
	}

	@Override
	@Transactional
	public void saveClub(Club club) {
		clubRepository.save(club);
	}

	@Override
	@Transactional
	public Club getClub(int id) {
		Optional<Club> result = clubRepository.findById(id);
		Club club = null;
		if (result.isPresent()) {
			club = result.get();
		}
		else {
			throw new RuntimeException("Did not find club id: " + id);
		}
		return club;
	}

	@Override
	@Transactional
	public void deleteClub(int id) {
		clubRepository.deleteById(id);
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
	public Company getCompany(int id) {
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
	public void deleteCompany(int id) {
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
	public Funding getFunding(int id) {
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
	public void deleteFunding(int id) {
		fundingRepository.deleteById(id);
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
	public Investor getInvestor(int id) {
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
	public void deleteInvestor(int id) {
		investorRepository.deleteById(id);
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
