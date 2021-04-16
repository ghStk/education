package com.ghstk.demo.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;


public class TestExcel {


    public void testExcel() {
        String path = "C:\\Users\\ghStk\\Desktop\\test1.xlsx";
//        EasyExcel.write(path, DemoData.class).sheet("sheet1").doWrite(getList(8));
        EasyExcel.read(path,DemoData.class,new ExcelListener()).sheet().doRead();

    }

    private List<DemoData> getList(int n) {
        ArrayList<DemoData> arr = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            DemoData dd = new DemoData(i, "name" + i);
            arr.add(dd);
        }
        System.out.println(arr);
        return arr;
    }
}
