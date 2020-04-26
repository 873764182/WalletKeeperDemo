package com.example.walletkeeper.packages;

/**
 * 心愿记录的数据库实体
 */
public class WishMapper {

    public Integer id;

    public String insTime;

    public Integer money;

    public String comment;

    public WishMapper() {
    }

    public WishMapper(Integer id, String insTime, Integer money, String comment) {
        this.id = id;
        this.insTime = insTime;
        this.money = money;
        this.comment = comment;
    }
}
