package com.yvesprojects.gestaoconvidados.services;

import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yvesprojects.gestaoconvidados.models.User;
import com.yvesprojects.gestaoconvidados.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User findById(Long id) {
		Optional<User> user = this.userRepository.findById(id);
		return user.orElseThrow( () -> new RuntimeException(
					"Usuário não encontrado! id: " + id + " tipo: " + User.class.getName() + "."
				));
	}
	
	@Transactional // ou faz tudo ou faz nada (só registra se todos os campos estiverem preenchidos)
	public User create(User obj) {
		obj.setId(null);
		return this.userRepository.save(obj);
	}
	
	@Transactional // ou faz tudo ou faz nada (só registra se todos os campos estiverem preenchidos)
	public User update(User obj) {
		User newObj = findById(obj.getId());
		newObj.setPassword(obj.getPassword());
		return this.userRepository.save(newObj);
	}
	
	public void delete(Long id) {
		findById(id);
		try {
			this.userRepository.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException("Não é possível excluir pois há entidades relacionadas!");
		}
	}
}
