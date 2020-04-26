package com.example.walletkeeper.packages;

/**
 * 收入记录的数据库实体
 */
public class IncomeMapper {

    public Integer id;

    public String insTime;

    public String updTime;

    public Integer money;

    public String comment;

    public IncomeMapper() {
    }

    public IncomeMapper(Integer id, String insTime, String updTime, Integer money, String comment) {
        this.id = id;
        this.insTime = insTime;
        this.updTime = updTime;
        this.money = money;
        this.comment = comment;
    }
}
