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
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Saison {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn (name = "serie_id")
	private Serie serie;
	
	private int numSaison;
	
	@OneToMany(mappedBy = "saison", cascade = CascadeType.ALL)
	private List<Episode> episode = new ArrayList<>();

	public Saison(Serie serie, int numSaison) {
		super();
		this.serie = serie;
		this.numSaison = numSaison;
	}
	

	
}
