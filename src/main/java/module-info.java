module fr.isika.cad25.projets.P1_Annuaire_ISIKA {
    requires javafx.controls;
    requires org.apache.pdfbox;
    
    exports fr.isika.cad25.projets.P1_Annuaire_ISIKA.Programme;    
    
    opens fr.isika.cad25.projets.P1_Annuaire_ISIKA.Model to javafx.base;
}
