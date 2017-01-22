package com.softconf.confera.common.locations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softconf.confera.common.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

  Country findByCode(String code);
}
