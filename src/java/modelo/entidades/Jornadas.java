/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author martin
 */
@Entity
@Table(name = "jornadas")
@NamedQueries({
    @NamedQuery(name = "Jornadas.findAll", query = "SELECT j FROM Jornadas j"),
    @NamedQuery(name = "Jornadas.findById", query = "SELECT j FROM Jornadas j WHERE j.id = :id"),
    @NamedQuery(name = "Jornadas.findByNumero", query = "SELECT j FROM Jornadas j WHERE j.numero = :numero"),
    @NamedQuery(name = "Jornadas.findByFecha", query = "SELECT j FROM Jornadas j WHERE j.fecha = :fecha")})
public class Jornadas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "numero")
    private int numero;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "id_liga", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Ligas idLiga;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idJornada", fetch = FetchType.EAGER)
    private Collection<Partidos> partidosCollection;

    public Jornadas() {
    }

    public Jornadas(Integer id) {
        this.id = id;
    }

    public Jornadas(Integer id, int numero, Date fecha) {
        this.id = id;
        this.numero = numero;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Ligas getIdLiga() {
        return idLiga;
    }

    public void setIdLiga(Ligas idLiga) {
        this.idLiga = idLiga;
    }

    public Collection<Partidos> getPartidosCollection() {
        return partidosCollection;
    }

    public void setPartidosCollection(Collection<Partidos> partidosCollection) {
        this.partidosCollection = partidosCollection;
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
        if (!(object instanceof Jornadas)) {
            return false;
        }
        Jornadas other = (Jornadas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.entidades.Jornadas[ id=" + id + " ]";
    }
    
}
