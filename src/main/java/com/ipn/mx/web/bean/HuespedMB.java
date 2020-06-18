package com.ipn.mx.web.bean;

import com.ipn.mx.modelo.dao.EstadoDAO;
import com.ipn.mx.modelo.dao.HuespedDAO;
import com.ipn.mx.modelo.dao.MunicipioDAO;
import com.ipn.mx.modelo.dao.UsuarioDAO;
import com.ipn.mx.modelo.dto.EstadoDTO;
import com.ipn.mx.modelo.dto.HuespedDTO;
import com.ipn.mx.modelo.dto.MunicipioDTO;
import com.ipn.mx.modelo.dto.UsuarioDTO;
import com.ipn.mx.modelo.entidades.Estado;
import com.ipn.mx.modelo.entidades.Municipio;
import com.ipn.mx.utilidades.ConexionBD;
import com.ipn.mx.utilidades.Utilerias;
import static com.ipn.mx.web.bean.BaseBean.ACC_ACTUALIZAR;
import static com.ipn.mx.web.bean.BaseBean.ACC_CREAR;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;


@ManagedBean(name = "huespedMB")
@SessionScoped
public class HuespedMB extends BaseBean implements Serializable {
    private HuespedDAO dao = new HuespedDAO();
    private HuespedDTO dto;
    private List<HuespedDTO> listaDeHuespedes;
    private int idEstado;
    private String nombreUsuario;
    private String pswrd;
    private Part foto;
    
    public HuespedMB(){}

    public HuespedDAO getDao() {
        return dao;
    }
    
    public void setDao(HuespedDAO dao) {
        this.dao = dao;
    }

    public HuespedDTO getDto() {
        return dto;
    }

