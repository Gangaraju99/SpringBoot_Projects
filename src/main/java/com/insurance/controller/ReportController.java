package com.insurance.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.insurance.entity.Citizen;
import com.insurance.request.SearchRequest;
import com.insurance.service.ReportService;

@Controller
public class ReportController {

	@Autowired
	private ReportService service;

	@PostMapping("/fetch")
	public String handleSearch(@ModelAttribute("search") SearchRequest search, Model model) {
		//System.out.println(request);
		List<Citizen> plans = service.search(search);
		model.addAttribute("plans",plans);

		init(model);

		return "index";

	}

	@GetMapping("/")
	public String indexPage(Model model) {

		// SearchRequest searchObj = new SearchRequest();
		model.addAttribute("search", new SearchRequest());
		init(model);

		return "index";
	}

	private void init(Model model) {
		SearchRequest searchObj = new SearchRequest();

		model.addAttribute("names", service.getPlanNames());
		model.addAttribute("status", service.getPlanStatus());
	}
	
	@GetMapping("/excel")
	public void excelExport (HttpServletResponse response) throws Exception{
		boolean status = service.exportExcel(response);
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition","attachment;filename=plans.xls");
		
	
	
	}
	
	@GetMapping("/pdf")
	public void pdfExport (HttpServletResponse response) throws Exception{
		
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition","attachment;filename=plans.pdf");
		service.exportPdf(response);
		
		
		
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
