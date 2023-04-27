/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.entidades.Ligas;
import modelo.entidades.Partidos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.entidades.Jornadas;

/**
 *
 * @author martin
 */
public class JornadasJpaController implements Serializable {

    public JornadasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Jornadas jornadas) {
        if (jornadas.getPartidosList() == null) {
            jornadas.setPartidosList(new ArrayList<Partidos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ligas idLiga = jornadas.getIdLiga();
            if (idLiga != null) {
                idLiga = em.getReference(idLiga.getClass(), idLiga.getId());
                jornadas.setIdLiga(idLiga);
            }
            List<Partidos> attachedPartidosList = new ArrayList<Partidos>();
            for (Partidos partidosListPartidosToAttach : jornadas.getPartidosList()) {
                partidosListPartidosToAttach = em.getReference(partidosListPartidosToAttach.getClass(), partidosListPartidosToAttach.getId());
                attachedPartidosList.add(partidosListPartidosToAttach);
            }
            jornadas.setPartidosList(attachedPartidosList);
            em.persist(jornadas);
            if (idLiga != null) {
                idLiga.getJornadasList().add(jornadas);
                idLiga = em.merge(idLiga);
            }
            for (Partidos partidosListPartidos : jornadas.getPartidosList()) {
                Jornadas oldIdJornadaOfPartidosListPartidos = partidosListPartidos.getIdJornada();
                partidosListPartidos.setIdJornada(jornadas);
                partidosListPartidos = em.merge(partidosListPartidos);
                if (oldIdJornadaOfPartidosListPartidos != null) {
                    oldIdJornadaOfPartidosListPartidos.getPartidosList().remove(partidosListPartidos);
                    oldIdJornadaOfPartidosListPartidos = em.merge(oldIdJornadaOfPartidosListPartidos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Jornadas jornadas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jornadas persistentJornadas = em.find(Jornadas.class, jornadas.getId());
            Ligas idLigaOld = persistentJornadas.getIdLiga();
            Ligas idLigaNew = jornadas.getIdLiga();
            List<Partidos> partidosListOld = persistentJornadas.getPartidosList();
            List<Partidos> partidosListNew = jornadas.getPartidosList();
            List<String> illegalOrphanMessages = null;
            for (Partidos partidosListOldPartidos : partidosListOld) {
                if (!partidosListNew.contains(partidosListOldPartidos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Partidos " + partidosListOldPartidos + " since its idJornada field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idLigaNew != null) {
                idLigaNew = em.getReference(idLigaNew.getClass(), idLigaNew.getId());
                jornadas.setIdLiga(idLigaNew);
            }
            List<Partidos> attachedPartidosListNew = new ArrayList<Partidos>();
            for (Partidos partidosListNewPartidosToAttach : partidosListNew) {
                partidosListNewPartidosToAttach = em.getReference(partidosListNewPartidosToAttach.getClass(), partidosListNewPartidosToAttach.getId());
                attachedPartidosListNew.add(partidosListNewPartidosToAttach);
            }
            partidosListNew = attachedPartidosListNew;
            jornadas.setPartidosList(partidosListNew);
            jornadas = em.merge(jornadas);
            if (idLigaOld != null && !idLigaOld.equals(idLigaNew)) {
                idLigaOld.getJornadasList().remove(jornadas);
                idLigaOld = em.merge(idLigaOld);
            }
            if (idLigaNew != null && !idLigaNew.equals(idLigaOld)) {
                idLigaNew.getJornadasList().add(jornadas);
                idLigaNew = em.merge(idLigaNew);
            }
            for (Partidos partidosListNewPartidos : partidosListNew) {
                if (!partidosListOld.contains(partidosListNewPartidos)) {
                    Jornadas oldIdJornadaOfPartidosListNewPartidos = partidosListNewPartidos.getIdJornada();
                    partidosListNewPartidos.setIdJornada(jornadas);
                    partidosListNewPartidos = em.merge(partidosListNewPartidos);
                    if (oldIdJornadaOfPartidosListNewPartidos != null && !oldIdJornadaOfPartidosListNewPartidos.equals(jornadas)) {
                        oldIdJornadaOfPartidosListNewPartidos.getPartidosList().remove(partidosListNewPartidos);
                        oldIdJornadaOfPartidosListNewPartidos = em.merge(oldIdJornadaOfPartidosListNewPartidos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = jornadas.getId();
                if (findJornadas(id) == null) {
                    throw new NonexistentEntityException("The jornadas with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jornadas jornadas;
            try {
                jornadas = em.getReference(Jornadas.class, id);
                jornadas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jornadas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Partidos> partidosListOrphanCheck = jornadas.getPartidosList();
            for (Partidos partidosListOrphanCheckPartidos : partidosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Jornadas (" + jornadas + ") cannot be destroyed since the Partidos " + partidosListOrphanCheckPartidos + " in its partidosList field has a non-nullable idJornada field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Ligas idLiga = jornadas.getIdLiga();
            if (idLiga != null) {
                idLiga.getJornadasList().remove(jornadas);
                idLiga = em.merge(idLiga);
            }
            em.remove(jornadas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Jornadas> findJornadasEntities() {
        return findJornadasEntities(true, -1, -1);
    }

    public List<Jornadas> findJornadasEntities(int maxResults, int firstResult) {
        return findJornadasEntities(false, maxResults, firstResult);
    }

    private List<Jornadas> findJornadasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Jornadas.class));
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

    public Jornadas findJornadas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Jornadas.class, id);
        } finally {
            em.close();
        }
    }

    public int getJornadasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Jornadas> rt = cq.from(Jornadas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
