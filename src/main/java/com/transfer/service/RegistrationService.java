package com.transfer.service;

import com.transfer.dao.DaoApiMsgHis;
import com.transfer.dao.DaoCustBal;
import com.transfer.dao.DaoCustBase;
import com.transfer.dao.DaoTrnfHis;
import com.transfer.entity.ApiMsgHis;
import com.transfer.entity.CustBal;
import com.transfer.entity.CustBase;
import com.transfer.entity.TrnfHis;
import com.transfer.model.*;
import com.transfer.module.ExtKakaoApiNotifInput;
import com.transfer.util.RestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    @Autowired public DaoTrnfHis daoTrnfHis;
    @Autowired public DaoCustBase daoCustBase;
    @Autowired public DaoCustBal daoCustBal;
    @Autowired public DaoApiMsgHis daoApiMsgHis;
    private final RestTemplate restTemplate;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public UserRegOutput userReg(UserRegInput input) throws URISyntaxException {
        UserRegOutput output = new UserRegOutput();
        Long newAcctNo = daoCustBase.getMaxAcctNo() +1L;

        Long refNo =  insertApiMsgHis("userReg", newAcctNo, LocalDateTime.now(), input.toString());

        CustBase custBase = new CustBase();
        custBase.setAcctNo(newAcctNo);
        custBase.setCustNm(input.getCustNm());
        custBase.setUuid(input.getUuid());
        custBase.setSts("0");
        custBase.setPhone(input.getPhone());
        custBase.setKakaoId(input.getKakaoId());

        output.setDateTime(LocalDateTime.now());
        try {
            daoCustBase.save(custBase);
            output.setStatus    ("00");
        } catch (Exception E){
            output.setStatus    ("ER");
            output.setDesc      ("Balance can't be found");
        }
        return output;
    }



    //insert API msg and create ref no
    public Long insertApiMsgHis (String apiName, Long acctNo, LocalDateTime dateTime, String msg){
        //count data in Api Msg His
        List<ApiMsgHis> apiMsgHisList = daoApiMsgHis.findAll();

        ApiMsgHis apiMsgHis = new ApiMsgHis();
        apiMsgHis.setDateTime   (dateTime);
        apiMsgHis.setAcctNo     (acctNo);
        apiMsgHis.setApiName    (apiName);
        apiMsgHis.setRefNo      (Long.valueOf(apiMsgHisList.size()+1));
        apiMsgHis.setMsg        (msg);


        daoApiMsgHis.save(apiMsgHis);
        return Long.valueOf(apiMsgHisList.size()+1);
    }
}
