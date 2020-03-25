package fr.prive.gestionSeriesFilm.bo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

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
	
	private int annee;
	
	@OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
	private List<Saison> saison = new ArrayList<>();

	public Serie(String nom, int annee) {
		super();
		this.nom = nom;
		this.annee = annee;
	}

	public Serie(int id, String nom, int annee) {
		super();
		this.id = id;
		this.nom = nom;
		this.annee = annee;
	}
	
	

}
