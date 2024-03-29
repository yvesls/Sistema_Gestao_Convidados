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
import com.yvesprojects.gestaoconvidados.models.Guest;
import com.yvesprojects.gestaoconvidados.models.projection.GuestProjection;
import com.yvesprojects.gestaoconvidados.services.GuestService;

@RestController
@RequestMapping("/guest")
@Validated
public class GuestController {

	@Autowired
	private GuestService guestService;
	
	@GetMapping("/{gId}")
	public ResponseEntity<Guest> findById(@PathVariable Long gId) {
		Guest obj = this.guestService.findById(gId);
		return ResponseEntity.ok().body(obj);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<GuestProjection>> findAllByUser(){
		List<GuestProjection> objs = this.guestService.findAllByUser();
		return ResponseEntity.ok().body(objs);
	}
	
	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody Guest obj) {
		this.guestService.create(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getGuestId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{gId}")
	public ResponseEntity<Void> update(@Valid @RequestBody Guest obj,@PathVariable Long gId) {
		obj.setGuestId(gId);
		this.guestService.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{gId}")
	public ResponseEntity<Void> delete(@PathVariable Long gId) {
		this.guestService.delete(gId);
		return ResponseEntity.noContent().build();
	}
}
