package com.project.dreamingAnimal.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class ResponseDto<T> {
    private int status; // 상태
    private T data; // 데이터
    private String message; // 메세지
    private String redirectUrl; // URL

    public ResponseDto(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public ResponseDto(int status, T data, String message, String redirectUrl) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.redirectUrl = redirectUrl;
    }

}
