package com.example.demo.exportFile;

import com.poiji.annotation.ExcelCell;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Student {
    @ExcelCell(0)
    private String id;
    @ExcelCell(1)
    private String code;
    @ExcelCell(2)
    private String name;
    @ExcelCell(3)
    private Integer age;
    @ExcelCell(4)
    private String className;
}
