package com.koo.generator;

import com.koo.generator.generator.ChineseIDCardNumberGenerator;
import com.koo.generator.utils.AlertUtil;
import com.koo.generator.utils.BankAccountUtils;
import com.koo.generator.utils.CreditCodeUtil;
import com.koo.generator.utils.IdCardGeneratorUtil;
import com.koo.generator.utils.PhoneUtils;
import com.koo.generator.utils.StageUtils;
import com.koo.generator.utils.StringUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author gu.wq
 * @version 1.0
 * @type GeneratorViewController
 * @desc
 * @date 2024/3/22 16:34
 */
public class GeneratorViewController {
    @FXML
    private TextField phoneText;
    @FXML
    private TextField nameText;
    @FXML
    private TextField accountNoText;
    @FXML
    private TextField creditCodeText;
    @FXML
    private TextField companyNameText;
    @FXML
    private TextField legalPersonText;
    @FXML
    private ToggleGroup sex;
    @FXML
    private DatePicker pickerBirthDate;

    @FXML
    protected void onGeneratorPhoneClick() {
        phoneText.setText(PhoneUtils.generatorPhoneNo());
    }

    @FXML
    protected void onGeneratorBankCardNoClick() {
        String accountNo = BankAccountUtils.generatorBankAccount();
        accountNoText.setText(accountNo);
    }

    @FXML
    protected void onGeneratorCreditCodeClick() {
        creditCodeText.setText(CreditCodeUtil.randomCreditCode());
    }

    @FXML
    protected void onGeneratorCreditImageClick() {
        String companyName = companyNameText.getText();
        if (StringUtils.isEmpty(companyName)) {
            AlertUtil.showAlert(Alert.AlertType.WARNING, "消息提示", "", "请输入企业名称");
            return;
        }
        String legalPerson = legalPersonText.getText();
        if (StringUtils.isEmpty(legalPerson)) {
            AlertUtil.showAlert(Alert.AlertType.WARNING, "消息提示", "", "请输入法人姓名");
            return;
        }
        String creditCode = creditCodeText.getText();
        if (StringUtils.isEmpty(creditCode)) {
             creditCode = CreditCodeUtil.randomCreditCode();
            creditCodeText.setText(creditCode);
        }
        String companyImage = CreditCodeUtil.generatorPersonCard(companyName, legalPerson, creditCode);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("保存");
        fileChooser.setInitialFileName(companyImage);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("jpg Files", "*.jpg")
        );
        // 显示文件选择对话框
        File file = fileChooser.showSaveDialog(StageUtils.stage);
        if (file != null) {
            try (FileOutputStream os =  new FileOutputStream(file)) {
                byte[] buffers = new byte[10240];
                int len;
                File outFile = new File(companyImage);
                try (FileInputStream bis = new FileInputStream(outFile)) {
                    while ((len = bis.read(buffers)) > 0) {
                        os.write(buffers, 0, len);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        File deleteFile = new File(companyImage);
        if (deleteFile.exists()) {
            deleteFile.delete();
        }
    }

    @FXML
    protected void onGeneratorIdCordClick() {
        String name = nameText.getText();
        if (StringUtils.isEmpty(name)) {
            AlertUtil.showAlert(Alert.AlertType.WARNING, "消息提示", "", "请输入姓名");
            return;
        }
        LocalDate birthLocalDate = pickerBirthDate.getValue();
        if (birthLocalDate == null) {
            AlertUtil.showAlert(Alert.AlertType.WARNING, "消息提示", "", "请选择出生日期");
            return;
        }
        Object userData = sex.getSelectedToggle().getUserData();
        if (userData == null) {
            AlertUtil.showAlert(Alert.AlertType.WARNING, "消息提示", "", "请选择性别");
            return;
        }
        int sex = Integer.parseInt(userData.toString());
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = birthLocalDate.atStartOfDay(zoneId);
        Date birthDate = Date.from(zdt.toInstant());
        String personCardPath = IdCardGeneratorUtil.generatorPersonCard(nameText.getText(), ChineseIDCardNumberGenerator.getInstance().generateByBirthDateAndSex(birthDate, sex), ChineseIDCardNumberGenerator.generateIssueOrg());
        String nationalCardPath = IdCardGeneratorUtil.generatorNationalCard(nameText.getText(), ChineseIDCardNumberGenerator.generateIssueOrg(), ChineseIDCardNumberGenerator.generateValidPeriod());

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("保存");
        fileChooser.setInitialFileName(name + "身份证信息.zip");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("zip Files", "*.zip")
        );
        List<String> filePaths = new ArrayList<>();
        filePaths.add(personCardPath);
        filePaths.add(nationalCardPath);
        // 显示文件选择对话框
        File file = fileChooser.showSaveDialog(StageUtils.stage);
        if (file != null) {
            try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(file))) {
                byte[] buffers = new byte[10240];
                int len;
                for (int i = 0; i < filePaths.size(); i++) {
                    File outFile = new File(filePaths.get(i));
                    ZipEntry ze = new ZipEntry(filePaths.get(i));
                    zos.putNextEntry(ze);
                    try (FileInputStream bis = new FileInputStream(outFile)) {
                        while ((len = bis.read(buffers)) > 0) {
                            zos.write(buffers, 0, len);
                        }
                    }
                }
                zos.closeEntry();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (String filePath : filePaths) {
            File deleteFile = new File(filePath);
            if (deleteFile.exists()) {
                deleteFile.delete();
            }
        }
    }
}
