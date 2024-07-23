package com.project.dreamingAnimal.config.auth;


import com.project.dreamingAnimal.dto.UserDto;
import com.project.dreamingAnimal.entity.UserEntity;
import com.project.dreamingAnimal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        UserDto userDto = UserDto.toUserDto(userEntity);
        if(userEntity!=null){
            return new PrincipalDetails(userDto);
        }
        return null;
    }


}
