package com.yvesprojects.gestaoconvidados.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yvesprojects.gestaoconvidados.models.TypeGuest;
import com.yvesprojects.gestaoconvidados.models.User;
import com.yvesprojects.gestaoconvidados.repositories.TypeGuestRepository;

@Service
public class TypeGuestService {
	
	@Autowired
	private TypeGuestRepository typeGuestRepository;
	
	@Autowired
	private UserService userService;
	
	public TypeGuest findById(Long id) {
		Optional<TypeGuest> type = this.typeGuestRepository.findById(id);
		return type.orElseThrow( () -> new RuntimeException(
					"Tipo de convidado não encontrado! id: " + id + " tipo: " + TypeGuest.class.getName() + "."
				));
	}
	
	public List<TypeGuest> findAllByUserId(Long userId) {
		List<TypeGuest> types = this.typeGuestRepository.findByUserId(userId);
		return types;
	}
	
	@Transactional 
	public TypeGuest create(TypeGuest obj) {
		User user = this.userService.findById(obj.getUser().getId());
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
			throw new RuntimeException("Não é possível excluir pois há entidades relacionadas!");
		}
	}
}
