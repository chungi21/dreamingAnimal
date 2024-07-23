package com.project.dreamingAnimal.config.auth;

import com.project.dreamingAnimal.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class PrincipalDetails implements UserDetails, OAuth2User {

    private UserDto userDto;
    private Map<String,Object> attributes;

    // 일반 로그인 할 때 사용하는 생성자
    public PrincipalDetails(UserDto userDto){
        this.userDto = userDto;
    }

    // OAuth 로그인 할 때 사용하는 생성자
    public PrincipalDetails(UserDto user,Map<String,Object> attributes){
        this.userDto = user;
        this.attributes = attributes;
    }

    // UserDetails Override
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userDto.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return userDto.getPassword();
    }

    @Override
    public String getUsername() {
        return userDto.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // OAuth2User Override
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }

    public int getId(){
        return userDto.getId();
    }

    public String getEmial(){
        return userDto.getEmail();
    }

    public String getNickname(){
        return userDto.getNickname();
    }

    public String getRole(){
        return userDto.getRole();
    }

    public String getProvider(){
        return userDto.getProvider();
    }

    public String getProviderId(){
        return userDto.getProviderId();
    }


}



















