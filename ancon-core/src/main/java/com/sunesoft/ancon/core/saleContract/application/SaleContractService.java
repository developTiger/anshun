package com.sunesoft.ancon.core.saleContract.application;

import com.sunesoft.ancon.core.saleContract.application.criteria.SaleContractCriteria;
import com.sunesoft.ancon.core.saleContract.application.dtos.*;
import com.sunesoft.ancon.core.saleContract.domain.SaleContract;
import com.sunesoft.ancon.fr.results.CommonResult;
import com.sunesoft.ancon.fr.results.PagedResult;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/11/24.
 */
public interface SaleContractService {

    public CommonResult addSaleContract(SaleContractDto dto);

    public boolean deleteSaleContract(Long[] id);

    public CommonResult updateSaleContract(SaleContractDto dto);

    public List<SaleContractDto> getAll();

    public List<InSimpleSaleContractDto> getInSimpleAll();


    public List<SaleContractSimpleDto> getSimpleAll();

    public SaleContractDto getById(Long id);
    public SaleContractDto getByNum(String num);

    /**
     * 销售合同统计表查询
     * @param contractCriteria
     * @return
     */
    public PagedResult<SaleContractDtoModel> findPages(SaleContractCriteria contractCriteria);

    public PagedResult<SaleContractDto> findPagesBySaleContract_echarts(SaleContractCriteria contractCriteria);

    public List<SaleContractDto> getByCompanyId(Long companyId);

    //
    public List<Map<String,Object>> getSaleContractsCountData(String year,String branchCompany,String jiafangName);

    /**
     * 今年销售合同签单数查询 首页
     * @return
     */
    public List<Map<String,Object>> getSaleContracts_count_byNumber();

    /**
     * 开票统计 首页
     * @return
     */
    public List<Map<String,Object>> getBilling_count();

    /**
     * 收款统计 首页
     * @return
     */
    public List<Map<String,Object>> getGathering_count();

    /**
     *施工产值统计 首页
     * @return
     */
    public List<Map<String,Object>> getConValue_count();

    /**
     * 根据合同id 查询合同开票总金额 收款总金额 施工产值总金额
     * @param saleContractId
     * @return
     */
    public List<Map<String,Object>> getAllCount_biilingAndGatheringAndConValue(Long saleContractId);

    /**
     * 动态总报表 数据查询
     * @return
     */
    public List<DynamicTotalReportFormDto> getDynamicTotalReportForm();

    /**
     * 销售合同报表 数据查询
     * @param contractCriteria
     * @return
     */
    public List<ContractReportFormDto>  getReportFormByBranchCompany(SaleContractCriteria contractCriteria);

    /**
     * 首页 四个按钮 销售合同统计
     * @return
     */
    public List<IndexContractsTotalCountDto> getContractsTotalByMonthAndYear();

    /**
     * 获取销售合同统计，按月查询
     * @param contractCriteria
     * @return
     */
    public List<Map<Integer,Object>> summeryJudgeMoney(SaleContractCriteria contractCriteria);

    /**
     *
     * @param begin
     * @param end
     * @return
     */
    public List<Map<Integer,Object>> summerysalType(String begin,String end);

    public CommonResult editUploadDtoInfo(SaleContractDto dto);

    /**
     * 查询合同编号是否重复
     * @param number
     * @return
     */
    public SaleContract getBySaleContractNum(String number);

    /**
     * 通过合同编号更新
     * @param dto
     * @return
     */
    public CommonResult updateSaleContractByNum(SaleContractDto dto);






}
