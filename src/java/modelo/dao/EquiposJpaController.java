/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.dao;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.entities.Ligas;
import modelo.entities.Estadisticas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.entities.Equipos;
import modelo.entities.Partidos;

/**
 *
 * @author martin
 */
public class EquiposJpaController implements Serializable {

    public EquiposJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Equipos equipos) {
        if (equipos.getEstadisticasList() == null) {
            equipos.setEstadisticasList(new ArrayList<Estadisticas>());
        }
        if (equipos.getPartidosList() == null) {
            equipos.setPartidosList(new ArrayList<Partidos>());
        }
        if (equipos.getPartidosList1() == null) {
            equipos.setPartidosList1(new ArrayList<Partidos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ligas idLiga = equipos.getIdLiga();
            if (idLiga != null) {
                idLiga = em.getReference(idLiga.getClass(), idLiga.getId());
                equipos.setIdLiga(idLiga);
            }
            List<Estadisticas> attachedEstadisticasList = new ArrayList<Estadisticas>();
            for (Estadisticas estadisticasListEstadisticasToAttach : equipos.getEstadisticasList()) {
                estadisticasListEstadisticasToAttach = em.getReference(estadisticasListEstadisticasToAttach.getClass(), estadisticasListEstadisticasToAttach.getId());
                attachedEstadisticasList.add(estadisticasListEstadisticasToAttach);
            }
            equipos.setEstadisticasList(attachedEstadisticasList);
            List<Partidos> attachedPartidosList = new ArrayList<Partidos>();
            for (Partidos partidosListPartidosToAttach : equipos.getPartidosList()) {
                partidosListPartidosToAttach = em.getReference(partidosListPartidosToAttach.getClass(), partidosListPartidosToAttach.getId());
                attachedPartidosList.add(partidosListPartidosToAttach);
            }
            equipos.setPartidosList(attachedPartidosList);
            List<Partidos> attachedPartidosList1 = new ArrayList<Partidos>();
            for (Partidos partidosList1PartidosToAttach : equipos.getPartidosList1()) {
                partidosList1PartidosToAttach = em.getReference(partidosList1PartidosToAttach.getClass(), partidosList1PartidosToAttach.getId());
                attachedPartidosList1.add(partidosList1PartidosToAttach);
            }
            equipos.setPartidosList1(attachedPartidosList1);
            em.persist(equipos);
            if (idLiga != null) {
                idLiga.getEquiposList().add(equipos);
                idLiga = em.merge(idLiga);
            }
            for (Estadisticas estadisticasListEstadisticas : equipos.getEstadisticasList()) {
                Equipos oldIdEquipoOfEstadisticasListEstadisticas = estadisticasListEstadisticas.getIdEquipo();
                estadisticasListEstadisticas.setIdEquipo(equipos);
                estadisticasListEstadisticas = em.merge(estadisticasListEstadisticas);
                if (oldIdEquipoOfEstadisticasListEstadisticas != null) {
                    oldIdEquipoOfEstadisticasListEstadisticas.getEstadisticasList().remove(estadisticasListEstadisticas);
                    oldIdEquipoOfEstadisticasListEstadisticas = em.merge(oldIdEquipoOfEstadisticasListEstadisticas);
                }
            }
            for (Partidos partidosListPartidos : equipos.getPartidosList()) {
                Equipos oldIdLocalOfPartidosListPartidos = partidosListPartidos.getIdLocal();
                partidosListPartidos.setIdLocal(equipos);
                partidosListPartidos = em.merge(partidosListPartidos);
                if (oldIdLocalOfPartidosListPartidos != null) {
                    oldIdLocalOfPartidosListPartidos.getPartidosList().remove(partidosListPartidos);
                    oldIdLocalOfPartidosListPartidos = em.merge(oldIdLocalOfPartidosListPartidos);
                }
            }
            for (Partidos partidosList1Partidos : equipos.getPartidosList1()) {
                Equipos oldIdVisitanteOfPartidosList1Partidos = partidosList1Partidos.getIdVisitante();
                partidosList1Partidos.setIdVisitante(equipos);
                partidosList1Partidos = em.merge(partidosList1Partidos);
                if (oldIdVisitanteOfPartidosList1Partidos != null) {
                    oldIdVisitanteOfPartidosList1Partidos.getPartidosList1().remove(partidosList1Partidos);
                    oldIdVisitanteOfPartidosList1Partidos = em.merge(oldIdVisitanteOfPartidosList1Partidos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Equipos equipos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Equipos persistentEquipos = em.find(Equipos.class, equipos.getId());
            Ligas idLigaOld = persistentEquipos.getIdLiga();
            Ligas idLigaNew = equipos.getIdLiga();
            List<Estadisticas> estadisticasListOld = persistentEquipos.getEstadisticasList();
            List<Estadisticas> estadisticasListNew = equipos.getEstadisticasList();
            List<Partidos> partidosListOld = persistentEquipos.getPartidosList();
            List<Partidos> partidosListNew = equipos.getPartidosList();
            List<Partidos> partidosList1Old = persistentEquipos.getPartidosList1();
            List<Partidos> partidosList1New = equipos.getPartidosList1();
            List<String> illegalOrphanMessages = null;
            for (Estadisticas estadisticasListOldEstadisticas : estadisticasListOld) {
                if (!estadisticasListNew.contains(estadisticasListOldEstadisticas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Estadisticas " + estadisticasListOldEstadisticas + " since its idEquipo field is not nullable.");
                }
            }
            for (Partidos partidosListOldPartidos : partidosListOld) {
                if (!partidosListNew.contains(partidosListOldPartidos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Partidos " + partidosListOldPartidos + " since its idLocal field is not nullable.");
                }
            }
            for (Partidos partidosList1OldPartidos : partidosList1Old) {
                if (!partidosList1New.contains(partidosList1OldPartidos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Partidos " + partidosList1OldPartidos + " since its idVisitante field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idLigaNew != null) {
                idLigaNew = em.getReference(idLigaNew.getClass(), idLigaNew.getId());
                equipos.setIdLiga(idLigaNew);
            }
            List<Estadisticas> attachedEstadisticasListNew = new ArrayList<Estadisticas>();
            for (Estadisticas estadisticasListNewEstadisticasToAttach : estadisticasListNew) {
                estadisticasListNewEstadisticasToAttach = em.getReference(estadisticasListNewEstadisticasToAttach.getClass(), estadisticasListNewEstadisticasToAttach.getId());
                attachedEstadisticasListNew.add(estadisticasListNewEstadisticasToAttach);
            }
            estadisticasListNew = attachedEstadisticasListNew;
            equipos.setEstadisticasList(estadisticasListNew);
            List<Partidos> attachedPartidosListNew = new ArrayList<Partidos>();
            for (Partidos partidosListNewPartidosToAttach : partidosListNew) {
                partidosListNewPartidosToAttach = em.getReference(partidosListNewPartidosToAttach.getClass(), partidosListNewPartidosToAttach.getId());
                attachedPartidosListNew.add(partidosListNewPartidosToAttach);
            }
            partidosListNew = attachedPartidosListNew;
            equipos.setPartidosList(partidosListNew);
            List<Partidos> attachedPartidosList1New = new ArrayList<Partidos>();
            for (Partidos partidosList1NewPartidosToAttach : partidosList1New) {
                partidosList1NewPartidosToAttach = em.getReference(partidosList1NewPartidosToAttach.getClass(), partidosList1NewPartidosToAttach.getId());
                attachedPartidosList1New.add(partidosList1NewPartidosToAttach);
            }
            partidosList1New = attachedPartidosList1New;
            equipos.setPartidosList1(partidosList1New);
            equipos = em.merge(equipos);
            if (idLigaOld != null && !idLigaOld.equals(idLigaNew)) {
                idLigaOld.getEquiposList().remove(equipos);
                idLigaOld = em.merge(idLigaOld);
            }
            if (idLigaNew != null && !idLigaNew.equals(idLigaOld)) {
                idLigaNew.getEquiposList().add(equipos);
                idLigaNew = em.merge(idLigaNew);
            }
            for (Estadisticas estadisticasListNewEstadisticas : estadisticasListNew) {
                if (!estadisticasListOld.contains(estadisticasListNewEstadisticas)) {
                    Equipos oldIdEquipoOfEstadisticasListNewEstadisticas = estadisticasListNewEstadisticas.getIdEquipo();
                    estadisticasListNewEstadisticas.setIdEquipo(equipos);
                    estadisticasListNewEstadisticas = em.merge(estadisticasListNewEstadisticas);
                    if (oldIdEquipoOfEstadisticasListNewEstadisticas != null && !oldIdEquipoOfEstadisticasListNewEstadisticas.equals(equipos)) {
                        oldIdEquipoOfEstadisticasListNewEstadisticas.getEstadisticasList().remove(estadisticasListNewEstadisticas);
                        oldIdEquipoOfEstadisticasListNewEstadisticas = em.merge(oldIdEquipoOfEstadisticasListNewEstadisticas);
                    }
                }
            }
            for (Partidos partidosListNewPartidos : partidosListNew) {
                if (!partidosListOld.contains(partidosListNewPartidos)) {
                    Equipos oldIdLocalOfPartidosListNewPartidos = partidosListNewPartidos.getIdLocal();
                    partidosListNewPartidos.setIdLocal(equipos);
                    partidosListNewPartidos = em.merge(partidosListNewPartidos);
                    if (oldIdLocalOfPartidosListNewPartidos != null && !oldIdLocalOfPartidosListNewPartidos.equals(equipos)) {
                        oldIdLocalOfPartidosListNewPartidos.getPartidosList().remove(partidosListNewPartidos);
                        oldIdLocalOfPartidosListNewPartidos = em.merge(oldIdLocalOfPartidosListNewPartidos);
                    }
                }
            }
            for (Partidos partidosList1NewPartidos : partidosList1New) {
                if (!partidosList1Old.contains(partidosList1NewPartidos)) {
                    Equipos oldIdVisitanteOfPartidosList1NewPartidos = partidosList1NewPartidos.getIdVisitante();
                    partidosList1NewPartidos.setIdVisitante(equipos);
                    partidosList1NewPartidos = em.merge(partidosList1NewPartidos);
                    if (oldIdVisitanteOfPartidosList1NewPartidos != null && !oldIdVisitanteOfPartidosList1NewPartidos.equals(equipos)) {
                        oldIdVisitanteOfPartidosList1NewPartidos.getPartidosList1().remove(partidosList1NewPartidos);
                        oldIdVisitanteOfPartidosList1NewPartidos = em.merge(oldIdVisitanteOfPartidosList1NewPartidos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = equipos.getId();
                if (findEquipos(id) == null) {
                    throw new NonexistentEntityException("The equipos with id " + id + " no longer exists.");
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
            Equipos equipos;
            try {
                equipos = em.getReference(Equipos.class, id);
                equipos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The equipos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Estadisticas> estadisticasListOrphanCheck = equipos.getEstadisticasList();
            for (Estadisticas estadisticasListOrphanCheckEstadisticas : estadisticasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Equipos (" + equipos + ") cannot be destroyed since the Estadisticas " + estadisticasListOrphanCheckEstadisticas + " in its estadisticasList field has a non-nullable idEquipo field.");
            }
            List<Partidos> partidosListOrphanCheck = equipos.getPartidosList();
            for (Partidos partidosListOrphanCheckPartidos : partidosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Equipos (" + equipos + ") cannot be destroyed since the Partidos " + partidosListOrphanCheckPartidos + " in its partidosList field has a non-nullable idLocal field.");
            }
            List<Partidos> partidosList1OrphanCheck = equipos.getPartidosList1();
            for (Partidos partidosList1OrphanCheckPartidos : partidosList1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Equipos (" + equipos + ") cannot be destroyed since the Partidos " + partidosList1OrphanCheckPartidos + " in its partidosList1 field has a non-nullable idVisitante field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Ligas idLiga = equipos.getIdLiga();
            if (idLiga != null) {
                idLiga.getEquiposList().remove(equipos);
                idLiga = em.merge(idLiga);
            }
            em.remove(equipos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Equipos> findEquiposEntities() {
        return findEquiposEntities(true, -1, -1);
    }

    public List<Equipos> findEquiposEntities(int maxResults, int firstResult) {
        return findEquiposEntities(false, maxResults, firstResult);
    }

    private List<Equipos> findEquiposEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Equipos.class));
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

    public Equipos findEquipos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Equipos.class, id);
        } finally {
            em.close();
        }
    }

    public int getEquiposCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Equipos> rt = cq.from(Equipos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
