package com.yvesprojects.gestaoconvidados.services;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yvesprojects.gestaoconvidados.exceptions.AuthorizationException;
import com.yvesprojects.gestaoconvidados.exceptions.ObjectNotFoundException;
import com.yvesprojects.gestaoconvidados.models.Guest;
import com.yvesprojects.gestaoconvidados.models.TypeGuest;
import com.yvesprojects.gestaoconvidados.models.User;
import com.yvesprojects.gestaoconvidados.models.enums.ProfileEnum;
import com.yvesprojects.gestaoconvidados.models.projection.GuestProjection;
import com.yvesprojects.gestaoconvidados.repositories.GuestRepository;
import com.yvesprojects.gestaoconvidados.security.UserSpringSecurity;

@Service
public class GuestService {
	@Autowired
	private GuestRepository guestRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TypeGuestService typeGuestService;
	
	public Guest findById(Long id) {
		Guest guest = this.guestRepository.findById(id).orElseThrow( () -> new ObjectNotFoundException(
					"Convidado não encontrado! id: " + id + " tipo: " + Guest.class.getName() + "."
				));
		UserSpringSecurity userSpringSecurity = userService.authenticated();
		if(!Objects.nonNull(userSpringSecurity) || !userSpringSecurity.hasRole(ProfileEnum.ADMIN) && !userHasGuest(userSpringSecurity, guest))
			throw new AuthorizationException("Acesso negado!");
		return guest;
	}
	
	public List<GuestProjection> findAllByUser() {
		UserSpringSecurity userSpringSecurity = userService.authenticated();
		if(!Objects.nonNull(userSpringSecurity))
			throw new AuthorizationException("Acesso negado!");
		List<GuestProjection> guests = this.guestRepository.findByUserId(userSpringSecurity.getId());
		return guests;
	}
	
	@Transactional 
	public Guest create(Guest obj) {
		UserSpringSecurity userSpringSecurity = userService.authenticated();
		if(!Objects.nonNull(userSpringSecurity))
			throw new AuthorizationException("Acesso negado!");
		User user = this.userService.findById(userSpringSecurity.getId());
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
		newObj.setGuestName(obj.getGuestName());
		newObj.setPresent(obj.getPresent());
		
		return this.guestRepository.save(newObj);
	}
	
	public void delete(Long id) {
		findById(id);
		this.guestRepository.deleteById(id);
	}
	
	private Boolean userHasGuest(UserSpringSecurity userSpringSecurity, Guest guest) {
		return guest.getUser().getId().equals(userSpringSecurity.getId());
	}
}
