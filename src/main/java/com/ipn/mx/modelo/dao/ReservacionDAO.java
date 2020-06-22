package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.dto.ReservacionDTO;
import com.ipn.mx.utilidades.HibernateUtil;
import java.util.List;
import javax.persistence.ParameterMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.query.Query;
import org.hibernate.result.ResultSetOutput;

public class ReservacionDAO {
        public void create(ReservacionDTO dto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try{
            transaction.begin();
            session.save(dto.getEntidad());
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
    }
    
    public void update(ReservacionDTO dto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try{
            transaction.begin();
            session.update(dto.getEntidad());
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
    }
    
    public String delete(ReservacionDTO dto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        String msj ="";
        try{
            transaction.begin();
            //session.delete(dto.getEntidad());
            ProcedureCall call = session.createStoredProcedureCall( "sp_cancelarReservacion" );
            call.registerParameter("idR",String.class,ParameterMode.IN).bindValue(dto.getEntidad().getIdReservacion()+"");
            ResultSetOutput rs = (ResultSetOutput)call.getOutputs().getCurrent();            
            msj = (String) rs.getSingleResult();
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
                msj = "Error";
            }
        }
        return msj;
    }
    
    public ReservacionDTO read(ReservacionDTO dto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try{
            transaction.begin();
            dto.setEntidad(session.get(dto.getEntidad().getClass(),dto.getEntidad().getIdReservacion()));
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
        return dto;
    }
    
    public List<ReservacionDTO> readAll(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        List<ReservacionDTO> lista = null;
        try{
            transaction.begin();
            Query q = session.createQuery("from Reservacion r order by r.idReservacion");
            lista = q.list();
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
        return lista;
    }
    
    public List<ReservacionDTO> readAllbyHuesped(int idHuesped){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        List<ReservacionDTO> lista = null;
        try{
            transaction.begin();
            Query q = session.createQuery("from Reservacion r where r.idHuesped = " + idHuesped + " order by r.idReservacion");
            lista = q.list();
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
        return lista;
    }
    
    public String validate(ReservacionDTO dto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        String msj = "";
        try{
            transaction.begin();
            ProcedureCall call = session.createStoredProcedureCall( "sp_validateReservacion" );
            call.registerParameter("idC",String.class,ParameterMode.IN).bindValue(dto.getEntidad().getIdCuarto()+"");
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

            String fecIni = sdf.format(dto.getEntidad().getFechaInicio());
            String fecFin = sdf.format(dto.getEntidad().getFechaTermino());
            call.registerParameter("fecIni",String.class,ParameterMode.IN).bindValue(fecIni);
            call.registerParameter("fecFin",String.class,ParameterMode.IN).bindValue(fecFin);
            ResultSetOutput rs = (ResultSetOutput)call.getOutputs().getCurrent();            
            msj = (String) rs.getSingleResult();
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive())
                transaction.rollback();
        }
        return msj;
    } 
}
