package com.softconf.confera.common.locations;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.softconf.confera.common.model.Country;

@Service
public class CountryService {

  @Inject
  private CountryRepository countryRepository;

  public List<Country> listCountries() {
    return countryRepository.findAll(new Sort(Direction.ASC, "name"));
  }

}
