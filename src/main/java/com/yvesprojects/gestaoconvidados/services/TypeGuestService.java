package com.yvesprojects.gestaoconvidados.services;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yvesprojects.gestaoconvidados.exceptions.AuthorizationException;
import com.yvesprojects.gestaoconvidados.exceptions.DataBindingViolationException;
import com.yvesprojects.gestaoconvidados.exceptions.ObjectNotFoundException;
import com.yvesprojects.gestaoconvidados.models.TypeGuest;
import com.yvesprojects.gestaoconvidados.models.User;
import com.yvesprojects.gestaoconvidados.models.enums.ProfileEnum;
import com.yvesprojects.gestaoconvidados.models.projection.TypeGuestProjection;
import com.yvesprojects.gestaoconvidados.repositories.TypeGuestRepository;
import com.yvesprojects.gestaoconvidados.security.UserSpringSecurity;

@Service
public class TypeGuestService {
	
	@Autowired
	private TypeGuestRepository typeGuestRepository;
	
	@Autowired
	private UserService userService;
	
	public TypeGuest findById(Long id) {
		TypeGuest typeGuest = this.typeGuestRepository.findById(id).orElseThrow( () -> new ObjectNotFoundException(
					"Tipo de convidado não encontrado! id: " + id + " tipo: " + TypeGuest.class.getName() + "."
				));
		UserSpringSecurity userSpringSecurity = userService.authenticated();
		if(!Objects.nonNull(userSpringSecurity) || !userSpringSecurity.hasRole(ProfileEnum.ADMIN) && !userHasTypeGuest(userSpringSecurity, typeGuest))
			throw new AuthorizationException("Acesso negado!");
		return typeGuest;
	}
	
	public List<TypeGuestProjection> findAllByUser() {
		UserSpringSecurity userSpringSecurity = userService.authenticated();
		if(!Objects.nonNull(userSpringSecurity))
			throw new AuthorizationException("Acesso negado!");
		List<TypeGuestProjection> types = this.typeGuestRepository.findByUserId(userSpringSecurity.getId());
		return types;
	}
	
	@Transactional 
	public TypeGuest create(TypeGuest obj) {
		UserSpringSecurity userSpringSecurity = userService.authenticated();
		if(!Objects.nonNull(userSpringSecurity))
			throw new AuthorizationException("Acesso negado!");
		User user = this.userService.findById(userSpringSecurity.getId());
		obj.setTypeId(null);
		obj.setUser(user);
		return this.typeGuestRepository.save(obj);
	}
	
	@Transactional 
	public TypeGuest update(TypeGuest obj) {
		TypeGuest newObj = findById(obj.getTypeId());
		newObj.setTypeDescription(obj.getTypeDescription());
		return this.typeGuestRepository.save(newObj);
	}
	
	public void delete(Long id) {
		findById(id);
		try {
			this.typeGuestRepository.deleteById(id);
		} catch (Exception e) {
			throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas!");
		}
	}
	
	private Boolean userHasTypeGuest(UserSpringSecurity userSpringSecurity, TypeGuest typeGuest) {
		return typeGuest.getUser().getId().equals(userSpringSecurity.getId());
	}
}
