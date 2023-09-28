package com.unbosque.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import java.io.IOException;

import javax.faces.application.FacesMessage;

import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.util.EscapeUtils;

@ManagedBean

@RequestScoped
public class IndexMB {
	private UploadedFile archivo;
	private String strBuscar;
	private String contenido;

	public void upload() {
		if (archivo != null) {
			System.out.println("Archivo no vacio");
			FacesMessage message = new FacesMessage("Successful", archivo.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
			leerArchivoCargado();
		} else {
			System.out.println("Archivo vacio");
		}
	}
	public void leerArchivoCargado() {
		System.out.println("Entra al metodo");
	    if (archivo!= null) {
	        try {
	        	System.out.println("No esta vacio");
	            contenido = new String(archivo.getContent(), "UTF-8");
	            System.out.println(contenido);
	        } catch (IOException e) {
	            e.printStackTrace(); // Manejo de errores en caso de problemas al leer el contenido
	        }
	    } else {
	    	System.out.println("Esta vacio");
	        contenido = null;
	    }
	}



	public UploadedFile getArchivo() {
		return archivo;
	}

	public void setArchivo(UploadedFile archivo) {
		this.archivo = archivo;
	}

	public String getStrBuscar() {
		return strBuscar;
	}

	public void setStrBuscar(String strBuscar) {
		this.strBuscar = strBuscar;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

}
