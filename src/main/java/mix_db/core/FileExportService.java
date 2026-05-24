package mix_db.core;

import java.io.FileOutputStream;

import org.openpdf.text.Document;
import org.openpdf.text.Element;
import org.openpdf.text.Image;
import org.openpdf.text.List;
import org.openpdf.text.ListItem;
import org.openpdf.text.Paragraph;
import org.openpdf.text.pdf.PdfWriter;

import mix_db.data.dao.Drink;
import mix_db.data.dbConnection.DAOException;

/**
 * service to export drinks in pdf 
 */
public class FileExportService {

    /** 0-args constructor */
    public FileExportService() { }

    /**
     * generates a .pdf file with all drink informations
     * @param drink .
     * @param creator .
     * @param keywords .
     * @param outputPath .
     */
    public static void createPdf(Drink drink, String creator, java.util.List<String> keywords, String outputPath) {

        final Document document = new Document();

        try {
            final FileOutputStream fileStream = new FileOutputStream(outputPath);
            PdfWriter.getInstance(document, fileStream);

            document.open();

            final Paragraph title = new Paragraph("🍹" + drink.getName() + "🍹");
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


            final Paragraph drinkName = new Paragraph("🍸Nome: " + drink.getName());
            drinkName.setSpacingAfter(5);
            document.add(drinkName);

            if(drink.isIBA()) {
                final Paragraph drinkCreator = new Paragraph("🧑🏻Creatore: ricetta IBA");
                drinkCreator.setSpacingAfter(5);
                document.add(drinkCreator);
            } else {
                final Paragraph drinkCreator = new Paragraph("🧑🏻Creatore: " + creator);
                drinkCreator.setSpacingAfter(5);
                document.add(drinkCreator);
            }

            final Paragraph description = new Paragraph("🖋️Descrizione: " + drink.getDescription());
            description.setSpacingAfter(5);
            document.add(drinkName);

            final Paragraph cat = new Paragraph("☀️Categoria: " + drink.getCategoryName());
            cat.setSpacingAfter(15);
            document.add(cat);

            final Paragraph kws = new Paragraph("🔑Parole chiave:\n");
            document.add(kws);
            final List list = new List(false, 20);
            for(var k: keywords) {
                final ListItem li = new ListItem(k);
                list.add(li);
            }
            document.add(list);
        } catch(Exception e) {
            throw new DAOException(e);
        } finally {
            if(document.isOpen()) {
                document.close();
            }
        }
    }
    
}
