package fr.prive.gestionSeriesFilm.bo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.codehaus.jackson.annotate.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({ 
	@NamedQuery(name = "listeEpisodeById", query = "FROM Episode e where saison_id = :var order by numEpisode")
})
public class Episode {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@JsonManagedReference
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

	
	
	@Override
	public String toString() {
		return "Episode [id=" + id + ", saison=" + saison + ", numEpisode=" + numEpisode + ", vu=" + vu + "]";
	}
	
	
	
}
