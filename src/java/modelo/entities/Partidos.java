/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author martin
 */
@Entity
@Table(name = "partidos")
@NamedQueries({
    @NamedQuery(name = "Partidos.findAll", query = "SELECT p FROM Partidos p"),
    @NamedQuery(name = "Partidos.findById", query = "SELECT p FROM Partidos p WHERE p.id = :id"),
    @NamedQuery(name = "Partidos.findByFecha", query = "SELECT p FROM Partidos p WHERE p.fecha = :fecha"),
    @NamedQuery(name = "Partidos.findByPuntosLocal", query = "SELECT p FROM Partidos p WHERE p.puntosLocal = :puntosLocal"),
    @NamedQuery(name = "Partidos.findByPuntosVisitante", query = "SELECT p FROM Partidos p WHERE p.puntosVisitante = :puntosVisitante")})
public class Partidos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "puntos_local")
    private int puntosLocal;
    @Basic(optional = false)
    @Column(name = "puntos_visitante")
    private int puntosVisitante;
    @JoinColumn(name = "id_local", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Equipos idLocal;
    @JoinColumn(name = "id_visitante", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Equipos idVisitante;
    @JoinColumn(name = "id_jornada", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Jornadas idJornada;
    @JoinColumn(name = "id_liga", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Ligas idLiga;

    public Partidos() {
    }

    public Partidos(Integer id) {
        this.id = id;
    }

    public Partidos(Integer id, Date fecha, int puntosLocal, int puntosVisitante) {
        this.id = id;
        this.fecha = fecha;
        this.puntosLocal = puntosLocal;
        this.puntosVisitante = puntosVisitante;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getPuntosLocal() {
        return puntosLocal;
    }

    public void setPuntosLocal(int puntosLocal) {
        this.puntosLocal = puntosLocal;
    }

    public int getPuntosVisitante() {
        return puntosVisitante;
    }

    public void setPuntosVisitante(int puntosVisitante) {
        this.puntosVisitante = puntosVisitante;
    }

    public Equipos getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(Equipos idLocal) {
        this.idLocal = idLocal;
    }

    public Equipos getIdVisitante() {
        return idVisitante;
    }

    public void setIdVisitante(Equipos idVisitante) {
        this.idVisitante = idVisitante;
    }

    public Jornadas getIdJornada() {
        return idJornada;
    }

    public void setIdJornada(Jornadas idJornada) {
        this.idJornada = idJornada;
    }

    public Ligas getIdLiga() {
        return idLiga;
    }

    public void setIdLiga(Ligas idLiga) {
        this.idLiga = idLiga;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partidos)) {
            return false;
        }
        Partidos other = (Partidos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entidades.Partidos[ id=" + id + " ]";
    }
    
}
