package com.unicorn.studio.service;

import java.util.List;

import com.unicorn.studio.entity.*;

public interface UnicornService {
	List<Industry> getIndustries();

	void saveIndustry(Industry industry);

	Industry getIndustry(long id);

	void deleteIndustry(long id);


	List<Company> getCompanies();

	void saveCompany(Company company);

	Company getCompany(long id);

	void deleteCompany(long id);


	List<CompanyMetrics> getCompanyMetrics();

	void saveCompanyMetrics(CompanyMetrics companyMetrics);

	CompanyMetrics getCompanyMetrics(long id);

	void deleteCompanyMetrics(long id);
	
	
	List<Funding> getFundings();

	void saveFunding(Funding funding);

	Funding getFunding(long id);

	void deleteFunding(long id);


	List<Investment> getInvestments();

	void saveInvestment(Investment investment);

	Investment getInvestment(long id);

	void deleteInvestment(long id);

	
	List<Investor> getInvestors();

	void saveInvestor(Investor investor);

	Investor getInvestor(long id);

	void deleteInvestor(long id);


	List<Team> getTeams();

	void saveTeam(Team teams);

	Team getTeam(long id);

	void deleteTeam(long id);

	
	List<User> getUsers();

	void saveUser(User user);

	User getUser(long id);

	void deleteUser(long id);

	User getUserByEmail(String email);


}
