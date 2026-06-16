package mix_db.view.mainWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import mix_db.data.dao.Review;
import mix_db.data.dao.User;
import mix_db.view.FrameIcon;

public class AnaliticsView extends JFrame{

    private final JPanel mainPanel;
    private final Map<JTextArea, JButton> rowsList;


    public AnaliticsView() {
        this.rowsList = new HashMap<>();

        this.mainPanel = new ScrollablePanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.Y_AXIS));
        this.mainPanel.setOpaque(false);


        final JScrollPane scrollPane = new JScrollPane(this.mainPanel);
        scrollPane.setBorder(null);

        scrollPane.getVerticalScrollBar().setUnitIncrement(20); 

        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);

        this.setTitle("ADMIN - Analitiche Utenti");
        
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width / 3, screenSize.height / 2);
        
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        new FrameIcon(this);
        this.setVisible(true);
    }

    public void populate(Map<User, List<Review>> usersAnalitics, ActionListener al) {

        this.mainPanel.removeAll();
        this.rowsList.clear();

        if (usersAnalitics == null || usersAnalitics.isEmpty()) {
            final JTextArea emptyText = new JTextArea("Lista utenti vuota");
            emptyText.setEditable(false);
            emptyText.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            this.mainPanel.add(emptyText);
        } else {
            for (Map.Entry<User, List<Review>> entry : usersAnalitics.entrySet()) {
                final User user = entry.getKey();
                final List<Review> reviewsList = entry.getValue();

                final StringBuilder sb = new StringBuilder();
                sb.append("ID UTENTE: ").append(user.getUserID()).append("\n")
                    .append("NOME COMPLETO: ").append(user.getName().toUpperCase()).append(" ").append(user.getSurname().toUpperCase()).append("\n")
                    .append("EMAIL: ").append(user.getEmail()).append("\n")
                    .append("--------------------------------------------------\n")
                    .append("RECENSIONI EFFETTUATE: (").append(reviewsList.size()).append("):\n");

                if (reviewsList.isEmpty()) {
                    sb.append("Nessuna recensione effettuata");
                } else {
                    for (Review r : reviewsList) {
                        sb.append("- Data: ").append(r.getReviewDate())
                          .append(" | Voto: ").append(r.getScore()).append("/5\n")
                          .append("  Descrizione: \"").append(r.getDescription()).append("\"\n\n");
                    }
                }

                final JTextArea textArea = new JTextArea(sb.toString().trim());
                textArea.setEditable(false);
                textArea.setLineWrap(true);
                textArea.setWrapStyleWord(true);
                textArea.setOpaque(false);
                textArea.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                textArea.setAlignmentX(Component.LEFT_ALIGNMENT);

                textArea.setColumns(10); 

                final JButton banButton = new JButton("Ban");

                banButton.setForeground(Color.red);
                banButton.putClientProperty("user", user); 
                banButton.addActionListener(al);

                final JPanel buttonContainer = new JPanel(new GridBagLayout());
                buttonContainer.setOpaque(false);
                buttonContainer.add(banButton);

                final JPanel rowPanel = new JPanel(new BorderLayout());
                rowPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
                rowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                rowPanel.add(textArea, BorderLayout.CENTER);
                rowPanel.add(buttonContainer, BorderLayout.EAST);

                this.mainPanel.add(rowPanel);

                this.rowsList.put(textArea, banButton);
            }
        }

        this.revalidate();
        this.repaint();
    }


    private static class ScrollablePanel extends JPanel implements javax.swing.Scrollable {
        
        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return this.getPreferredSize();
        }

        @Override
        public int getScrollableUnitIncrement(java.awt.Rectangle visibleRect, int orientation, int direction) {
            return 20;
        }

        @Override
        public int getScrollableBlockIncrement(java.awt.Rectangle visibleRect, int orientation, int direction) {
            return 20;
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return true; 
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
    }

}

