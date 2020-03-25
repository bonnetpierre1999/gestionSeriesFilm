package fr.prive.gestionSeriesFilm.bo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Episode {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn (name = "saison_id")
	private Saison saison;
	
	private int numEpisode;
	
	private boolean vu;

	public Episode(Saison saison, int numEpisode, boolean vu) {
		super();
		this.saison = saison;
		this.numEpisode = numEpisode;
		this.vu = vu;
	}
	
	
	
}
