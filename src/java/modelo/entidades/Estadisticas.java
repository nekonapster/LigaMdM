/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.entidades;

import java.io.Serializable;
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

/**
 *
 * @author martin
 */
@Entity
@Table(name = "estadisticas")
@NamedQueries({
    @NamedQuery(name = "Estadisticas.findAll", query = "SELECT e FROM Estadisticas e"),
    @NamedQuery(name = "Estadisticas.findById", query = "SELECT e FROM Estadisticas e WHERE e.id = :id"),
    @NamedQuery(name = "Estadisticas.findByPartidosJugados", query = "SELECT e FROM Estadisticas e WHERE e.partidosJugados = :partidosJugados"),
    @NamedQuery(name = "Estadisticas.findByPartidosGanados", query = "SELECT e FROM Estadisticas e WHERE e.partidosGanados = :partidosGanados"),
    @NamedQuery(name = "Estadisticas.findByPartidosPerdidos", query = "SELECT e FROM Estadisticas e WHERE e.partidosPerdidos = :partidosPerdidos"),
    @NamedQuery(name = "Estadisticas.findByPuntosClasificacion", query = "SELECT e FROM Estadisticas e WHERE e.puntosClasificacion = :puntosClasificacion"),
    @NamedQuery(name = "Estadisticas.findByPuntosAnotados", query = "SELECT e FROM Estadisticas e WHERE e.puntosAnotados = :puntosAnotados"),
    @NamedQuery(name = "Estadisticas.findByPuntosEncajados", query = "SELECT e FROM Estadisticas e WHERE e.puntosEncajados = :puntosEncajados")})
public class Estadisticas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "partidos_jugados")
    private int partidosJugados;
    @Basic(optional = false)
    @Column(name = "partidos_ganados")
    private int partidosGanados;
    @Basic(optional = false)
    @Column(name = "partidos_perdidos")
    private int partidosPerdidos;
    @Basic(optional = false)
    @Column(name = "puntos_clasificacion")
    private int puntosClasificacion;
    @Basic(optional = false)
    @Column(name = "puntos_anotados")
    private int puntosAnotados;
    @Basic(optional = false)
    @Column(name = "puntos_encajados")
    private int puntosEncajados;
    @JoinColumn(name = "id_equipo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Equipos idEquipo;
    @JoinColumn(name = "id_liga", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Ligas idLiga;

    public Estadisticas() {
    }

    public Estadisticas(Integer id) {
        this.id = id;
    }

    public Estadisticas(Integer id, int partidosJugados, int partidosGanados, int partidosPerdidos, int puntosClasificacion, int puntosAnotados, int puntosEncajados) {
        this.id = id;
        this.partidosJugados = partidosJugados;
        this.partidosGanados = partidosGanados;
        this.partidosPerdidos = partidosPerdidos;
        this.puntosClasificacion = puntosClasificacion;
        this.puntosAnotados = puntosAnotados;
        this.puntosEncajados = puntosEncajados;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPartidosJugados() {
        return partidosJugados;
    }

    public void setPartidosJugados(int partidosJugados) {
        this.partidosJugados = partidosJugados;
    }

    public int getPartidosGanados() {
        return partidosGanados;
    }

    public void setPartidosGanados(int partidosGanados) {
        this.partidosGanados = partidosGanados;
    }

    public int getPartidosPerdidos() {
        return partidosPerdidos;
    }

    public void setPartidosPerdidos(int partidosPerdidos) {
        this.partidosPerdidos = partidosPerdidos;
    }

    public int getPuntosClasificacion() {
        return puntosClasificacion;
    }

    public void setPuntosClasificacion(int puntosClasificacion) {
        this.puntosClasificacion = puntosClasificacion;
    }

    public int getPuntosAnotados() {
        return puntosAnotados;
    }

    public void setPuntosAnotados(int puntosAnotados) {
        this.puntosAnotados = puntosAnotados;
    }

    public int getPuntosEncajados() {
        return puntosEncajados;
    }

    public void setPuntosEncajados(int puntosEncajados) {
        this.puntosEncajados = puntosEncajados;
    }

    public Equipos getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Equipos idEquipo) {
        this.idEquipo = idEquipo;
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
        if (!(object instanceof Estadisticas)) {
            return false;
        }
        Estadisticas other = (Estadisticas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entidades.Estadisticas[ id=" + id + " ]";
    }
    
}
