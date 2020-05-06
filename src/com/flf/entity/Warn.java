package com.flf.entity;

import java.util.Date;

public class Warn {
	private Integer id;
    private String name;
    private Float propotion;//阈值
    private Float rate;//不合格率
    private String level;//预警界别 黄色 橙色 红色
    private Date dealTime;
    private Integer status;//0 未处理 1 已上报 2已忽略
    private Date createTime;
    private Page page;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public Float getPropotion() {
        return propotion;
    }

    public void setPropotion(Float propotion) {
        this.propotion = propotion;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }
}