package com.comulynx.wallet.rest.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.comulynx.wallet.rest.api.service.WebUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comulynx.wallet.rest.api.exception.ResourceNotFoundException;
import com.comulynx.wallet.rest.api.model.Webuser;
import com.comulynx.wallet.rest.api.repository.WebuserRepository;
import com.comulynx.wallet.rest.api.util.AppUtils;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(AppUtils.BASE_URL+"/webusers")
public class WebuserController {

	private final WebuserRepository webuserRepository;
	private final WebUserService webUserService;

	public WebuserController(WebuserRepository webuserRepository, WebUserService webUserService){
		this.webuserRepository = webuserRepository;
		this.webUserService = webUserService;
	}
	@GetMapping("/")
	public List<Webuser> getAllWebusers() {
		return webuserRepository.findAll();
	}

	@GetMapping("/{employeeId}")
	public ResponseEntity<Webuser> getWebuserByEmployeeId(@PathVariable(value = "employeeId") String employeeId)
			throws ResourceNotFoundException {
		Webuser webuser = webuserRepository.findByEmployeeId(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Webuser not found for this id :: " + employeeId));
		return ResponseEntity.ok().body(webuser);
	}

	@PostMapping("/create")
	public ResponseEntity<?> createWebuser(@RequestBody Webuser webuser) {
		try {
			// TODO : Add logic to check if Webuser with provided username, or
			// email, or employeeId, or customerId exists.
			// If exists, throw a Webuser with [?] exists Exception.
			//  /** Handled in service layer **/

			return ResponseEntity.ok().body(webUserService.create(webuser));
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PutMapping("/{employeeId}")
	public ResponseEntity<Webuser> updateWebuser(@PathVariable(value = "employeeId") String employeeId,
			@RequestBody Webuser webuserDetails) throws ResourceNotFoundException {
		Webuser webuser = webuserRepository.findByEmployeeId(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Webuser not found for this id :: " + employeeId));

		webuser.setEmail(webuserDetails.getEmail());
		webuser.setLastName(webuserDetails.getLastName());
		webuser.setFirstName(webuserDetails.getFirstName());
		final Webuser updatedWebuser = webuserRepository.save(webuser);
		return ResponseEntity.ok(updatedWebuser);
	}

	@DeleteMapping("/{employeeId}")
	public Map<String, Boolean> deleteWebuser(@PathVariable(value = "employeeId") String employeeId)
			throws ResourceNotFoundException {
		Webuser webuser = webuserRepository.findByEmployeeId(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Webuser not found for this id :: " + employeeId));

		webuserRepository.delete(webuser);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
