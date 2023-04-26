/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author martin
 */
@Entity
@Table(name = "ligas")
@NamedQueries({
    @NamedQuery(name = "Ligas.findAll", query = "SELECT l FROM Ligas l"),
    @NamedQuery(name = "Ligas.findById", query = "SELECT l FROM Ligas l WHERE l.id = :id"),
    @NamedQuery(name = "Ligas.findByNombre", query = "SELECT l FROM Ligas l WHERE l.nombre = :nombre")})
public class Ligas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLiga")
    private List<Estadisticas> estadisticasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLiga")
    private List<Jornadas> jornadasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLiga")
    private List<Partidos> partidosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLiga")
    private List<Equipos> equiposList;

    public Ligas() {
    }

    public Ligas(Integer id) {
        this.id = id;
    }

    public Ligas(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Estadisticas> getEstadisticasList() {
        return estadisticasList;
    }

    public void setEstadisticasList(List<Estadisticas> estadisticasList) {
        this.estadisticasList = estadisticasList;
    }

    public List<Jornadas> getJornadasList() {
        return jornadasList;
    }

    public void setJornadasList(List<Jornadas> jornadasList) {
        this.jornadasList = jornadasList;
    }

    public List<Partidos> getPartidosList() {
        return partidosList;
    }

    public void setPartidosList(List<Partidos> partidosList) {
        this.partidosList = partidosList;
    }

    public List<Equipos> getEquiposList() {
        return equiposList;
    }

    public void setEquiposList(List<Equipos> equiposList) {
        this.equiposList = equiposList;
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
        if (!(object instanceof Ligas)) {
            return false;
        }
        Ligas other = (Ligas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entidades.Ligas[ id=" + id + " ]";
    }
    
}
