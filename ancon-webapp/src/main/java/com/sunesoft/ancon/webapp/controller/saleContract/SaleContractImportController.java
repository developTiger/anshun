package com.sunesoft.ancon.webapp.controller.saleContract;

import com.sunesoft.ancon.core.saleContract.application.SaleContractService;
import com.sunesoft.ancon.core.saleContract.application.UploadSaleContracts;
import com.sunesoft.ancon.core.saleContract.application.dtos.SaleContractDto;
import com.sunesoft.ancon.core.uAuth.application.dtos.UserSessionDto;
import com.sunesoft.ancon.fr.results.CommonResult;
import com.sunesoft.ancon.fr.results.ListResult;
import com.sunesoft.ancon.fr.utils.JsonHelper;
import com.sunesoft.ancon.fr.utils.excel.ExpotExcel;
import com.sunesoft.ancon.webapp.function.UserSession;
import com.sunesoft.ancon.webapp.model.ImportSaleContractDtoModel;
import com.sunesoft.ancon.webapp.utils.AjaxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017/3/20.
 */
@Controller
public class SaleContractImportController {

    @Autowired
    SaleContractService saleContractService;

    @Autowired
    UserSession userSession;


    /**
     * 下载指定格式的excel
     *
     * @param response
     */
    @RequestMapping(value = "exportSaleContracts")
    public void exportSaleContractTemplate(HttpServletResponse response) {
        List list = new ArrayList<>();

        ImportSaleContractDtoModel model = new ImportSaleContractDtoModel();

        model.setNum("we233we");
        model.setName("XS_土建工程承包合同书");
        model.setContractMoney(BigDecimal.valueOf(23.98));
        model.setJudgeMoney(BigDecimal.valueOf(45.78));
        model.setJudgeStatus("未定审");
        model.setJudgeTime("2016-03-25");
        model.setBranchCompany("合肥安顺燃气分公司");
        model.setJiaFangName("上海证大房地产有限公司");
        model.setContractType("标准合同");
        model.setBidNotice("无");
        model.setConstructLicense("无");
        model.setFinishCheck("有已收回");
        model.setProjectSettlement("有未收回");
        model.setContractBeginTime("2016-03-12");
        model.setProjectStartTime("2016-02-15");
        model.setContractEndTime("2016-04-14");
        model.setProjectMajor("市政工程");
        model.setContractStatus("进行中");
        model.setContractIsReturn("否");
        model.setRemark("备注");
        list.add(model);
        ExpotExcel<ImportSaleContractDtoModel> expotExcel = new ExpotExcel<>();
        String[] header = new String[]{"合同编号", "合同名称", "合同金额", "定审金额","定审状态", "定审日期", "所属分公司",
                "甲方名称", "合同类型", "中标通知书", "施工许可证", "竣工验收单", "工程结算单",
                "合同签订日期", "合同开工日期","合同结束日期", "工程专业","合同状态","合同是否已返回","备注"};
        try {
            expotExcel.doExportExcel("更新销售合同——导入模板", header, list, "yyyy-MM-dd", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 项目计划导入
     *
     * @param request
     * @param response
     * @throws java.io.IOException
     */
    @RequestMapping(value = "importSaleContracts")
    public void inputProjectPlan(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CommonResult result = null;
        String originalFilename = null;
        UserSessionDto userSessionDto=userSession.getCurrentUser(request);
        List<MultipartFile> myfiles = ((DefaultMultipartHttpServletRequest) request).getFiles("filename");
        for (MultipartFile myfile : myfiles) {
            if (myfile.isEmpty()) {
                result = new CommonResult(false, "请选择要上传的文件！");
                AjaxResponse.write(response, JsonHelper.toJson(result));
                return;
            } else {
                originalFilename = myfile.getOriginalFilename();
                try {
                    Date startTime = new Date();
                    UploadSaleContracts factory = new UploadSaleContracts();
                    Date time_1 = new Date();
                    ListResult<SaleContractDto> dto = factory.readExcel(myfile.getInputStream());
                    System.out.println("数据读取耗时："+(new Date().getTime()-time_1.getTime()));
                    if (dto.getIsSuccess() && dto.getItems().size() > 0) {
                        for (SaleContractDto d : dto.getItems()) {
                            d.setRegisterPerson(userSessionDto.getName());
                            result = saleContractService.editUploadDtoInfo(d);
                            if (!result.getIsSuccess()) break;
                        }

                    }
                    System.out.println("数据录入用时：(秒)"+(new Date().getTime()-startTime.getTime())/1000);
                } catch (IOException e) {
                    System.out.println("文件[" + originalFilename + "]上传失败,堆栈轨迹如下");
                    e.printStackTrace();
                    result = new CommonResult(false, "文件上传失败，请重试！！");
                    AjaxResponse.write(response, JsonHelper.toJson(result));
                    break;
                }
            }
        }
        AjaxResponse.write(response, JsonHelper.toJson(result));
    }


}
