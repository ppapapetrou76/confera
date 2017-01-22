package com.softconf.confera.common.locations;

import com.softconf.confera.CheckedTransactional;
import com.softconf.confera.Constants;
import com.softconf.confera.common.model.Country;
import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "country")
@RestController
@RequestMapping(Constants.URI_API + "/common/countries")
@CheckedTransactional
public class CountryController {

  @Inject
  private CountryService countryService;

  @ApiOperation("Returns list of all countries")
  @RequestMapping(method = RequestMethod.GET)
  public List<Country> listCountries() {
    return countryService.listCountries();
  }
}
