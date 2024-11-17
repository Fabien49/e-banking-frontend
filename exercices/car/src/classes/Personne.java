package classes;

public class Rectangle {
   public Double longueur;

   public Double largeur;

    public Rectangle() {
    }

    public Rectangle(Double longueur, Double largeur) {
        this.longueur = longueur;
        this.largeur = largeur;
    }

    public Double surface (){
        return this.largeur*this.longueur;
    }

    public Double perimetre (){
        return 2*(this.largeur+this.longueur);
    }

    public void afficher(){
        System.out.println("La surface du rectangle est : " + surface());
        System.out.println("Le périmètre du rectangle est : " + perimetre());
    }
}
