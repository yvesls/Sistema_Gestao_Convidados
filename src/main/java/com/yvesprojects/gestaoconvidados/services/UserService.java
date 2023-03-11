package com.yvesprojects.gestaoconvidados.services;

import java.util.Optional;

import javax.management.RuntimeErrorException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yvesprojects.gestaoconvidados.models.User;
import com.yvesprojects.gestaoconvidados.repositories.GuestRepository;
import com.yvesprojects.gestaoconvidados.repositories.TypeGuestRepository;
import com.yvesprojects.gestaoconvidados.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GuestRepository guestRepository;
	
	@Autowired
	private TypeGuestRepository typeGuestRepository;
	
	public User findById(Long id) {
		Optional<User> user = this.userRepository.findById(id);
		return user.orElseThrow( () -> new RuntimeException(
					"Usuário não encontrado! id: " + id + " tipo: " + User.class.getName() + "."
				));
	}
	
	public User findByUserName(String name) {
		Optional<User> user = this.userRepository.findByUser_Name(name);
		return user.orElseThrow( () -> new RuntimeException(
				"Usuário não encontrado! name: " + name + " tipo: " + User.class.getName() + "."
			));
	}
	
	@Transactional // ou faz tudo ou faz nada (só registra se todos os campos estiverem preenchidos)
	public User create(User obj) {
		obj.setUserId(null);
		obj = this.userRepository.save(obj);
		return obj;
	}
	
	@Transactional // ou faz tudo ou faz nada (só registra se todos os campos estiverem preenchidos)
	public User update(User obj) {
		User newObj = findById(obj.getUserId());
		newObj.setPassword(obj.getPassword());
		return this.userRepository.save(newObj);
	}
	
	@Transactional
	public void delete(Long id) {
		findById(id);
		try {
			this.userRepository.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException("Não é possível excluir pois há entidades relacionadas!");
		}
	}
}
