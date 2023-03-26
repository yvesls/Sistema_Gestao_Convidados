package com.yvesprojects.gestaoconvidados.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.yvesprojects.gestaoconvidados.models.TypeGuest;
import com.yvesprojects.gestaoconvidados.models.projection.TypeGuestProjection;
import com.yvesprojects.gestaoconvidados.services.TypeGuestService;

@RestController
@RequestMapping("/typeguest")
@Validated
public class TypeGuestController {

	@Autowired
	private TypeGuestService typeGuestService;
	
	@GetMapping("/{tgId}")
	public ResponseEntity<TypeGuest> findById(@PathVariable Long tgId) {
		TypeGuest obj = this.typeGuestService.findById(tgId);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<TypeGuestProjection>> findAllByUserId(){
		List<TypeGuestProjection> objs = this.typeGuestService.findAllByUser();
		return ResponseEntity.ok().body(objs);
	}
	
	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody TypeGuest obj) {
		this.typeGuestService.create(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getTypeId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{tgId}")
	public ResponseEntity<Void> update(@Valid @RequestBody TypeGuest obj,@PathVariable Long tgId) {
		obj.setTypeId(tgId);
		this.typeGuestService.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{tgId}")
	public ResponseEntity<Void> delete(@PathVariable("tgId") Long tgId) {
		this.typeGuestService.delete(tgId);
		return ResponseEntity.noContent().build();
	}
}
