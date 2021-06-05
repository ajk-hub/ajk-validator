package com.github.ajkhub.sample;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account {

    private String name;
    private BigDecimal amount;
    private String currency;

}
