package com.yvesprojects.gestaoconvidados.services;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.yvesprojects.gestaoconvidados.exceptions.AuthorizationException;
import com.yvesprojects.gestaoconvidados.exceptions.DataBindingViolationException;
import com.yvesprojects.gestaoconvidados.exceptions.ObjectNotFoundException;
import com.yvesprojects.gestaoconvidados.models.User;
import com.yvesprojects.gestaoconvidados.models.enums.ProfileEnum;
import com.yvesprojects.gestaoconvidados.repositories.UserRepository;
import com.yvesprojects.gestaoconvidados.security.UserSpringSecurity;

@Service
public class UserService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	public User findById(Long id) {
		UserSpringSecurity userSpringSecurity = this.authenticated();
		if(!Objects.nonNull(userSpringSecurity) || !userSpringSecurity.hasRole(ProfileEnum.ADMIN) && !id.equals(userSpringSecurity.getId()))
			throw new AuthorizationException("Acesso negado!");
		
		Optional<User> user = this.userRepository.findById(id);
		return user.orElseThrow( () -> new ObjectNotFoundException(
					"Usuário não encontrado! id: " + id + " tipo: " + User.class.getName() + "."
				));
	}
	
	@Transactional // ou faz tudo ou faz nada (só registra se todos os campos estiverem preenchidos)
	public User create(User obj) {
		obj.setId(null);
		obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword())); // criptografa a senha
		 obj.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
		return this.userRepository.save(obj);
	}
	
	@Transactional // ou faz tudo ou faz nada (só registra se todos os campos estiverem preenchidos)
	public User update(User obj) {
		User newObj = findById(obj.getId());
		newObj.setPassword(obj.getPassword());
		newObj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
		return this.userRepository.save(newObj);
	}
	
	public void delete(Long id) {
		findById(id);
		try {
			this.userRepository.deleteById(id);
		} catch (Exception e) {
			throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas!");
		}
	}
	
	public UserSpringSecurity authenticated() {
		try {
			return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}catch(Exception e) {
			return null;
		}
	}
}
