package ma.hassar.demo.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ma.hassar.demo.beans.Ami;
import ma.hassar.demo.beans.User;
import ma.hassar.demo.repository.AmiRepository;
import ma.hassar.demo.repository.UserRepository;

@RestController
@RequestMapping("users")
public class UserController {
	@Autowired
	private UserRepository repository;
	@Autowired
	private AmiRepository amiRepository;
	@PostMapping("/save")
	public void save(@RequestBody User student){
		repository.save(student);
	}
	@CrossOrigin("*")
	@GetMapping("/all")
	public List<User>findAll(){
		return repository.findAll();
	}
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable (required = true) String id){
		User s = repository.findById(Integer.parseInt(id));
		repository.delete(s);
	}

	@GetMapping(value = "/count")
	public long countStudent() {
		return repository.count();
	}
	@GetMapping(value = "/findUserByTelephone/{telephone}")
	public User findUserByTelephone(@PathVariable(required = true) String telephone) {

		return repository.findUserByTelephone(telephone);
	}
	@GetMapping(value = "/findUserByImei/{imei}")
	public User findUserByImei(@PathVariable(required = true) String imei) {

		return repository.findUserByImei(imei);
	}
	@CrossOrigin("*")
	@GetMapping("/findAmis/{id}")
	public List<User> findAmis(@PathVariable (required = true) String id){
		List<User> users = new ArrayList<User>();
		List<Ami> amis = amiRepository.findAmis(Integer.parseInt(id));
		for(int i = 0;i<amis.size();i++) {
			if(Integer.parseInt(id) == amis.get(i).getId().getUser1Id()) {
				users.add(repository.findById(amis.get(i).getId().getUser2Id()));
			}else {
				users.add(repository.findById(amis.get(i).getId().getUser1Id()));
			}
		}
		return users;
	}
	

}
