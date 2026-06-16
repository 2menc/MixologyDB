package mix_db.core;

import java.io.FileOutputStream;

import org.openpdf.text.Document;
import org.openpdf.text.Element;
import org.openpdf.text.Font;
import org.openpdf.text.Image;
import org.openpdf.text.List;
import org.openpdf.text.ListItem;
import org.openpdf.text.Paragraph;
import org.openpdf.text.pdf.BaseFont;
import org.openpdf.text.pdf.PdfWriter;

import mix_db.data.dao.Bar;
import mix_db.data.dao.Drink;
import mix_db.data.dao.User;
import mix_db.data.dbConnection.DAOException;

/**
 * Provides services for exporting drink information into PDF files.
 * This class handles the generation of PDF documents containing details about a specific drink,
 * its creator, associated bar, and keywords.
 */
public class FileExportService {

    private final static String FONT_NAME = "Symbola";

    /** 0-args constructor */
    public FileExportService() { }

    /**
     * generates a .pdf file with all drink informations
     * @param drink the drink to be exported
     * @param creator the user who created the drink
     * @param bar the bar where the drink was created, or null
     * @param keywords the list of keywords associated with the drink
     * @param outputPath the file path where the PDF will be saved
     */
    public static void createPdf(Drink drink, User creator, Bar bar, java.util.List<String> keywords, String outputPath) {

        final Document document = new Document();

        try {
            final FileOutputStream fileStream = new FileOutputStream(outputPath);
            PdfWriter.getInstance(document, fileStream);

            document.open();
            
            // *font
            //final BaseFont font = BaseFont.createFont(GeneralSettings.fontiPath + 
                    //FONT_NAME + ".ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);    //? not working with jar

            byte[] fontBytes;
            try (java.io.InputStream is = FileExportService.class.getResourceAsStream("/fonts/Symbola.ttf")) {

                if (is == null) {
                    throw new java.io.FileNotFoundException("Font " + FONT_NAME + " non trovato nelle risorse del jar");
                }
                fontBytes = is.readAllBytes();
            }
            final BaseFont font = BaseFont.createFont("Symbola.ttf", 
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, fontBytes, null);    //? baseFont from the informations extracted from the jar

            final Font titleFont = new Font(font, 30, Font.BOLD);
            final Font textFont = new Font(font, 16, Font.NORMAL);
            final Font italicFont = new Font(font, 16, Font.ITALIC);
            final Font listFont = new Font(font, 13, Font.NORMAL);


            final Paragraph title = new Paragraph("🍹" + drink.getName() + "🍹", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(8);
            document.add(title);

            try {
                final Image image = Image.getInstance(GeneralSettings.fotoPath + drink.getImagePath());
                image.scaleToFit(250, 180);
                image.setAlignment(Element.ALIGN_LEFT); 
                image.setSpacingAfter(10);               
                document.add(image);
            } catch (Exception e) {
                throw new DAOException("Drink image not found in: " + GeneralSettings.fotoPath + drink.getImagePath());
            }


            final Paragraph drinkName = new Paragraph("🍸Nome: " + drink.getName(), textFont);
            drinkName.setSpacingAfter(5);
            document.add(drinkName);

            final Paragraph drinkCreator;
            if(drink.isIBA()) {
                drinkCreator = new Paragraph("🧑🏻Creatore: ricetta IBA", textFont);
                drinkCreator.setSpacingAfter(15);
            } else {
                drinkCreator = new Paragraph("🧑🏻Creatore: " + creator.getName() + " " + creator.getSurname(), textFont);
                drinkCreator.setSpacingAfter(15);
            }

            if (bar != null) {
                drinkCreator.setSpacingAfter(5);
                document.add(drinkCreator);
                
                final Paragraph drinkBar = new Paragraph("🏪Creato presso: " + bar.getBarName() + " (" + bar.getCity() + ", " + bar.getAddress() + ")", textFont);
                drinkBar.setSpacingAfter(15);
                document.add(drinkBar);
            } else {
                drinkCreator.setSpacingAfter(15);
                document.add(drinkCreator);
            }

            final Paragraph description = new Paragraph("🖋️Descrizione: " + drink.getDescription(), italicFont);
            description.setSpacingAfter(15);
            document.add(description);

            final Paragraph cat = new Paragraph("☀️Categoria: " + drink.getCategoryName(), textFont);
            cat.setSpacingAfter(15);
            document.add(cat);

            final Paragraph kws = new Paragraph("🔑Parole chiave:\n", textFont);
            document.add(kws);
            final List list = new List(false, 20);
            for(var k: keywords) {
                final ListItem li = new ListItem(k, listFont);
                list.add(li);
            }
            document.add(list);
        } catch(Exception e) {
            throw new DAOException(e);
        } finally {
            try {
                if(document.isOpen()) {
                    document.close();
                }
            } catch (Exception e) {}
        }
    }
    
}