package lastfmmix;

import Support.Icons;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author mars
 */
public class Copyright{

    private JFrame  iFrame;
    private JPanel  iPanel;
    private JButton closeButton;
    private JLabel  iLabel;

    Copyright(){
        iFrame=new JFrame("Information");
        iFrame.setIconImage(Icons.Icon().getImage());
        iFrame.setPreferredSize(new Dimension(260,180));
        iFrame.setMinimumSize(new Dimension(260,180));
        iFrame.setResizable(false);
        iFrame.setLayout(new BorderLayout());

        closeButton=new JButton("Close");
        closeButton.setPreferredSize(new Dimension(100,25));
        closeButton.setMinimumSize(new Dimension(100,25));
        closeButton.addActionListener(new closeButton_Listener(this));

        iLabel=new JLabel();
        iLabel.setPreferredSize(new Dimension(240,140));
        iLabel.setIcon(Icons.Icon());
        iLabel.setText("<html><body>Last.fm jSubmitter v0.91<br/>"
                     + "Copyright (C) 2012-2014 Alexandrov Vitaly<br/><br/>"
                     + "Contacts:<br/>"
                     + "e-mail:mars-alex@mail.ru<br/>"
                     + "vkontakte.ru id: 33525017<br/></body></html>");

        iPanel=new JPanel();
        iPanel.add(closeButton);
        iFrame.add(iLabel,BorderLayout.CENTER);
        iFrame.add(iPanel,BorderLayout.SOUTH);

        iFrame.pack();
        iFrame.setLocationRelativeTo(null);
        iFrame.setVisible(true);
    }

    public void dispose(){
        iFrame.dispose();
    }

}

class closeButton_Listener implements ActionListener {

        Copyright info;

        closeButton_Listener(Copyright iframe) {
            info=iframe;
            }

        public void actionPerformed(ActionEvent e) {
                info.dispose();
            }
        }