    public void setDto(HuespedDTO dto) {
        this.dto = dto;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPswrd() {
        return pswrd;
    }

    public void setPswrd(String pswrd) {
        this.pswrd = pswrd;
    }

    public Part getFoto() {
        return foto;
    }

    public void setFoto(Part foto) {
        this.foto = foto;
    }
    
    public List<HuespedDTO> getListaDeHuespedes() {
        return listaDeHuespedes;
    }

    public void setListaDeHuespedes(List<HuespedDTO> listaDeHuespedes) {
        this.listaDeHuespedes = listaDeHuespedes;
    }
    
    @PostConstruct
    public void init(){
        listaDeHuespedes = new ArrayList<>();
        listaDeHuespedes = dao.readAll();    
    }
    
    public String prepareAdd(){
        dto = new HuespedDTO();
        idEstado = 0;
        setAccion(ACC_CREAR);
        return "/huespedes/huespedForm?faces-redirect=true";
    }
    
    public String prepareUpdate(){
        setAccion(ACC_ACTUALIZAR);
        
        int idMunicipio = dto.getEntidad().getIdMunicipio();
        MunicipioDTO mdto = new MunicipioDTO();
        MunicipioDAO mdao = new MunicipioDAO();
        UsuarioDTO udto = new UsuarioDTO();
        UsuarioDAO udao = new UsuarioDAO();
        
        mdto.getEntidad().setIdMunicipio(idMunicipio);
        mdto = mdao.read(mdto);
        idEstado = mdto.getEntidad().getIdEstado();
        nombreUsuario = dto.getEntidad().getNombreUsuario();
        
        udto.getEntidad().setNombreUsuario(nombreUsuario);
        udto = udao.read(udto);
        pswrd = udto.getEntidad().getPswrd();
        return "/huespedes/huespedForm?faces-redirect=true";
    }
    
    public String prepareListaHuespedes(){
        init();
        return "/huespedes/listaHuespedes?faces-redirect=true";
    }
    
    public String preparePerfil(){
        init();
        return "/huespedes/perfil?faces-redirect=true";
    }
    
    public String prepareIndex(){
        init();
        return "/index?faces-redirect=true";
    }
    
    public String back(){
        return prepareListaHuespedes();
    }
    
    public Boolean validate(){
        boolean valido = true;
        //validar campos del formulario
        
        
        return valido;
    }
    
    public String add(){
        Boolean valido = validate();
        if(valido){
            UsuarioDTO udto = new UsuarioDTO();
            UsuarioDAO udao = new UsuarioDAO();
            udto.getEntidad().setNombreUsuario(nombreUsuario);
            
            String existencia = udao.validate(udto);
            
            if(existencia.equals("Existente")){
                init();
                FacesContext.getCurrentInstance().addMessage("Formulario", new FacesMessage("Usuario ya existente"));
                return null;
            }else{
                udto.getEntidad().setPswrd(pswrd);
                udto.getEntidad().setTipo("huesped");
                udto.getEntidad().setExiste(true);
                if(existencia.equals("Eliminado")){
                    try {
                        if (foto != null && !foto.getSubmittedFileName().isEmpty()){
                            dto.getEntidad().setFoto(getBytesFromInputStream(foto.getInputStream()));
                        }else{
                            File img = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/img/marc.jpg"));
                            FileInputStream fis = new FileInputStream(img);
                            dto.getEntidad().setFoto(getBytesFromInputStream(fis));
                        }
                        dto.getEntidad().setNombreUsuario(nombreUsuario);
                        dto.getEntidad().setExiste(true);
                        dao.update(dto);
                        udao.update(udto);                        
                        Utilerias.enviarEmail(dto.getEntidad().getEmail(),"Registro de Huesped","Te has registrado nuevamente en HotelWeb como el usuario "+nombreUsuario);
                    } catch (IOException ex) {
                        Logger.getLogger(HuespedMB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    try {
                        if (foto != null && !foto.getSubmittedFileName().isEmpty()){
                            dto.getEntidad().setFoto(getBytesFromInputStream(foto.getInputStream()));
                        }else{
                            File img = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/img/marc.jpg"));
                            FileInputStream fis = new FileInputStream(img);
                            dto.getEntidad().setFoto(getBytesFromInputStream(fis));
                        }
                        dto.getEntidad().setNombreUsuario(nombreUsuario);
                        dto.getEntidad().setExiste(true);
                        udao.create(udto);
                        dao.create(dto);
                        Utilerias.enviarEmail(dto.getEntidad().getEmail(),"Registro de Huesped","Has completado tu registro en HotelWeb como el usuario "+nombreUsuario);
                    } catch (IOException ex) {
                        Logger.getLogger(HuespedMB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if(valido){
                return prepareIndex();
            }else{
                prepareAdd();
            }
        }
        return prepareAdd();
    }
    
    public String update(){
        Boolean valido = validate();
        if(valido){
            UsuarioDTO udto = new UsuarioDTO();
            UsuarioDAO udao = new UsuarioDAO();
            udto.getEntidad().setNombreUsuario(nombreUsuario);
            udto.getEntidad().setPswrd(pswrd);
            udto.getEntidad().setTipo("huesped");
            udto.getEntidad().setExiste(true);
            try {
                if (foto != null && !foto.getSubmittedFileName().isEmpty()){
                    dto.getEntidad().setFoto(getBytesFromInputStream(foto.getInputStream()));
                }else{
                    dto.getEntidad().setFoto(dto.getEntidad().getFoto());
                }
                dto.getEntidad().setNombreUsuario(nombreUsuario);
                dto.getEntidad().setExiste(true);
            } catch (IOException ex) {
                Logger.getLogger(HuespedMB.class.getName()).log(Level.SEVERE, null, ex);
            }
            dao.update(dto);
            udao.update(udto);
            if(valido){
                return prepareListaHuespedes();
            }else{
                prepareUpdate();
            }
        }
        return prepareUpdate();
    }
    
    public String delete(){
        Boolean valido = validate();
        if(valido){
            UsuarioDTO udto = new UsuarioDTO();
            UsuarioDAO udao = new UsuarioDAO();
            udto.getEntidad().setNombreUsuario(nombreUsuario);
            udto = udao.read(udto);
            udto.getEntidad().setExiste(false);
            dto.getEntidad().setNombreUsuario(nombreUsuario);
            dto = dao.read(dto);
            dto.getEntidad().setExiste(false);
            udao.update(udto);
            dao.update(dto);
            return prepareListaHuespedes();
        }
        
        return prepareIndex();
    }
    
    public void seleccionarHuesped(ActionEvent event){
        String claveSel = (String) FacesContext.getCurrentInstance()
                        .getExternalContext().getRequestParameterMap()
                        .get("claveSel");
        dto = new HuespedDTO();
        dto.getEntidad().setIdHuesped(Integer.parseInt(claveSel));
        try{
           dto = dao.read(dto);
            setNombreUsuario(dto.getEntidad().getNombreUsuario());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public List<Estado> listaEstados(){
        EstadoDAO edao = new EstadoDAO();
        return edao.readAll();
    }
    
    public List<Municipio> listaMunicipios(){
        MunicipioDAO mdao = new MunicipioDAO();
        return mdao.readAllEstado(idEstado);
    }
    
    public byte[] getBytesFromInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream(); 
        byte[] buffer = new byte[0xFFFF];
        for (int len = is.read(buffer); len != -1; len = is.read(buffer)) { 
            os.write(buffer, 0, len);
        }
        return os.toByteArray();
    }
    
    public String displayEstado(int idMunicipio){
        MunicipioDTO mdto = new MunicipioDTO();
        mdto.getEntidad().setIdMunicipio(idMunicipio);
        MunicipioDAO mdao = new MunicipioDAO();
        EstadoDTO edto = new EstadoDTO();
        EstadoDAO edao = new EstadoDAO();
        mdto = mdao.read(mdto);
        edto.getEntidad().setIdEstado(mdto.getEntidad().getIdEstado());
        edao.read(edto);
        return edto.getEntidad().getNombre();
    }
    
    public String displayMunicipio(int idMunicipio){
        MunicipioDTO mdto = new MunicipioDTO();
        mdto.getEntidad().setIdMunicipio(idMunicipio);
        MunicipioDAO mdao = new MunicipioDAO();
        mdto = mdao.read(mdto);
        return mdto.getEntidad().getNombre();
    }
    
    public String displayFoto(int idHuesped){
        return "/Imagen?id="+idHuesped;
    }
    
    public void generarReporteGeneral() {
        File reporte = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/Huespedes.jasper"));
        ConexionBD con = new ConexionBD();
        byte[] bytes;
        try {
            bytes = JasperRunManager.runReportToPdf(reporte.getPath(), null, con.obtenerConexion());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            ServletOutputStream outStream = response.getOutputStream();
            outStream.write(bytes, 0, bytes.length);
            outStream.flush();
            outStream.close();
        } catch (JRException | IOException ex) {
            Logger.getLogger(HuespedMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public void generarReporteIndividual(){
        Map parametros = new HashMap();
        int idHuesped = dto.getEntidad().getIdHuesped();
        parametros.put("idHuesped", idHuesped);
        File reporte = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reportes/Huesped.jasper"));
        ConexionBD con = new ConexionBD();
        byte[] bytes;
        try {
            bytes = JasperRunManager.runReportToPdf(reporte.getPath(), parametros, con.obtenerConexion());
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            ServletOutputStream outStream = response.getOutputStream();
            outStream.write(bytes, 0, bytes.length);
            outStream.flush();
            outStream.close();
        } catch (JRException | IOException ex) {
            Logger.getLogger(HuespedMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().responseComplete();
    }
    
}