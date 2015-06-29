package com.twit.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author root
 */
@Entity
@Table(name = "crawler")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Crawler.findAll", query = "SELECT c FROM Crawler c"),
    @NamedQuery(name = "Crawler.findById", query = "SELECT c FROM Crawler c WHERE c.id = :id"),
    @NamedQuery(name = "Crawler.findByData", query = "SELECT c FROM Crawler c WHERE c.dataCriacao = :dataCriacao")})
public class Crawler implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    @GeneratedValue(generator = "crawler_id_seq")
    @SequenceGenerator(name = "crawler_id_seq", sequenceName = "crawler_id_seq", allocationSize = 1)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data")
    private Date dataCriacao;

    @OneToMany(mappedBy = "idcrawler")
    private Collection<Produto> produtoCollection;

    @Column(name = "path")
    private String path;

    public Crawler() {
    }

    public Crawler(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<Produto> getProdutoCollection() {
        return produtoCollection;
    }

    public void setProdutoCollection(Collection<Produto> produtoCollection) {
        this.produtoCollection = produtoCollection;
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
        if (!(object instanceof Crawler)) {
            return false;
        }
        Crawler other = (Crawler) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.twit.entity.Crawler[ id=" + id + " ]";
    }

}
