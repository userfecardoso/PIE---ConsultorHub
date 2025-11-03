package com.consultorhub.backend.service;

import java.io.InputStream;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

@Service
public class PDFBoxService {

	public String extractText(InputStream document) throws IOException{
		
		byte[] documentBytes = document.readAllBytes();
		
		try(PDDocument inputtedDoc = Loader.loadPDF(documentBytes)){
		
			if(inputtedDoc.isEncrypted()) {
				throw new IOException("Erro: O Documento PDF está protegido por uma senha.");
			}
			
			PDFTextStripper extractor = new PDFTextStripper();
			
			return extractor.getText(inputtedDoc);
			
		}
		
	}
}
