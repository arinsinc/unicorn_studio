package com.unicorn.studio.rest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unicorn.studio.entity.Club;
import com.unicorn.studio.entity.Company;
import com.unicorn.studio.entity.Funding;
import com.unicorn.studio.entity.Investor;
import com.unicorn.studio.entity.User;
import com.unicorn.studio.service.UnicornService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UnicornStudioRestController {
	private UnicornService unicornService;
	
	@GetMapping("/clubs")
	public List<Club> getClubs() {
		return unicornService.getClubs();
	}
	
	@GetMapping("/clubs/{clubId}")
	public Club getClub(@PathVariable int clubId) {
		Club club = unicornService.getClub(clubId);
		if (club == null) {
			throw new NotFoundException("Club not found with ID:" + clubId);
		}
		return club;
	}
	
	@PostMapping("/clubs")
	public Club addClub(@RequestBody Club club) {
		club.setId(0);
		unicornService.saveClub(club);
		return club;
	}
	
	@PutMapping("/clubs")
	public Club updateClub(@RequestBody Club club) {
		unicornService.saveClub(club);
		return club;
	}
	
	@DeleteMapping("/clubs/{clubId}")
	public String deleteClub(@PathVariable int clubId) {
		Club club = unicornService.getClub(clubId);
		if (club == null) {
			throw new NotFoundException("Club not found with ID:" + clubId);
		}
		unicornService.deleteClub(clubId);
		return "Club deleted successfully for Id:" + clubId;
	}
	
	@GetMapping("/companies")
	public List<Company> getCompanies() {
		return unicornService.getCompanies();
	}
	
	@GetMapping("/companies/{companyId}")
	public Company getCompany(@PathVariable int companyId) {
		Company company = unicornService.getCompany(companyId);
		if (company == null) {
			throw new NotFoundException("Company not found with ID:" + companyId);
		}
		return company;
	}
	
	@PostMapping("/companies")
	public Company addCompany(@RequestBody Company company) {
		company.setId(0);
		unicornService.saveCompany(company);
		return company;
	}
	
	@PutMapping("/companies")
	public Company updateCompany(@RequestBody Company company) {
		unicornService.saveCompany(company);
		return company;
	}
	
	@DeleteMapping("/companies/{companyId}")
	public String deleteCompany(@PathVariable int companyId) {
		Company company = unicornService.getCompany(companyId);
		if (company == null) {
			throw new NotFoundException("Company not found with ID:" + companyId);
		}
		unicornService.deleteCompany(companyId);
		return "Company deleted successfully for Id:" + companyId;
	}
	
	@GetMapping("/fundings")
	public List<Funding> getFundings() {
		return unicornService.getFundings();
	}
	
	@GetMapping("/fundings/{fundingId}")
	public Funding getFunding(@PathVariable int fundingId) {
		Funding funding = unicornService.getFunding(fundingId);
		if (funding == null) {
			throw new NotFoundException("Funding not found with ID:" + fundingId);
		}
		return funding;
	}
	
	@PostMapping("/fundings")
	public Funding addFunding(@RequestBody Funding funding) {
		funding.setId(0);
		unicornService.saveFunding(funding);
		return funding;
	}
	
	@PutMapping("/fundings")
	public Funding updateFunding(@RequestBody Funding funding) {
		unicornService.saveFunding(funding);
		return funding;
	}
	
	@DeleteMapping("/fundings/{fundingId}")
	public String deleteFunding(@PathVariable int fundingId) {
		Funding funding = unicornService.getFunding(fundingId);
		if (funding == null) {
			throw new NotFoundException("Funding not found with ID:" + fundingId);
		}
		unicornService.deleteFunding(fundingId);
		return "Funding deleted successfully for Id:" + fundingId;
	}
	
	@GetMapping("/investors")
	public List<Investor> getInvestors() {
		return unicornService.getInvestors();
	}
	
	@GetMapping("/investors/{investorId}")
	public Investor getInvestor(@PathVariable int investorId) {
		Investor investor = unicornService.getInvestor(investorId);
		if (investor == null) {
			throw new NotFoundException("Investor not found with ID:" + investorId);
		}
		return investor;
	}
	
	@PostMapping("/investors")
	public Investor addInvestor(@RequestBody Investor investor) {
		investor.setId(0);
		unicornService.saveInvestor(investor);
		return investor;
	}
	
	@PutMapping("/investors")
	public Investor updateInvestor(@RequestBody Investor investor) {
		unicornService.saveInvestor(investor);
		return investor;
	}
	
	@DeleteMapping("/investors/{investorId}")
	public String deleteInvestor(@PathVariable int investorId) {
		Investor investor = unicornService.getInvestor(investorId);
		if (investor == null) {
			throw new NotFoundException("User not found with ID:" + investorId);
		}
		unicornService.deleteInvestor(investorId);
		return "Investor deleted successfully for Id:" + investorId;
	}
	
	@GetMapping("/users")
	public List<User> getUsers() {
		return unicornService.getUsers();
	}
	
	@GetMapping("/users/{userId}")
	public User getUser(@PathVariable int userId) {
		User user = unicornService.getUser(userId);
		if (user == null) {
			throw new NotFoundException("User not found with ID:" + userId);
		}
		return user;
	}
	
	@PostMapping("/users")
	public User addUser(@RequestBody User user) {
		user.setId(0);
		unicornService.saveUser(user);
		return user;
	}
	
	@PutMapping("/users")
	public User updateUser(@RequestBody User user) {
		unicornService.saveUser(user);
		return user;
	}
	
	@DeleteMapping("/users/{userId}")
	public String deleteUser(@PathVariable int userId) {
		User isUser = unicornService.getUser(userId);
		if (isUser == null) {
			throw new NotFoundException("User not found with ID:" + userId);
		}
		unicornService.deleteUser(userId);
		return "User deleted successfully for Id:" + userId;
	}
}
