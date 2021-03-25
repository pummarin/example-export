package com.prior.training.export.controller;

import com.prior.training.export.service.ExportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class ExportController {

    private ExportService exportService;

    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    @GetMapping("/test")
    public void testExport(HttpServletRequest request, HttpServletResponse response) {

        this.exportService.exportCsv(response);

    }

    @GetMapping("/excel")
    public void testExportExcel(HttpServletRequest request, HttpServletResponse response) {

        this.exportService.exportExcel(response);

    }
}
