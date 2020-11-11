package com.company.ksena.web.screens.reports;

import com.company.ksena.entity.cleaning_map.CleaningPosition;
import com.company.ksena.entity.cleaning_map.PositionWrapper;
import com.company.ksena.entity.company.Company;
import com.company.ksena.entity.inventory.ExpendableMaterial;
import com.company.ksena.entity.people.Employee;
import com.company.ksena.entity.people.Qualification;
import com.company.ksena.entity.task.Task;
import com.company.ksena.entity.task.TaskStatus;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FileLoader;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.DateField;
import com.haulmont.cuba.gui.components.LookupPickerField;
import com.haulmont.cuba.gui.export.ExportDisplay;
import com.haulmont.cuba.gui.export.ExportFormat;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.upload.FileUploadingAPI;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.inject.Inject;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@UiController("ksena_ReportsScreen")
@UiDescriptor("reports-screen.xml")
public class ReportsScreen extends Screen {
    @Inject
    private DateField<LocalDate> startDate;
    @Inject
    private DateField<LocalDate> finishDate;
    @Inject
    private LookupPickerField<Company> companyField;
    @Inject
    private DataManager dataManager;
    @Inject
    private FileUploadingAPI fileUploadingAPI;
    @Inject
    private Metadata metadata;
    @Inject
    private FileLoader fileLoader;
    @Inject
    private ExportDisplay exportDisplay;
    @Inject
    private Notifications notifications;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private DateField<LocalDate> startDateEmployeeReport;
    @Inject
    private DateField<LocalDate> finishDateEmployeeReport;

    @Subscribe("generate")
    public void onGenerateClick(Button.ClickEvent event) throws FileStorageException, IOException {
        LocalDate startDateValue = startDate.getValue();
        LocalDate finishDateValue = finishDate.getValue();
        if (startDateValue != null || finishDateValue != null) {

            Company company = companyField.getValue();
            List<Task> taskList;
            String companyName;
            String pointName;
            String taskNumber;
            String taskDocNumber;
            String taskTimeFactual;
            String taskTimePlane;
            String taskDate;
            double fullPrice = 0;
            double taskCost = 0;
            String oldPointName = null;

            HSSFWorkbook workbook = new HSSFWorkbook(); //создаешь новый файл
            HSSFSheet sheet = workbook.createSheet(messageBundle.getMessage("sheet")); // создаешь новый лист
            sheet.setMargin(Sheet.LeftMargin, 0.0);
            sheet.setMargin(Sheet.RightMargin, 0.0);
            sheet.getPrintSetup().setLandscape(true);
            int rowNum = 0;
            Cell cell;
            Row row;

            FileUploadingAPI.FileInfo info = fileUploadingAPI.createFile();
            try {
                info = fileUploadingAPI.createFile();
            } catch (FileStorageException e) {
                e.printStackTrace();
            }


            assert finishDateValue != null;
            assert startDateValue != null;
            if (company != null) {
                taskList = dataManager.load(Task.class)
                        .query("select e from ksena_Task e where (e.company = :company) and (e.taskStatus = :taskStatus) and (e.dateOfCompletion >= :startDateValue) and (e.dateOfCompletion <= :finishDateValue)")
                        .parameter("company", company)
                        .parameter("taskStatus", TaskStatus.EXECUTED)
                        .parameter("startDateValue", startDateValue)
                        .parameter("finishDateValue", finishDateValue)
                        .view("task-view")
                        .list();
            } else {
                taskList = dataManager.load(Task.class)
                        .query("select e from ksena_Task e where (e.taskStatus = :taskStatus) and (e.dateOfCompletion >= :startDateValue) and (e.dateOfCompletion <= :finishDateValue)")
                        .parameter("taskStatus", TaskStatus.EXECUTED)
                        .parameter("startDateValue", startDateValue)
                        .parameter("finishDateValue", finishDateValue)
                        .view("task-view")
                        .list();
            }

            sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 7));
            sheet.setColumnWidth(0, 1000);
            sheet.setColumnWidth(1, 2000);
            sheet.setColumnWidth(2, 6000);
            row = sheet.createRow(rowNum);
            row.setHeightInPoints(15.75f);
            rowNum++;
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(messageBundle.getMessage("reportName"));
            cell.setCellStyle(createStyle(workbook, true, 12, false, HorizontalAlignment.CENTER));

            sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 7));
            row = sheet.createRow(rowNum);
            row.setHeightInPoints(12.75f);
            rowNum += 2;
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(messageBundle.getMessage("timeRange") + startDateValue.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " - " + finishDateValue.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            cell.setCellStyle(createStyle(workbook, false, 10, false, HorizontalAlignment.CENTER));

            taskList = taskList.stream().sorted(Comparator.comparing(task -> task.getPoint().getName())).collect(Collectors.toList());
            taskList = taskList.stream().sorted(Comparator.comparing(Task::getDateOfCompletion)).collect(Collectors.toList());

            for (Task task : taskList) {


                companyName = task.getCompany().getName();
                taskNumber = task.getTaskNumber();

                try {
                    pointName = task.getPoint().getName();

                } catch (Exception e) {
                    pointName = "";
                }

                try {
                    taskDocNumber = task.getTaskNumber();
                } catch (Exception e) {
                    taskDocNumber = "";
                }
                try {
                    taskDate = task.getDateOfCompletion().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                } catch (Exception e) {
                    taskDate = "";
                }
                try {
                    taskCost = task.getCost();
                } catch (Exception e) {
                    taskCost = 0;
                }
                try {
                    taskTimeFactual = task.getTaskTimeFactual().format(DateTimeFormatter.ofPattern("HH:mm"));
                } catch (Exception e) {
                    taskTimeFactual = "";
                }
                try {
                    taskTimePlane = task.getTaskTimePlane().format(DateTimeFormatter.ofPattern("HH:mm"));
                } catch (Exception e) {
                    taskTimePlane = "";
                }


                if (oldPointName != pointName) {
                    sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 7));
                    row = sheet.createRow(rowNum);
                    row.setHeightInPoints(12.75f);
                    rowNum++;
                    cell = row.createCell(0, CellType.STRING);
                    cell.setCellValue(messageBundle.getMessage("point") + pointName);
                    cell.setCellStyle(createStyle(workbook, false, 10, false, HorizontalAlignment.CENTER));

                    sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 7));
                    row = sheet.createRow(rowNum);
                    row.setHeightInPoints(12.75f);
                    rowNum++;
                    cell = row.createCell(0, CellType.STRING);
                    cell.setCellValue(messageBundle.getMessage("client") + companyName);
                    cell.setCellStyle(createStyle(workbook, false, 10, false, HorizontalAlignment.CENTER));


                    row = sheet.createRow(rowNum);
                    rowNum++;
                    row.setHeightInPoints(32.25f);
                    cell = row.createCell(0, CellType.STRING);
                    cell.setCellValue(messageBundle.getMessage("taskNumber"));
                    cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                    cell = row.createCell(1, CellType.STRING);
                    cell.setCellValue(messageBundle.getMessage("taskDocNumber"));
                    cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                    cell = row.createCell(2, CellType.STRING);
                    cell.setCellValue(messageBundle.getMessage("taskDate"));
                    cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                    cell = row.createCell(3, CellType.STRING);
                    cell.setCellValue(messageBundle.getMessage("planedTime"));
                    cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                    cell = row.createCell(4, CellType.STRING);
                    cell.setCellValue(messageBundle.getMessage("factualTime"));
                    cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                    cell = row.createCell(5, CellType.STRING);
                    cell.setCellValue(messageBundle.getMessage("jobCost"));
                    cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                    cell = row.createCell(6, CellType.STRING);
                    cell.setCellValue(messageBundle.getMessage("expendableMaterialsCost"));
                    cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                    cell = row.createCell(7, CellType.STRING);
                    cell.setCellValue(messageBundle.getMessage("fullCost"));
                    cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));
                }

                for (PositionWrapper position : task.getCleaningMap()) {
                    double fullPriseExpendableMaterial = 0;
                    if (task.getAddPriseExpendableMaterial() != null) {
                        if (task.getAddPriseExpendableMaterial()) {

                            for (ExpendableMaterial material : position.getPosition().getExpendableMaterials()) {
                                fullPriseExpendableMaterial = fullPriseExpendableMaterial + material.getPrice();
                            }
                        }
                    }
                    double pricePosition = 0;
                    if (position.getPosition().getPrice() != null) {
                        pricePosition = position.getPosition().getPrice();
                    }

                    fullPrice = fullPrice + fullPriseExpendableMaterial + pricePosition;


                }

                row = sheet.createRow(rowNum);
                rowNum++;

                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue(taskNumber);
                cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(taskDocNumber);
                cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(taskDate);
                cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(taskTimePlane);
                cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(taskTimeFactual);
                cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(taskCost);
                cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                cell = row.createCell(6, CellType.STRING);
                cell.setCellValue(fullPrice);
                cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                cell = row.createCell(7, CellType.STRING);
                cell.setCellValue(fullPrice + taskCost);
                cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                oldPointName = pointName;

            }
            rowNum += 3;

            FileOutputStream outFile = new FileOutputStream(info.getFile());
            workbook.write(outFile);
            outFile.close();

            FileDescriptor fileDescriptor = metadata.create(FileDescriptor.class);
            fileDescriptor.setName(messageBundle.getMessage("fileName"));
            fileDescriptor.setExtension("xls");
            fileDescriptor.setSize((info.getFile().length()));
            fileDescriptor.setCreateDate(new Date());

            FileUploadingAPI.FileInfo finalInfo = info;
            fileLoader.saveStream(fileDescriptor, () -> {
                try {
                    return new FileInputStream(finalInfo.getFile());
                } catch (FileNotFoundException e) {
                    return null;
                }
            });

            dataManager.commit(fileDescriptor);

            exportDisplay.show(fileDescriptor, ExportFormat.XLS);

            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption(messageBundle.getMessage("generated"))
                    .show();
        } else {
            notifications.create().withDescription("You cannot set start or finish date").show();
        }

    }

    private static HSSFCellStyle createStyle(HSSFWorkbook workbook, boolean bold, int fontHeight, boolean border, HorizontalAlignment alignment) {
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) fontHeight);
        font.setBold(bold);

        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setAlignment(alignment);
        style.setWrapText(true);


        if (border) {
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);
        }

        return style;
    }

    @Subscribe("generateEmployeeReport")
    public void onGenerateEmployeeReportClick(Button.ClickEvent event) {
        LocalDate startDateValue = startDateEmployeeReport.getValue();
        LocalDate finishDateValue = finishDateEmployeeReport.getValue();
        if (startDateValue != null || finishDateValue != null) {

            List<Task> taskList;

            HSSFWorkbook workbook = new HSSFWorkbook(); //создаешь новый файл
            HSSFSheet sheet = workbook.createSheet(messageBundle.getMessage("sheet")); // создаешь новый лист
            sheet.setMargin(Sheet.LeftMargin, 0.0);
            sheet.setMargin(Sheet.RightMargin, 0.0);
            sheet.getPrintSetup().setLandscape(true);
            int rowNum = 0;
            Cell cell;
            Row row;

            FileUploadingAPI.FileInfo info = null;
            try {
                info = fileUploadingAPI.createFile();
            } catch (FileStorageException e) {
                e.printStackTrace();
            }
            try {
                info = fileUploadingAPI.createFile();
            } catch (FileStorageException e) {
                e.printStackTrace();
            }

            assert finishDateValue != null;
            assert startDateValue != null;

            taskList = dataManager.load(Task.class)
                    .query("select e from ksena_Task e where (e.taskStatus = :taskStatus) and (e.dateOfCompletion >= :startDateValue) and (e.dateOfCompletion <= :finishDateValue)")
                    .parameter("taskStatus", TaskStatus.EXECUTED)
                    .parameter("startDateValue", startDateValue)
                    .parameter("finishDateValue", finishDateValue)
                    .view("task-view")
                    .list();


            sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 5));
            sheet.setColumnWidth(0, 1000);
            sheet.setColumnWidth(1, 2000);
            sheet.setColumnWidth(2, 6000);
            row = sheet.createRow(rowNum);
            row.setHeightInPoints(15.75f);
            rowNum++;
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(messageBundle.getMessage("employeeReport"));
            cell.setCellStyle(createStyle(workbook, true, 12, false, HorizontalAlignment.CENTER));

            sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 5));
            row = sheet.createRow(rowNum);
            row.setHeightInPoints(12.75f);
            rowNum += 2;
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(messageBundle.getMessage("timeRange") + startDateValue.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " - " + finishDateValue.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            cell.setCellStyle(createStyle(workbook, false, 10, false, HorizontalAlignment.CENTER));

            taskList = taskList.stream().sorted(Comparator.comparing(task -> task.getPoint().getName())).collect(Collectors.toList());
            taskList = taskList.stream().sorted(Comparator.comparing(Task::getDateOfCompletion)).collect(Collectors.toList());

            List<Employee> taskEmployeesList = new ArrayList<>();

            for (Task task : taskList) {
                taskEmployeesList.addAll(task.getEmployees());
            }
            taskEmployeesList = taskEmployeesList.stream().distinct().collect(Collectors.toList());


            for (Employee employee : taskEmployeesList) {

                sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 5));
                row = sheet.createRow(rowNum);
                row.setHeightInPoints(12.75f);
                rowNum++;
                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue(messageBundle.getMessage("employee") + employee.getFirstName() + " " + employee.getLastName());
                cell.setCellStyle(createStyle(workbook, false, 10, false, HorizontalAlignment.CENTER));

                sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 0, 5));
                row = sheet.createRow(rowNum);
                row.setHeightInPoints(12.75f);
                rowNum++;
                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue(messageBundle.getMessage("qualification") + employee.getQualification());
                cell.setCellStyle(createStyle(workbook, false, 10, false, HorizontalAlignment.CENTER));

                List<Task> taskForEmploee;

                taskForEmploee = dataManager.load(Task.class)
                        .query("select e from ksena_Task e where (e.taskStatus = :taskStatus) and (e.dateOfCompletion >= :startDateValue) and (e.dateOfCompletion <= :finishDateValue) and (e.employees = :employees)")
                        .parameter("taskStatus", TaskStatus.EXECUTED)
                        .parameter("startDateValue", startDateValue)
                        .parameter("finishDateValue", finishDateValue)
                        .parameter("employees", employee)
                        .view("task-view")
                        .list();

                row = sheet.createRow(rowNum);
                rowNum++;
                row.setHeightInPoints(32.25f);
                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue(messageBundle.getMessage("taskDocNumber"));
                cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(messageBundle.getMessage("taskNumber"));
                cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(messageBundle.getMessage("companyName"));
                cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(messageBundle.getMessage("pointName"));
                cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(messageBundle.getMessage("dateOfCompletion"));
                cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(messageBundle.getMessage("wage"));
                cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                taskForEmploee = taskForEmploee.stream().sorted(Comparator.comparing(Task::getDateOfCompletion)).collect(Collectors.toList());

                for (Task task :taskForEmploee){

                    String taskNumber = task.getTaskNumber();

                    String pointName;
                    try {
                        pointName = task.getPoint().getName();
                    } catch (Exception e) {
                        pointName = "";
                    }

                    String taskDocNumber;
                    try {
                        taskDocNumber = task.getTaskDocument().getDocNumber();
                    } catch (Exception e) {
                        taskDocNumber = "";
                    }
                    String dateOfCompletion;
                    try {
                        dateOfCompletion = task.getDateOfCompletion().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    } catch (Exception e) {
                        dateOfCompletion = "";
                    }

                    String companyName;
                    try {
                        companyName = task.getCompany().getName();
                    } catch (Exception e) {
                        companyName = "";
                    }

                    double wage;
                    if (employee.getQualification() == Qualification.ELEMENTARY){
                        wage = task.getSalaryElementary();
                    }else if (employee.getQualification()==Qualification.MEDIUM){
                        wage = task.getSalaryMedium();
                    }else if (employee.getQualification()==Qualification.HIGH){
                        wage = task.getSalaryHigh();
                    }else {
                        wage = 0;
                    }

                    row = sheet.createRow(rowNum);
                    rowNum++;

                    cell = row.createCell(0, CellType.STRING);
                    cell.setCellValue(taskDocNumber);
                    cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                    cell = row.createCell(1, CellType.STRING);
                    cell.setCellValue(task.getTaskNumber());
                    cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                    cell = row.createCell(2, CellType.STRING);
                    cell.setCellValue(companyName);
                    cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                    cell = row.createCell(3, CellType.STRING);
                    cell.setCellValue(pointName);
                    cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                    cell = row.createCell(4, CellType.STRING);
                    cell.setCellValue(dateOfCompletion);
                    cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                    cell = row.createCell(5, CellType.STRING);
                    cell.setCellValue(wage);
                    cell.setCellStyle(createStyle(workbook, true, 8, true, HorizontalAlignment.CENTER));

                }
                rowNum++;
            }
            rowNum += 3;

            FileOutputStream outFile = null;
            try {
                outFile = new FileOutputStream(info.getFile());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                workbook.write(outFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            FileDescriptor fileDescriptor = metadata.create(FileDescriptor.class);
            fileDescriptor.setName(messageBundle.getMessage("fileName"));
            fileDescriptor.setExtension("xls");
            fileDescriptor.setSize((info.getFile().length()));
            fileDescriptor.setCreateDate(new Date());

            FileUploadingAPI.FileInfo finalInfo = info;
            try {
                fileLoader.saveStream(fileDescriptor, () -> {
                    try {
                        return new FileInputStream(finalInfo.getFile());
                    } catch (FileNotFoundException e) {
                        return null;
                    }
                });
            } catch (FileStorageException e) {
                e.printStackTrace();
            }

            dataManager.commit(fileDescriptor);

            exportDisplay.show(fileDescriptor, ExportFormat.XLS);

            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption(messageBundle.getMessage("generated"))
                    .show();
        } else {
            notifications.create().withDescription("You cannot set start or finish date").show();
        }

    }
}