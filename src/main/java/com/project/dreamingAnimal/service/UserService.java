package com.project.dreamingAnimal.service;

import com.project.dreamingAnimal.config.auth.PrincipalDetails;
import com.project.dreamingAnimal.dto.UserDto;
import com.project.dreamingAnimal.entity.UserEntity;
import com.project.dreamingAnimal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    // 회원가입
    @Transactional
    public void save(UserDto userDto){
        // 비밀번호 암호화
        String rawPW = userDto.getPassword();
        String encPW = bCryptPasswordEncoder.encode(rawPW);
        userDto.setPassword(encPW);

        // 등급 설정
        userDto.setRole("ROLE_USER");

        UserEntity userEntity = UserEntity.toUserEntity(userDto);
        userRepository.save(userEntity);
    }

    // 회원가입 시 id 중복 체크
    @Transactional
    public String idCheck(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        if(userEntity!=null){
            return null;
        }else{
            return "ok";
        }
    }

    // 회원가입 시 email 중복 체크
    @Transactional
    public String emailCheck(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity!=null){ // 조회결과가 있다 -> 사용할 수 없다.
            return null;
        }else{ // 조회결과가 없다 -> 사용할 수 있다.
            return "ok";
        }
    }

    // 회원가입 시 nickname 중복 체크
    @Transactional
    public String nicknameCheck(String nickname) {
        UserEntity userEntity = userRepository.findByNickname(nickname);

        if(userEntity!=null){ // 조회결과가 있다 -> 사용할 수 없다.
            return null;
        }else{ // 조회결과가 없다 -> 사용할 수 있다.
            return "ok";
        }
    }

    // 회원 정보 수정
    @Transactional
    public void update(UserDto userDto){

        // Provider 와 ProviderId 값이 없이 온 경우 DB에 공백으로 들어가므로 null값으로 다시 만들어줌
        if (userDto.getProvider() != null && userDto.getProvider().trim().isEmpty()) {
            userDto.setProvider(null);
            userDto.setProviderId(null);
        }

        // 기존 정보 가져오기 - 암호를 공백으로 했을시 기존 비밀번호와 동일하게 하기 위함
        Optional<UserEntity> findUser = userRepository.findById(userDto.getId());

        if (!findUser.isPresent()) { // 값이 없으면 예외
            throw new IllegalArgumentException("Invalid user ID");
        }

        UserEntity userEntity = findUser.get();

        if(userDto.getPassword() == null || userDto.getPassword().isEmpty()){ // 비밀번호가 공백일 경우
            userDto.setPassword(userEntity.getPassword());
        }else{ // 비밀번호가 공백이 아닐 경우
            String rawPW = userDto.getPassword();
            String encPW = bCryptPasswordEncoder.encode(rawPW);
            userDto.setPassword(encPW);
        }

        // 수정된 정보 저장하기
        UserEntity userUpdateEntity = UserEntity.toUserUpdateEntity(userDto);

        updateUserDetails(userDto);

        userRepository.save(userUpdateEntity);

    }


    // 회원정보 수정 시 Authentication 객체 정보도 수정
    public void updateUserDetails(UserDto userDto) {
        // 현재 Authentication 객체 얻기
        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();

        // 기존 사용자 정보 가져오기
        UserDetails originalUserDetails = (UserDetails) currentAuthentication.getPrincipal();

        // 수정된 정보로 UserDetails 객체 생성
        UserDetails updatedUserDetails = new PrincipalDetails(userDto);

        // 수정된 UserDetails로 새로운 Authentication 객체 생성
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                updatedUserDetails,
                currentAuthentication.getCredentials(),
                updatedUserDetails.getAuthorities()
        );

        // SecurityContextHolder에 새로운 Authentication 설정
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
    }

    public void updateRole(String username){
        UserEntity userEntity = userRepository.findByUsername(username);
        userEntity.setRole("ROLE_SHELTER");
        userRepository.save(userEntity);
    }


}
