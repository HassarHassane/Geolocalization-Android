package ma.hassar.demo.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("amis")
public class AmiController {
	@Autowired
    private AmiRepository amiRepository;
   
    @Autowired
    private UserRepository userRepository;
   
    @PostMapping("/save")
    public void save(@RequestBody Ami a){
        Ami ami = amiRepository.findAmi(a.getId().getUser1Id(), a.getId().getUser2Id());
        if(ami != null) {
            ami.setDate(a.getDate());
            ami.setEtat(a.getEtat());
            amiRepository.save(ami);
        }else {
            amiRepository.save(a);
        }
    }
   
    @GetMapping("/findAmis/{imei}")
    public List<User> findAmis(@PathVariable (required = true) String imei){
        User u = userRepository.findUserByImei(imei);
        List<Ami> amis = amiRepository.findAmis(u.getId());
        List<User> friends = new ArrayList<User>();
        for(int i = 0;i<amis.size();i++) {
            if(u.getId() == amis.get(i).getId().getUser1Id()) {
                friends.add(userRepository.findById(amis.get(i).getId().getUser2Id()));
            }else {
                friends.add(userRepository.findById(amis.get(i).getId().getUser1Id()));
            }
        }
        return friends;
    }
   
    @GetMapping("/findInvitations/{imei}")
    public List<User> findInvitations(@PathVariable (required = true) String imei){
        User u = userRepository.findUserByImei(imei);
        List<Ami> amis = amiRepository.findInvitations(u.getId());
        List<User> invitations = new ArrayList<User>();
        for(int i = 0;i<amis.size();i++) {
            if(u.getId() == amis.get(i).getId().getUser1Id()) {
                invitations.add(userRepository.findById(amis.get(i).getId().getUser2Id()));
            }else {
                invitations.add(userRepository.findById(amis.get(i).getId().getUser1Id()));
            }
        }
        return invitations;
    }
   
    @GetMapping("/findAmi/{id1}/{id2}")
    public Ami findAmi(@PathVariable (required = true) int id1,@PathVariable (required = true) int id2){
        return amiRepository.findAmi(id1, id2);
    }
   
	
	
	

}
