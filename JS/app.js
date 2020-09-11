/* Fonction filtrant les nom des séries */
function filtreSeries() {
  var input, filter, table, tr, td, td2, i, txtValue, txtValue2;
  input = document.getElementById("myInput");
  filter = input.value.toUpperCase();
  table = document.getElementById("myTable");
  tr = table.getElementsByTagName("tr");
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[0];
    td2 =tr[i].getElementsByTagName("td")[1];
    if (td || td2) {
      txtValue = td.textContent || td.innerText ;
      txtValue2 = td2.textContent || td2.innerText
      if (txtValue.toUpperCase().indexOf(filter) > -1 || txtValue2.toUpperCase() == filter) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }       
  }
}

/* Affiche la flèche en bas à droite permettant de remonter en haut */
jQuery(document).ready(function() {
  var duration = 500;
  jQuery(window).scroll(function() {
    if (jQuery(this).scrollTop() > 100) {
      // Si un défillement de 100 pixels ou plus.
      // Ajoute le bouton
      jQuery('.cRetour').fadeIn(duration);
    } else {
      // Sinon enlève le bouton
      jQuery('.cRetour').fadeOut(duration);
    }
  });		
  jQuery('.cRetour').click(function(event) {
    // Un clic provoque le retour en haut animé.
    event.preventDefault();
    jQuery('html, body').animate({scrollTop: 0}, duration);
    return false;
  })
});
