package com.unicorn.studio.service;

import java.util.List;

import com.unicorn.studio.entity.Club;
import com.unicorn.studio.entity.Company;
import com.unicorn.studio.entity.Funding;
import com.unicorn.studio.entity.Investor;
import com.unicorn.studio.entity.User;

import javax.transaction.Transactional;

public interface UnicornService {
	public List<Club> getClubs();

	public void saveClub(Club club);

	public Club getClub(int id);

	public void deleteClub(int id);


	public List<Company> getCompanies();

	public void saveCompany(Company company);

	public Company getCompany(int id);

	public void deleteCompany(int id);
	
	
	public List<Funding> getFundings();

	public void saveFunding(Funding funding);

	public Funding getFunding(int id);

	public void deleteFunding(int id);

	
	public List<Investor> getInvestors();

	public void saveInvestor(Investor investor);

	public Investor getInvestor(int id);

	public void deleteInvestor(int id);
	
	public List<User> getUsers();

	public void saveUser(User user);

	public User getUser(long id);

	public void deleteUser(long id);

	public User getUserByEmail(String email);
}
