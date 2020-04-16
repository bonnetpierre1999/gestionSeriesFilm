package fr.prive.gestionSeriesFilm.bo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

import fr.prive.gestionSeriesFilm.dal.daoMysql;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@NamedQueries({ 
	@NamedQuery(name = "listeSaisonById", query = "FROM Saison s where serie_id = :var order by numSaison"),
	@NamedQuery(name = "findNbSaisonBySerie", query = "SELECT count(*) FROM Saison s where serie_id = :var ")
})
public class Saison {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JsonManagedReference
	@ManyToOne
	@JoinColumn (name = "serie_id")
	private Serie serie;
	
	private int numSaison;
	
	@JsonBackReference
	@OneToMany(mappedBy = "saison", cascade = CascadeType.ALL)
	private List<Episode> episode = new ArrayList<>();

	public Saison(Serie serie, int numSaison) {
		super();
		this.serie = serie;
		this.numSaison = numSaison;
	}

	@Override
	public String toString() {
		return "Saison [id=" + id + ", serie=" + serie + ", numSaison=" + numSaison + "]";
	}
	
	public long getNbEpisode()
	{
		return daoMysql.AfficherNbEp(this.getId());
	}

	
}
