package io.ashimjk.validator;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Account {

    private String name;
    private BigDecimal amount;
    private String currency;

}
