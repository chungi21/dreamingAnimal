package com.project.dreamingAnimal.config.oauth;

import com.project.dreamingAnimal.config.auth.PrincipalDetails;
import com.project.dreamingAnimal.config.oauth.provider.*;
import com.project.dreamingAnimal.dto.UserDto;
import com.project.dreamingAnimal.entity.UserEntity;
import com.project.dreamingAnimal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("-x oauth 로그인 시 받아오는 정보 확인 -userRequest : "+userRequest);

        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 회원가입을 강제로 진행
        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            oAuth2UserInfo = new NaverUserInfo((Map) oAuth2User.getAttributes().get("response"));
        }else{
            System.out.println("우리는 구글, 네이버만 지원");
        }

        String provider = oAuth2UserInfo.getProvider(); // google
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider+"_"+providerId; //
        String email = oAuth2UserInfo.getEmail();
        String role = "ROLE_USER";

        UserEntity userEntity = userRepository.findByUsername(username);

        if(userEntity==null){
            userEntity = UserEntity.builder()
                    .username(username)
                    .nickname(username)
                    .password("")
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(userEntity);
        }

        return new PrincipalDetails(UserDto.toUserDto(userEntity),oAuth2User.getAttributes());
    }
}
