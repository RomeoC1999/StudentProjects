package crstandard.gui;

/**
 *
 * @author Romeo Carrara
 * @since 17.12.2015
 */
public interface CRTextComponent {
    
    public void buildUpFont(int sizeFont);
    public void setCentralAlignment(boolean isCentral);
    public void createBorder();
    public void createBorder(int dim, java.awt.Color color);
    
    public void setFontName(String name);
    public void setFontStyle(int style);
    public void setFontSize(int size);
    
}
