package com.ipn.mx.modelo.dao;

import com.ipn.mx.modelo.dto.UsuarioDTO;
import com.ipn.mx.utilidades.HibernateUtil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UsuarioDAO {
    public void create(UsuarioDTO dto){
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
    
    public void update(UsuarioDTO dto){
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
    
    public void delete(UsuarioDTO dto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try{
            transaction.begin();
            session.delete(dto.getEntidad());
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
    }
    
    public UsuarioDTO read(UsuarioDTO dto){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        try{
            transaction.begin();
            dto.setEntidad(session.get(dto.getEntidad().getClass(),dto.getEntidad().getNombreUsuario()));
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
        return dto;
    }
    
    public List<UsuarioDTO> readAll(){
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.getTransaction();
        List<UsuarioDTO> lista = null;
        try{
            transaction.begin();
            Query q = session.createQuery("from Usuario u order by u.nombreUsuario");
            lista = q.list();
            transaction.commit();
        }catch(HibernateException he){
            if(transaction!=null && transaction.isActive()){
                transaction.rollback();
            }
        }
        return lista;
    }
}
