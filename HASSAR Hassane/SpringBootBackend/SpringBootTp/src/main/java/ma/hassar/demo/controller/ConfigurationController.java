package ma.hassar.demo.controller;



import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ma.hassar.demo.beans.Configuration;
import ma.hassar.demo.beans.User;
import ma.hassar.demo.repository.ConfigurationRepository;
import ma.hassar.demo.repository.UserRepository;







@RestController
@RequestMapping("configurations")
public class ConfigurationController {
   
   @Autowired
   private ConfigurationRepository configurationRepository;
   
   @Autowired
   private UserRepository userRepository;
   
   @PostMapping("/save")
   public void save(@RequestBody Configuration configuration){
       Configuration config = configurationRepository.findConfigByUserId(configuration.getUser().getId());
       if(config != null) {
           configuration.setId(config.getId());
       }
       configurationRepository.save(configuration);
   }
   
   
   @GetMapping(value = "/findConfigByImei/{imei}")
   public Configuration findConfigByImei(@PathVariable (required = true) String imei){
       User user = userRepository.findUserByImei(imei);
       return configurationRepository.findConfigByUserId(user.getId());
   }
}
