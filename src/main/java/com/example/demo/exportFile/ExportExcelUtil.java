package com.example.demo.exportFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.poiji.bind.Poiji;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.util.ResourceUtils;

public class ExportExcelUtil {
    public static <T> List<T> readExcel(String templatePath, Class<T> clazz) throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:templates/danh sách sinh viên.xlsx");
        return Poiji.fromExcel(file, clazz);

    }
    public static byte[] exportExcelWithTemplate(String templatePath, Map<String, Object> data) throws FileNotFoundException {
        Context context = new Context();
        context.putVar("data", data);

        try (InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("templates/template-ds-sv.xlsx");
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            JxlsHelper.getInstance().processTemplate(inputStream, outputStream, context);
            return outputStream.toByteArray();
        } catch (IOException e) {


        }
        return null;

    }
}
