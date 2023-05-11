/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.entities.Equipos;
import modelo.entities.Estadisticas;
import modelo.entities.Ligas;

/**
 *
 * @author martin
 */
public class EstadisticasJpaController implements Serializable {

    public EstadisticasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estadisticas estadisticas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipos idEquipo = estadisticas.getIdEquipo();
            if (idEquipo != null) {
                idEquipo = em.getReference(idEquipo.getClass(), idEquipo.getId());
                estadisticas.setIdEquipo(idEquipo);
            }
            Ligas idLiga = estadisticas.getIdLiga();
            if (idLiga != null) {
                idLiga = em.getReference(idLiga.getClass(), idLiga.getId());
                estadisticas.setIdLiga(idLiga);
            }
            em.persist(estadisticas);
            if (idEquipo != null) {
                idEquipo.getEstadisticasList().add(estadisticas);
                idEquipo = em.merge(idEquipo);
            }
            if (idLiga != null) {
                idLiga.getEstadisticasList().add(estadisticas);
                idLiga = em.merge(idLiga);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estadisticas estadisticas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estadisticas persistentEstadisticas = em.find(Estadisticas.class, estadisticas.getId());
            Equipos idEquipoOld = persistentEstadisticas.getIdEquipo();
            Equipos idEquipoNew = estadisticas.getIdEquipo();
            Ligas idLigaOld = persistentEstadisticas.getIdLiga();
            Ligas idLigaNew = estadisticas.getIdLiga();
            if (idEquipoNew != null) {
                idEquipoNew = em.getReference(idEquipoNew.getClass(), idEquipoNew.getId());
                estadisticas.setIdEquipo(idEquipoNew);
            }
            if (idLigaNew != null) {
                idLigaNew = em.getReference(idLigaNew.getClass(), idLigaNew.getId());
                estadisticas.setIdLiga(idLigaNew);
            }
            estadisticas = em.merge(estadisticas);
            if (idEquipoOld != null && !idEquipoOld.equals(idEquipoNew)) {
                idEquipoOld.getEstadisticasList().remove(estadisticas);
                idEquipoOld = em.merge(idEquipoOld);
            }
            if (idEquipoNew != null && !idEquipoNew.equals(idEquipoOld)) {
                idEquipoNew.getEstadisticasList().add(estadisticas);
                idEquipoNew = em.merge(idEquipoNew);
            }
            if (idLigaOld != null && !idLigaOld.equals(idLigaNew)) {
                idLigaOld.getEstadisticasList().remove(estadisticas);
                idLigaOld = em.merge(idLigaOld);
            }
            if (idLigaNew != null && !idLigaNew.equals(idLigaOld)) {
                idLigaNew.getEstadisticasList().add(estadisticas);
                idLigaNew = em.merge(idLigaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = estadisticas.getId();
                if (findEstadisticas(id) == null) {
                    throw new NonexistentEntityException("The estadisticas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estadisticas estadisticas;
            try {
                estadisticas = em.getReference(Estadisticas.class, id);
                estadisticas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estadisticas with id " + id + " no longer exists.", enfe);
            }
            Equipos idEquipo = estadisticas.getIdEquipo();
            if (idEquipo != null) {
                idEquipo.getEstadisticasList().remove(estadisticas);
                idEquipo = em.merge(idEquipo);
            }
            Ligas idLiga = estadisticas.getIdLiga();
            if (idLiga != null) {
                idLiga.getEstadisticasList().remove(estadisticas);
                idLiga = em.merge(idLiga);
            }
            em.remove(estadisticas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Estadisticas> findEstadisticasEntities() {
        return findEstadisticasEntities(true, -1, -1);
    }

    public List<Estadisticas> findEstadisticasEntities(int maxResults, int firstResult) {
        return findEstadisticasEntities(false, maxResults, firstResult);
    }

    private List<Estadisticas> findEstadisticasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estadisticas.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Estadisticas findEstadisticas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estadisticas.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadisticasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estadisticas> rt = cq.from(Estadisticas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
