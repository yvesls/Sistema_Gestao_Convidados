package com.yvesprojects.gestaoconvidados.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yvesprojects.gestaoconvidados.models.Guest;
import com.yvesprojects.gestaoconvidados.models.TypeGuest;
import com.yvesprojects.gestaoconvidados.models.User;
import com.yvesprojects.gestaoconvidados.repositories.GuestRepository;

@Service
public class GuestService {
	@Autowired
	private GuestRepository guestRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TypeGuestService typeGuestService;
	
	public Guest findById(Long id) {
		Optional<Guest> type = this.guestRepository.findById(id);
		return type.orElseThrow( () -> new RuntimeException(
					"Convidado não encontrado! id: " + id + " tipo: " + Guest.class.getName() + "."
				));
	}
	
	public List<Guest> findAllByUserId(Long id) {
		List<Guest> guests = this.guestRepository.findByUserId(id);
		return guests;
	}
	
	@Transactional 
	public Guest create(Guest obj) {
		User user = this.userService.findById(obj.getUser().getId());
		TypeGuest typeGuest = this.typeGuestService.findById(obj.getTypeGuest().getTypeId());
		obj.setUser(user);
		obj.setTypeGuest(typeGuest);
		obj.setGuestId(null);
		return this.guestRepository.save(obj);
	}
	
	@Transactional 
	public Guest update(Guest obj) {
		Guest newObj = findById(obj.getGuestId());
		newObj.setTypeGuest(obj.getTypeGuest());
		newObj.setPresent(obj.getPresent());
		return this.guestRepository.save(newObj);
	}
	
	public void delete(Long id) {
		findById(id);
		this.guestRepository.deleteById(id);
	}
}
