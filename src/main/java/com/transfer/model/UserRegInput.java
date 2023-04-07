package com.transfer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false) //lombok
public class UserRegInput implements Serializable {
    private String        kakaoId       ;
    private String        uuid          ;
    private String        custNm       ;
    private Long          phone     =0L;
}
