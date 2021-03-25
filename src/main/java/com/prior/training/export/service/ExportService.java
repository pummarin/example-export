package com.prior.training.export.service;

import com.prior.training.export.entity.FilmEntity;
import com.prior.training.export.repository.FilmRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExportService {

    private FilmRepository filmRepository;

    public ExportService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    public void exportExcel(HttpServletResponse response) {
        response.setHeader("Content-disposition", "attachment;filename=filmList.xlsx");
        response.setHeader("Content-type","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        OutputStream os = null;

        try {
            os = response.getOutputStream();

            File file = new File("/home/pongpat/IdeaProjects/training/export/src/main/resources/excel-template/templat01.xlsx");
            FileInputStream fileInputStream = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheet("Sheet1");

            //init header
            Date now = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dateForm = simpleDateFormat.format(now);
            String dateTo = simpleDateFormat.format(now);

            //row4
            Row row4 = sheet.getRow(4);

            Cell cell4 = row4.createCell(4);
            cell4.setCellValue(dateForm);

            Cell cell6 = row4.createCell(6);
            cell6.setCellValue(dateTo);
            //row4

            //fill Data
            List<FilmEntity> filmEntityList = this.filmRepository.getOnly20Film();
            int rowStart = 6;
            for (int i = 0; i < filmEntityList.size(); i++) {
                FilmEntity fi = filmEntityList.get(i);

                Row rowData = sheet.createRow(rowStart);

                Cell data0 = rowData.createCell(0);
                data0.setCellValue(fi.getFilmID());

                Cell data1 = rowData.createCell(1);
                data1.setCellValue(fi.getTitle());

                Cell data2 = rowData.createCell(2);
                data2.setCellValue(fi.getDescription());

                Cell data3 = rowData.createCell(3);
                data3.setCellValue(fi.getReleaseYear());

                Cell data4 = rowData.createCell(4);
                data4.setCellValue(fi.getRentalRate());

                Cell data5 = rowData.createCell(5);
                data5.setCellValue(fi.getRentalDuration());

                Cell data6 = rowData.createCell(6);
                data6.setCellValue(fi.getLength());

                Cell data7 = rowData.createCell(7);
                data7.setCellValue(fi.getRating());

                rowStart++;
            }


            workbook.write(os);
            os.flush();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void exportCsv(HttpServletResponse response){

        response.setHeader("Content-disposition", "attachment;filename=filmList.txt");
        response.setHeader("Content-type","application/octet-stream");



        OutputStream os = null;


        try {
            os = response.getOutputStream();

            List<FilmEntity> filmEntityList = this.filmRepository.getOnly20Film();
            List<String> colNames = new ArrayList<>();
            colNames.add("Film Id");
            colNames.add("Title");
            colNames.add("Description");
            colNames.add("Release Year");
            colNames.add("Rental Rate");
            colNames.add("Rental Duration");
            colNames.add("Length");
            colNames.add("Rating");

            StringBuilder sb = new StringBuilder();
            sb.append(String.join(",",colNames)).append("\n");
            for (int i = 0; i < filmEntityList.size(); i++) {
                FilmEntity fi = filmEntityList.get(i);
                List<String> v = new ArrayList<>();
                v.add(String.valueOf(fi.getFilmID()));
                v.add(fi.getTitle());
                v.add(fi.getDescription());
                v.add(String.valueOf(fi.getReleaseYear()));
                v.add(String.valueOf(fi.getRentalRate()));
                v.add(String.valueOf(fi.getRentalDuration()));
                v.add(String.valueOf(fi.getLength()));
                v.add(fi.getRating());

                sb.append(String.join(",",v)).append("\n");

            }

            os.write(sb.toString().getBytes());

            response.setContentLength(sb.toString().length());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(null != os){
                    os.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }
}
