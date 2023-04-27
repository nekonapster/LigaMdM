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
import modelo.entidades.Estadisticas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.entidades.Jornadas;
import modelo.entidades.Partidos;
import modelo.entidades.Equipos;
import modelo.entidades.Ligas;

/**
 *
 * @author martin
 */
public class LigasJpaController implements Serializable {

    public LigasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ligas ligas) {
        if (ligas.getEstadisticasList() == null) {
            ligas.setEstadisticasList(new ArrayList<Estadisticas>());
        }
        if (ligas.getJornadasList() == null) {
            ligas.setJornadasList(new ArrayList<Jornadas>());
        }
        if (ligas.getPartidosList() == null) {
            ligas.setPartidosList(new ArrayList<Partidos>());
        }
        if (ligas.getEquiposList() == null) {
            ligas.setEquiposList(new ArrayList<Equipos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Estadisticas> attachedEstadisticasList = new ArrayList<Estadisticas>();
            for (Estadisticas estadisticasListEstadisticasToAttach : ligas.getEstadisticasList()) {
                estadisticasListEstadisticasToAttach = em.getReference(estadisticasListEstadisticasToAttach.getClass(), estadisticasListEstadisticasToAttach.getId());
                attachedEstadisticasList.add(estadisticasListEstadisticasToAttach);
            }
            ligas.setEstadisticasList(attachedEstadisticasList);
            List<Jornadas> attachedJornadasList = new ArrayList<Jornadas>();
            for (Jornadas jornadasListJornadasToAttach : ligas.getJornadasList()) {
                jornadasListJornadasToAttach = em.getReference(jornadasListJornadasToAttach.getClass(), jornadasListJornadasToAttach.getId());
                attachedJornadasList.add(jornadasListJornadasToAttach);
            }
            ligas.setJornadasList(attachedJornadasList);
            List<Partidos> attachedPartidosList = new ArrayList<Partidos>();
            for (Partidos partidosListPartidosToAttach : ligas.getPartidosList()) {
                partidosListPartidosToAttach = em.getReference(partidosListPartidosToAttach.getClass(), partidosListPartidosToAttach.getId());
                attachedPartidosList.add(partidosListPartidosToAttach);
            }
            ligas.setPartidosList(attachedPartidosList);
            List<Equipos> attachedEquiposList = new ArrayList<Equipos>();
            for (Equipos equiposListEquiposToAttach : ligas.getEquiposList()) {
                equiposListEquiposToAttach = em.getReference(equiposListEquiposToAttach.getClass(), equiposListEquiposToAttach.getId());
                attachedEquiposList.add(equiposListEquiposToAttach);
            }
            ligas.setEquiposList(attachedEquiposList);
            em.persist(ligas);
            for (Estadisticas estadisticasListEstadisticas : ligas.getEstadisticasList()) {
                Ligas oldIdLigaOfEstadisticasListEstadisticas = estadisticasListEstadisticas.getIdLiga();
                estadisticasListEstadisticas.setIdLiga(ligas);
                estadisticasListEstadisticas = em.merge(estadisticasListEstadisticas);
                if (oldIdLigaOfEstadisticasListEstadisticas != null) {
                    oldIdLigaOfEstadisticasListEstadisticas.getEstadisticasList().remove(estadisticasListEstadisticas);
                    oldIdLigaOfEstadisticasListEstadisticas = em.merge(oldIdLigaOfEstadisticasListEstadisticas);
                }
            }
            for (Jornadas jornadasListJornadas : ligas.getJornadasList()) {
                Ligas oldIdLigaOfJornadasListJornadas = jornadasListJornadas.getIdLiga();
                jornadasListJornadas.setIdLiga(ligas);
                jornadasListJornadas = em.merge(jornadasListJornadas);
                if (oldIdLigaOfJornadasListJornadas != null) {
                    oldIdLigaOfJornadasListJornadas.getJornadasList().remove(jornadasListJornadas);
                    oldIdLigaOfJornadasListJornadas = em.merge(oldIdLigaOfJornadasListJornadas);
                }
            }
            for (Partidos partidosListPartidos : ligas.getPartidosList()) {
                Ligas oldIdLigaOfPartidosListPartidos = partidosListPartidos.getIdLiga();
                partidosListPartidos.setIdLiga(ligas);
                partidosListPartidos = em.merge(partidosListPartidos);
                if (oldIdLigaOfPartidosListPartidos != null) {
                    oldIdLigaOfPartidosListPartidos.getPartidosList().remove(partidosListPartidos);
                    oldIdLigaOfPartidosListPartidos = em.merge(oldIdLigaOfPartidosListPartidos);
                }
            }
            for (Equipos equiposListEquipos : ligas.getEquiposList()) {
                Ligas oldIdLigaOfEquiposListEquipos = equiposListEquipos.getIdLiga();
                equiposListEquipos.setIdLiga(ligas);
                equiposListEquipos = em.merge(equiposListEquipos);
                if (oldIdLigaOfEquiposListEquipos != null) {
                    oldIdLigaOfEquiposListEquipos.getEquiposList().remove(equiposListEquipos);
                    oldIdLigaOfEquiposListEquipos = em.merge(oldIdLigaOfEquiposListEquipos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ligas ligas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ligas persistentLigas = em.find(Ligas.class, ligas.getId());
            List<Estadisticas> estadisticasListOld = persistentLigas.getEstadisticasList();
            List<Estadisticas> estadisticasListNew = ligas.getEstadisticasList();
            List<Jornadas> jornadasListOld = persistentLigas.getJornadasList();
            List<Jornadas> jornadasListNew = ligas.getJornadasList();
            List<Partidos> partidosListOld = persistentLigas.getPartidosList();
            List<Partidos> partidosListNew = ligas.getPartidosList();
            List<Equipos> equiposListOld = persistentLigas.getEquiposList();
            List<Equipos> equiposListNew = ligas.getEquiposList();
            List<String> illegalOrphanMessages = null;
            for (Estadisticas estadisticasListOldEstadisticas : estadisticasListOld) {
                if (!estadisticasListNew.contains(estadisticasListOldEstadisticas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Estadisticas " + estadisticasListOldEstadisticas + " since its idLiga field is not nullable.");
                }
            }
            for (Jornadas jornadasListOldJornadas : jornadasListOld) {
                if (!jornadasListNew.contains(jornadasListOldJornadas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Jornadas " + jornadasListOldJornadas + " since its idLiga field is not nullable.");
                }
            }
            for (Partidos partidosListOldPartidos : partidosListOld) {
                if (!partidosListNew.contains(partidosListOldPartidos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Partidos " + partidosListOldPartidos + " since its idLiga field is not nullable.");
                }
            }
            for (Equipos equiposListOldEquipos : equiposListOld) {
                if (!equiposListNew.contains(equiposListOldEquipos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Equipos " + equiposListOldEquipos + " since its idLiga field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Estadisticas> attachedEstadisticasListNew = new ArrayList<Estadisticas>();
            for (Estadisticas estadisticasListNewEstadisticasToAttach : estadisticasListNew) {
                estadisticasListNewEstadisticasToAttach = em.getReference(estadisticasListNewEstadisticasToAttach.getClass(), estadisticasListNewEstadisticasToAttach.getId());
                attachedEstadisticasListNew.add(estadisticasListNewEstadisticasToAttach);
            }
            estadisticasListNew = attachedEstadisticasListNew;
            ligas.setEstadisticasList(estadisticasListNew);
            List<Jornadas> attachedJornadasListNew = new ArrayList<Jornadas>();
            for (Jornadas jornadasListNewJornadasToAttach : jornadasListNew) {
                jornadasListNewJornadasToAttach = em.getReference(jornadasListNewJornadasToAttach.getClass(), jornadasListNewJornadasToAttach.getId());
                attachedJornadasListNew.add(jornadasListNewJornadasToAttach);
            }
            jornadasListNew = attachedJornadasListNew;
            ligas.setJornadasList(jornadasListNew);
            List<Partidos> attachedPartidosListNew = new ArrayList<Partidos>();
            for (Partidos partidosListNewPartidosToAttach : partidosListNew) {
                partidosListNewPartidosToAttach = em.getReference(partidosListNewPartidosToAttach.getClass(), partidosListNewPartidosToAttach.getId());
                attachedPartidosListNew.add(partidosListNewPartidosToAttach);
            }
            partidosListNew = attachedPartidosListNew;
            ligas.setPartidosList(partidosListNew);
            List<Equipos> attachedEquiposListNew = new ArrayList<Equipos>();
            for (Equipos equiposListNewEquiposToAttach : equiposListNew) {
                equiposListNewEquiposToAttach = em.getReference(equiposListNewEquiposToAttach.getClass(), equiposListNewEquiposToAttach.getId());
                attachedEquiposListNew.add(equiposListNewEquiposToAttach);
            }
            equiposListNew = attachedEquiposListNew;
            ligas.setEquiposList(equiposListNew);
            ligas = em.merge(ligas);
            for (Estadisticas estadisticasListNewEstadisticas : estadisticasListNew) {
                if (!estadisticasListOld.contains(estadisticasListNewEstadisticas)) {
                    Ligas oldIdLigaOfEstadisticasListNewEstadisticas = estadisticasListNewEstadisticas.getIdLiga();
                    estadisticasListNewEstadisticas.setIdLiga(ligas);
                    estadisticasListNewEstadisticas = em.merge(estadisticasListNewEstadisticas);
                    if (oldIdLigaOfEstadisticasListNewEstadisticas != null && !oldIdLigaOfEstadisticasListNewEstadisticas.equals(ligas)) {
                        oldIdLigaOfEstadisticasListNewEstadisticas.getEstadisticasList().remove(estadisticasListNewEstadisticas);
                        oldIdLigaOfEstadisticasListNewEstadisticas = em.merge(oldIdLigaOfEstadisticasListNewEstadisticas);
                    }
                }
            }
            for (Jornadas jornadasListNewJornadas : jornadasListNew) {
                if (!jornadasListOld.contains(jornadasListNewJornadas)) {
                    Ligas oldIdLigaOfJornadasListNewJornadas = jornadasListNewJornadas.getIdLiga();
                    jornadasListNewJornadas.setIdLiga(ligas);
                    jornadasListNewJornadas = em.merge(jornadasListNewJornadas);
                    if (oldIdLigaOfJornadasListNewJornadas != null && !oldIdLigaOfJornadasListNewJornadas.equals(ligas)) {
                        oldIdLigaOfJornadasListNewJornadas.getJornadasList().remove(jornadasListNewJornadas);
                        oldIdLigaOfJornadasListNewJornadas = em.merge(oldIdLigaOfJornadasListNewJornadas);
                    }
                }
            }
            for (Partidos partidosListNewPartidos : partidosListNew) {
                if (!partidosListOld.contains(partidosListNewPartidos)) {
                    Ligas oldIdLigaOfPartidosListNewPartidos = partidosListNewPartidos.getIdLiga();
                    partidosListNewPartidos.setIdLiga(ligas);
                    partidosListNewPartidos = em.merge(partidosListNewPartidos);
                    if (oldIdLigaOfPartidosListNewPartidos != null && !oldIdLigaOfPartidosListNewPartidos.equals(ligas)) {
                        oldIdLigaOfPartidosListNewPartidos.getPartidosList().remove(partidosListNewPartidos);
                        oldIdLigaOfPartidosListNewPartidos = em.merge(oldIdLigaOfPartidosListNewPartidos);
                    }
                }
            }
            for (Equipos equiposListNewEquipos : equiposListNew) {
                if (!equiposListOld.contains(equiposListNewEquipos)) {
                    Ligas oldIdLigaOfEquiposListNewEquipos = equiposListNewEquipos.getIdLiga();
                    equiposListNewEquipos.setIdLiga(ligas);
                    equiposListNewEquipos = em.merge(equiposListNewEquipos);
                    if (oldIdLigaOfEquiposListNewEquipos != null && !oldIdLigaOfEquiposListNewEquipos.equals(ligas)) {
                        oldIdLigaOfEquiposListNewEquipos.getEquiposList().remove(equiposListNewEquipos);
                        oldIdLigaOfEquiposListNewEquipos = em.merge(oldIdLigaOfEquiposListNewEquipos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ligas.getId();
                if (findLigas(id) == null) {
                    throw new NonexistentEntityException("The ligas with id " + id + " no longer exists.");
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
            Ligas ligas;
            try {
                ligas = em.getReference(Ligas.class, id);
                ligas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ligas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Estadisticas> estadisticasListOrphanCheck = ligas.getEstadisticasList();
            for (Estadisticas estadisticasListOrphanCheckEstadisticas : estadisticasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ligas (" + ligas + ") cannot be destroyed since the Estadisticas " + estadisticasListOrphanCheckEstadisticas + " in its estadisticasList field has a non-nullable idLiga field.");
            }
            List<Jornadas> jornadasListOrphanCheck = ligas.getJornadasList();
            for (Jornadas jornadasListOrphanCheckJornadas : jornadasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ligas (" + ligas + ") cannot be destroyed since the Jornadas " + jornadasListOrphanCheckJornadas + " in its jornadasList field has a non-nullable idLiga field.");
            }
            List<Partidos> partidosListOrphanCheck = ligas.getPartidosList();
            for (Partidos partidosListOrphanCheckPartidos : partidosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ligas (" + ligas + ") cannot be destroyed since the Partidos " + partidosListOrphanCheckPartidos + " in its partidosList field has a non-nullable idLiga field.");
            }
            List<Equipos> equiposListOrphanCheck = ligas.getEquiposList();
            for (Equipos equiposListOrphanCheckEquipos : equiposListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Ligas (" + ligas + ") cannot be destroyed since the Equipos " + equiposListOrphanCheckEquipos + " in its equiposList field has a non-nullable idLiga field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(ligas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ligas> findLigasEntities() {
        return findLigasEntities(true, -1, -1);
    }

    public List<Ligas> findLigasEntities(int maxResults, int firstResult) {
        return findLigasEntities(false, maxResults, firstResult);
    }

    private List<Ligas> findLigasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ligas.class));
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

    public Ligas findLigas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ligas.class, id);
        } finally {
            em.close();
        }
    }

    public int getLigasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ligas> rt = cq.from(Ligas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
