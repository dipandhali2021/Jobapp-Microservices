package com.jobapp.companyms.company;


import java.util.List;


public interface CompanyService {
    List<Company> getAllCompanies();

    boolean updateCompany(Long id, Company updatedCompany);

    void addCompany(Company company);

    boolean deleteCompany(Long id);

    Company getCompanyById(Long id);
}
