package com.sunesoft.ancon.core.saleContract.application;

import com.sunesoft.ancon.core.saleContract.application.dtos.SaleContractDto;
import com.sunesoft.ancon.fr.results.UniqueResult;
import com.sunesoft.ancon.fr.utils.DateHelper;
import com.sunesoft.ancon.fr.utils.StringUtils;
import com.sunesoft.ancon.fr.utils.excel.ReadExcel;
import jdk.nashorn.internal.parser.DateParser;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.formula.functions.T;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by admin on 2017/3/20.
 */
public class UploadSaleContracts extends ReadExcel<SaleContractDto> {

    @Override
    protected UniqueResult<SaleContractDto> convertRow(HSSFRow row,int rowNum,int colNum){
        try {

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);

            SaleContractDto dto = new SaleContractDto();
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(0)))){
                row.getCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);//设置单元格格式为 文本格式
                dto.setNum(getCellFormatValue(row.getCell(0)).trim());
            }
            if(!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(1)))){
              /*  // 定义单元格为字符串类型
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellStyle(cellStyle);*/

//                row.getCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);//设置单元格格式为 文本格式
                dto.setName(getCellFormatValue(row.getCell(1)));
            }

            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(2)))) {
                String str = getCellFormatValue(row.getCell(2));
                int count = 0;
                for (int i = 0; i < str.length(); i++) {
                    if (!Character.isDigit(str.charAt(i)) ) {
                        if (str.contains(".")) {
                            count++;
                            if (count>1){
                                dto.setContractMoney(BigDecimal.valueOf(0));
                                break;
                            }
                            continue;
                        }
                        dto.setContractMoney(BigDecimal.valueOf(0));
                        break;
                    }

                }
                if (dto.getContractMoney() == null)
                    dto.setContractMoney(BigDecimal.valueOf(Double.valueOf(getCellFormatValue(row.getCell(2)).trim())).movePointLeft(4));
            }else {
                dto.setContractMoney(BigDecimal.valueOf(0));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(3)))) {
                dto.setJudgeMoney(dto.getContractMoney());
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(4)))) {
                dto.setJudgeStatus(getCellFormatValue(row.getCell(4)).trim());
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(5)))) {
                dto.setJudgeTime(DateHelper.parse(getCellFormatValue(row.getCell(5)),"yyyy-MM-dd"));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(6)))) {
                dto.setBranchCompany(getCellFormatValue(row.getCell(6)).trim());
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(7)))) {
                dto.setJiaFangName(getCellFormatValue(row.getCell(7)).trim());
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(8)))) {
                dto.setContractType(getCellFormatValue(row.getCell(8)).trim());
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(9)))) {
                dto.setBidNotice(getCellFormatValue(row.getCell(9)).trim());
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(10)))) {
                dto.setConstructLicense(getCellFormatValue(row.getCell(10)).trim());
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(11)))) {
                dto.setFinishCheck(getCellFormatValue(row.getCell(11)).trim());
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(12)))) {
                dto.setProjectSettlement(getCellFormatValue(row.getCell(12)).trim());
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(13)))) {
                dto.setContractBeginTime(DateHelper.parse(getCellFormatValue(row.getCell(13)).trim(), "yyyy-MM-dd"));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(14)))) {

                dto.setProjectStartTime(DateHelper.parse(getCellFormatValue(row.getCell(14)).trim(), "yyyy-MM-dd"));
            }else{

                dto.setProjectStartTime(DateHelper.parse((year+"-01-01"),"yyyy-MM-dd"));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(15)))) {
                dto.setContractEndTime(DateHelper.parse(getCellFormatValue(row.getCell(15)).trim(), "yyyy-MM-dd"));
            }else{
                dto.setContractEndTime(DateHelper.parse((year+"-12-31"),"yyyy-MM-dd"));
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(16)))) {
                dto.setProjectMajor(getCellFormatValue(row.getCell(16)).trim());
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(17)))) {
                dto.setContractStatus(getCellFormatValue(row.getCell(17)).trim());
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(18)))) {
                dto.setContractIsReturn(getCellFormatValue(row.getCell(18)).trim());
            }
            if (!StringUtils.isNullOrWhiteSpace(getCellFormatValue(row.getCell(19)))) {
                dto.setRemark(getCellFormatValue(row.getCell(19)).trim());
            }else{
                dto.setRemark(getCellFormatValue(row.getCell(19)).trim());
            }

            return new UniqueResult<SaleContractDto>(dto);
        }catch (Exception ex){
            return new UniqueResult<SaleContractDto>("行"+rowNum+",数据解析错误，请检查！");
        }
    }


    /**
     * 判断表格是否正确
     * @param row 表头信息(列名)
     * @return Boolean
     */
    protected Boolean checkExcelCol(HSSFRow row) {
        System.out.print(row.getLastCellNum());
        //可判断指定列名是否一致等，如返回false 则会报格式不正确的错误
        if(row.getLastCellNum()!=20)
            return false;
        return true;
    }



}
