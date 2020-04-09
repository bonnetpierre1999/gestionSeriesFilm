package fr.prive.gestionSeriesFilm.bo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.codehaus.jackson.annotate.JsonBackReference;

import fr.prive.gestionSeriesFilm.dal.daoMysql;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@NamedQueries({ 
	@NamedQuery(name = "findTousSerie", query = "FROM Serie s order by nom")
})
public class Serie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(length = 100)
	private String nom;
	
	@Column(length = 40)
	private String moyenDiffusion;
	
	private boolean serieFini;
	
	@JsonBackReference
	@OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
	private List<Saison> saison = new ArrayList<>();

	public Serie(String nom) {
		super();
		this.nom = nom;
	}
	
	public Serie(int id, String nom, String moyenDiffusion, boolean serieFini) {
		super();
		this.id = id;
		this.nom = nom;
		this.moyenDiffusion = moyenDiffusion;
		this.serieFini = serieFini;
	}

	@Override
	public String toString() {
		return "Serie [id=" + id + ", nom=" + nom + ", moyenDiffusion=" + moyenDiffusion + ", serieFini=" + serieFini
				+ "]";
	}
	
	public int getNbSaisons()
	{
		int nb=0;
		List<Saison> saisons = daoMysql.AffichageAllSaisonsByIdserie(this.getId());
		nb = saisons.size();
		return nb;
	}
	
	public int getNbEpNonVus()
	{
		int nb=0;
		List<Episode> episodes = daoMysql.AffichageAllEpisodesNonVuByIdSerie(this.getId());
		nb = episodes.size();
		return nb;
	}

	public Serie(String nom, String moyenDiffusion) {
		super();
		this.nom = nom;
		this.moyenDiffusion = moyenDiffusion;
	}

	
}
