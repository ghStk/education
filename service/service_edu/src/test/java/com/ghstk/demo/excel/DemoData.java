package com.ghstk.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class DemoData {

    @ExcelProperty(value = "学生编号", index = 0)
    private Integer stuNo;
    @ExcelProperty(value = "学生姓名", index = 1)
    private String stuName;

    public DemoData() {
    }

    public DemoData(Integer sNo, String sName) {
        this.stuNo = sNo;
        this.stuName = sName;
    }

}
