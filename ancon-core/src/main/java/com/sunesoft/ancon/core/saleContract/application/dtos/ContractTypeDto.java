package com.sunesoft.ancon.core.saleContract.application.dtos;

import java.math.BigDecimal;

/**
 * Created by admin on 2016/12/7.
 */
public class ContractTypeDto {

    private BigDecimal tujianMoney;
    private BigDecimal installMoney;
    private BigDecimal gasMoney;
    private BigDecimal bridgeMoney;

    public BigDecimal getTujianMoney() {
        return tujianMoney;
    }

    public void setTujianMoney(BigDecimal tujianMoney) {
        this.tujianMoney = tujianMoney;
    }

    public BigDecimal getInstallMoney() {
        return installMoney;
    }

    public void setInstallMoney(BigDecimal installMoney) {
        this.installMoney = installMoney;
    }

    public BigDecimal getGasMoney() {
        return gasMoney;
    }

    public void setGasMoney(BigDecimal gasMoney) {
        this.gasMoney = gasMoney;
    }

    public BigDecimal getBridgeMoney() {
        return bridgeMoney;
    }

    public void setBridgeMoney(BigDecimal bridgeMoney) {
        this.bridgeMoney = bridgeMoney;
    }
}
