package com.unbosque.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.context.PartialViewContext;

import org.primefaces.model.file.UploadedFile;

import com.unbosque.utils.KMP;

@ManagedBean
@ViewScoped
public class IndexMB {
	private UploadedFile archivo;
	private String strBuscar;
	private String contenido;
	private String archivo_txt;
	private int coincidencias;
	private KMP kmp;

	public void upload() {
		if (archivo != null) {
			System.out.println("Archivo no vacio");
			agregarMsj(FacesMessage.SEVERITY_INFO, "Informacion:",
					"El archivo " + archivo.getFileName() + " fue cargado con exito.");
			leerArchivoCargado();
		} else {
			System.out.println("Archivo vacio");
			validarArchivoCargado();
		}
	}

	public void leerArchivoCargado() {
		if (archivo != null) {
			try {
				System.out.println("No esta vacio");
				this.archivo_txt = new String(archivo.getContent(), "UTF-8");
				this.contenido = this.archivo_txt;
			} catch (IOException e) {
				e.printStackTrace();
				agregarMsj(FacesMessage.SEVERITY_ERROR, "Error", "El archivo no ha sido cargado. Intente nuevamente.");
			}
		} else {
			System.out.println("Esta vacio");
			archivo_txt = null;
		}
	}

	public void buscar() {
		validarArchivoCargado();
		if (this.strBuscar != null) {
			this.contenido = this.archivo_txt;
		}

		System.out.println("Palabra a buscar: " + this.strBuscar);
		this.kmp = new KMP();

		this.coincidencias = this.kmp.buscarCoincidencias(this.contenido, this.strBuscar);

		if (this.coincidencias > 0) {
			System.out.println("Hay coincidencias: " + this.coincidencias);
			this.contenido = resaltarCoincidencias(this.contenido, this.strBuscar);
		} else if ((this.strBuscar.equals("") || this.strBuscar == null) && this.contenido != null) {
			System.out.println("No se encontraron coincidencias");
			agregarMsj(FacesMessage.SEVERITY_ERROR, "Error",
					"Debe diligenciar el campo Buscar para poder realizar la busqueda.");
			this.contenido = this.archivo_txt;
		}
		recargarComponentes();
	}

	public void limpiar() {
		this.archivo_txt = null;
		this.contenido = null;
		this.strBuscar = null;
		this.archivo = null;
		this.coincidencias = 0;
		recargarComponentes();
	}

	public void agregarMsj(FacesMessage.Severity severidad, String cabecera, String texto) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severidad, cabecera, texto));
	}

	public void validarArchivoCargado() {
		if (this.archivo_txt == null) {
			agregarMsj(FacesMessage.SEVERITY_ERROR, "Error", "El archivo no ha sido cargado");
		}
	}

	private String resaltarCoincidencias(String texto, String palabra) {
		String resultado = texto.replaceAll("(?i)" + palabra,
				"<span style='background-color: yellow;'>" + palabra + "</span>");
		return resultado;
	}

	private void recargarComponentes() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		PartialViewContext partialViewContext = facesContext.getPartialViewContext();
		partialViewContext.getRenderIds().add("resultado");
		partialViewContext.getRenderIds().add("coincidencias");
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

	public String getArchivo_txt() {
		return archivo_txt;
	}

	public void setArchivo_txt(String archivo_txt) {
		this.archivo_txt = archivo_txt;
	}

	public int getCoincidencias() {
		return coincidencias;
	}

	public void setCoincidencias(int coincidencias) {
		this.coincidencias = coincidencias;
	}

	public KMP getKmp() {
		return kmp;
	}

	public void setKmp(KMP kmp) {
		this.kmp = kmp;
	}

}
