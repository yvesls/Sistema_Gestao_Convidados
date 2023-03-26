package com.yvesprojects.gestaoconvidados.controllers;

import java.net.URI;

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

import com.yvesprojects.gestaoconvidados.models.User;
import com.yvesprojects.gestaoconvidados.models.dto.UserCreateDTO;
import com.yvesprojects.gestaoconvidados.models.dto.UserUpdateDTO;
import com.yvesprojects.gestaoconvidados.services.UserService;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/{userId}")
	public ResponseEntity<User> findById(@PathVariable Long userId) {
		User obj = this.userService.findById(userId);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody UserCreateDTO obj) {
		User user = this.userService.fromDTO(obj);
		User newUser = this.userService.create(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<Void> update(@Valid @RequestBody UserUpdateDTO obj,@PathVariable Long userId) {
		obj.setId(userId);
		User user = this.userService.fromDTO(obj);
		this.userService.update(user);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> delete(@PathVariable("userId") Long userId) {
		this.userService.delete(userId);
		return ResponseEntity.noContent().build();
	}
}
