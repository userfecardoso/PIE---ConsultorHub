package com.consultorhub.backend.controller;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {
	
	@GetMapping("/")
	public String home_route() {
		return "API ConsultorHub";
	}
	
	@GetMapping("/doc_search")
	public String routeSearch() {
		/*
		 
		*/
		return "Rota Search";
	}
	
//	@PostMapping("/upload")
//	public String routeUpload() {
//		return "Rota upload";
//	}
	
	@GetMapping("/near_end")
	public String routeNearEnd() {
		return "Rota Near End";
	}
	
	@GetMapping("/doc_reports")
	public String routeReports() {
		return "Rota Reports";
	}
	
	@PostMapping("/doc_update")
	public String routeUpdate() {
		return "Rota Update";
	}
	
	@DeleteMapping("doc_delete")
	public String routeDocDelete(){
		return "Rota Doc Delete";
	}
	
	@PostMapping("/client_register")
	public String routeClientRegister(){
		return "Rota Client Register";
	}
	
//	======================= Client Endpoints
	
	@PostMapping("/register")
	public String routeRegister() {
		return "Rota Register";
	}
	
	@GetMapping("/client_search")
	public String routeClientSearch() {
		return "Rota Client Search";
	}
	
	@GetMapping("/client_reports")
	public String routeClientReports(){
		return "Rota Client Reports";
	}
	
	@GetMapping("/client_docs")
	public String routeClientDocs(){
		return "Rota client docs";
	}
	
	@GetMapping("/client_update")
	public String routeClientUpdate(){
		return "Rota Client Update";
	}
	
	@DeleteMapping("client_delete")
	public String routeClientDelete(){
		return "Rota Client Delete";
	}
	
	@GetMapping("/client_company_reports")
	public String routeClientCompanyReports(){
		return "Rota Client Company Reports";
	}
	
	@PostMapping("/register_company")
	public String routeRegisterCompany(){
		return "Rota Register Company";
	}
	
}
