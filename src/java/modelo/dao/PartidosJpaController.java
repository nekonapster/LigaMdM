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
import modelo.entities.Jornadas;
import modelo.entities.Ligas;
import modelo.entities.Partidos;

/**
 *
 * @author martin
 */
public class PartidosJpaController implements Serializable {

    public PartidosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Partidos partidos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipos idLocal = partidos.getIdLocal();
            if (idLocal != null) {
                idLocal = em.getReference(idLocal.getClass(), idLocal.getId());
                partidos.setIdLocal(idLocal);
            }
            Equipos idVisitante = partidos.getIdVisitante();
            if (idVisitante != null) {
                idVisitante = em.getReference(idVisitante.getClass(), idVisitante.getId());
                partidos.setIdVisitante(idVisitante);
            }
            Jornadas idJornada = partidos.getIdJornada();
            if (idJornada != null) {
                idJornada = em.getReference(idJornada.getClass(), idJornada.getId());
                partidos.setIdJornada(idJornada);
            }
            Ligas idLiga = partidos.getIdLiga();
            if (idLiga != null) {
                idLiga = em.getReference(idLiga.getClass(), idLiga.getId());
                partidos.setIdLiga(idLiga);
            }
            em.persist(partidos);
            if (idLocal != null) {
                idLocal.getPartidosList().add(partidos);
                idLocal = em.merge(idLocal);
            }
            if (idVisitante != null) {
                idVisitante.getPartidosList().add(partidos);
                idVisitante = em.merge(idVisitante);
            }
            if (idJornada != null) {
                idJornada.getPartidosList().add(partidos);
                idJornada = em.merge(idJornada);
            }
            if (idLiga != null) {
                idLiga.getPartidosList().add(partidos);
                idLiga = em.merge(idLiga);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Partidos partidos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Partidos persistentPartidos = em.find(Partidos.class, partidos.getId());
            Equipos idLocalOld = persistentPartidos.getIdLocal();
            Equipos idLocalNew = partidos.getIdLocal();
            Equipos idVisitanteOld = persistentPartidos.getIdVisitante();
            Equipos idVisitanteNew = partidos.getIdVisitante();
            Jornadas idJornadaOld = persistentPartidos.getIdJornada();
            Jornadas idJornadaNew = partidos.getIdJornada();
            Ligas idLigaOld = persistentPartidos.getIdLiga();
            Ligas idLigaNew = partidos.getIdLiga();
            if (idLocalNew != null) {
                idLocalNew = em.getReference(idLocalNew.getClass(), idLocalNew.getId());
                partidos.setIdLocal(idLocalNew);
            }
            if (idVisitanteNew != null) {
                idVisitanteNew = em.getReference(idVisitanteNew.getClass(), idVisitanteNew.getId());
                partidos.setIdVisitante(idVisitanteNew);
            }
            if (idJornadaNew != null) {
                idJornadaNew = em.getReference(idJornadaNew.getClass(), idJornadaNew.getId());
                partidos.setIdJornada(idJornadaNew);
            }
            if (idLigaNew != null) {
                idLigaNew = em.getReference(idLigaNew.getClass(), idLigaNew.getId());
                partidos.setIdLiga(idLigaNew);
            }
            partidos = em.merge(partidos);
            if (idLocalOld != null && !idLocalOld.equals(idLocalNew)) {
                idLocalOld.getPartidosList().remove(partidos);
                idLocalOld = em.merge(idLocalOld);
            }
            if (idLocalNew != null && !idLocalNew.equals(idLocalOld)) {
                idLocalNew.getPartidosList().add(partidos);
                idLocalNew = em.merge(idLocalNew);
            }
            if (idVisitanteOld != null && !idVisitanteOld.equals(idVisitanteNew)) {
                idVisitanteOld.getPartidosList().remove(partidos);
                idVisitanteOld = em.merge(idVisitanteOld);
            }
            if (idVisitanteNew != null && !idVisitanteNew.equals(idVisitanteOld)) {
                idVisitanteNew.getPartidosList().add(partidos);
                idVisitanteNew = em.merge(idVisitanteNew);
            }
            if (idJornadaOld != null && !idJornadaOld.equals(idJornadaNew)) {
                idJornadaOld.getPartidosList().remove(partidos);
                idJornadaOld = em.merge(idJornadaOld);
            }
            if (idJornadaNew != null && !idJornadaNew.equals(idJornadaOld)) {
                idJornadaNew.getPartidosList().add(partidos);
                idJornadaNew = em.merge(idJornadaNew);
            }
            if (idLigaOld != null && !idLigaOld.equals(idLigaNew)) {
                idLigaOld.getPartidosList().remove(partidos);
                idLigaOld = em.merge(idLigaOld);
            }
            if (idLigaNew != null && !idLigaNew.equals(idLigaOld)) {
                idLigaNew.getPartidosList().add(partidos);
                idLigaNew = em.merge(idLigaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = partidos.getId();
                if (findPartidos(id) == null) {
                    throw new NonexistentEntityException("The partidos with id " + id + " no longer exists.");
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
            Partidos partidos;
            try {
                partidos = em.getReference(Partidos.class, id);
                partidos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partidos with id " + id + " no longer exists.", enfe);
            }
            Equipos idLocal = partidos.getIdLocal();
            if (idLocal != null) {
                idLocal.getPartidosList().remove(partidos);
                idLocal = em.merge(idLocal);
            }
            Equipos idVisitante = partidos.getIdVisitante();
            if (idVisitante != null) {
                idVisitante.getPartidosList().remove(partidos);
                idVisitante = em.merge(idVisitante);
            }
            Jornadas idJornada = partidos.getIdJornada();
            if (idJornada != null) {
                idJornada.getPartidosList().remove(partidos);
                idJornada = em.merge(idJornada);
            }
            Ligas idLiga = partidos.getIdLiga();
            if (idLiga != null) {
                idLiga.getPartidosList().remove(partidos);
                idLiga = em.merge(idLiga);
            }
            em.remove(partidos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Partidos> findPartidosEntities() {
        return findPartidosEntities(true, -1, -1);
    }

    public List<Partidos> findPartidosEntities(int maxResults, int firstResult) {
        return findPartidosEntities(false, maxResults, firstResult);
    }

    private List<Partidos> findPartidosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Partidos.class));
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

    public Partidos findPartidos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Partidos.class, id);
        } finally {
            em.close();
        }
    }

    public int getPartidosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Partidos> rt = cq.from(Partidos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
