package formularios;

import clases.FilterType;
import clases.MusicTags;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import jaco.mp3.player.MP3Player;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.swing.*;
import jmusic.JMusicPlayerList;
import jmusic.JMusicSong;
import jmusic.MusicPlayerControl;

/**
 *
 * @author Samuel David Ortiz
 */
public class FrmMusicPlayer implements ActionListener{

    private JFrame player;
    private JPanel panelInfo, panelPlayer;
    private ImageIcon Img;
    private Icon icono;
    private JLabel play, stop, fwd, prev, ps, abrir;
    //private JMenuBar menuBar;
    private JMenu menu, smenu;
    private JMenuItem e1, e2;

    private boolean press = false;

    File songFile;
    String currentDirectory = "home.user";
    String currentPath;
    String imagePath;
    
    private Media archivo;
    private MediaPlayer repro;
    int seleccion, cont = 0, contAuxNext = 1;

    public FrmMusicPlayer() {
        
        
        Player();
        PlayPause();
    }

    public void Player() {
        player = new JFrame("UMG Music Player 1.0");
        player.setSize(600, 350);
        player.setLayout(null);
        player.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        player.setLocationRelativeTo(null);
        player.setResizable(false);

        // <editor-fold defaultstate="collapsed" desc="Menu">
        JMenuBar menuBar = new JMenuBar();
        menu = new JMenu("Archivo");
        e1 = new JMenuItem("Abrir");
        e1.addActionListener(this); 
        menu.add(e1);
        
        menuBar.add(menu);
        player.setJMenuBar(menuBar);
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Panel info">
        panelInfo = new JPanel();
        panelInfo.setSize(570, 50);
        panelInfo.setLocation(5, 0);
        panelInfo.setLayout(null);
        panelInfo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelInfo.setVisible(true);
        player.add(panelInfo, 0);
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Panel Reproductor"> 
        panelPlayer = new JPanel();
        panelPlayer.setSize(player.getWidth(), 100);
        panelPlayer.setLocation(0, 180);
        panelPlayer.setLayout(null);
        panelPlayer.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelPlayer.setVisible(true);
        player.add(panelPlayer, 0);
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Boton play">
        play = new JLabel();
        play.setSize((panelPlayer.getWidth() / 10), 70);
        play.setLocation((panelPlayer.getWidth() / 2) - play.getWidth() / 2, 15);
        //play.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Img = new ImageIcon("./src/images/play.png");
        icono = new ImageIcon(Img.getImage().getScaledInstance(play.getWidth(), play.getHeight(),
                Image.SCALE_DEFAULT));

        play.setIcon(icono);
        play.setVisible(true);
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Boton siguiente">
        fwd = new JLabel();
        fwd.setSize((panelPlayer.getWidth() / 10), 70);
        fwd.setLocation(play.getX() + play.getWidth() + 10, play.getY());
        Img = new ImageIcon("./src/images/fast-forward.png");
        icono = new ImageIcon(Img.getImage().getScaledInstance(play.getWidth() - 15, play.getHeight() - 15,
                Image.SCALE_DEFAULT));

        fwd.setIcon(icono);
        fwd.setVisible(true);
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Boton anterior">
        prev = new JLabel();
        prev.setSize((panelPlayer.getWidth() / 10), 70);
        prev.setLocation(play.getX() - play.getWidth() + 5, play.getY());
        Img = new ImageIcon("./src/images/previous.png");
        icono = new ImageIcon(Img.getImage().getScaledInstance(play.getWidth() - 15, play.getHeight() - 15,
                Image.SCALE_DEFAULT));

        prev.setIcon(icono);
        prev.setVisible(true);
        // </editor-fold>
                
        panelPlayer.add(play, 0);
        panelPlayer.add(prev, 0);
        panelPlayer.add(fwd, 0);

        player.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == e1){
            //JOptionPane.showMessageDialog(null, "Hola");
            JFileChooser openFile = new JFileChooser(currentDirectory);
            openFile.setFileFilter(new FilterType(".mp3", "Abrir solo mp3"));
            int result = openFile.showOpenDialog(null);
            if(result == JFileChooser.APPROVE_OPTION){
                songFile = openFile.getSelectedFile();
                currentDirectory = songFile.getAbsolutePath();
                try {
                    MusicTags tags = new MusicTags(currentDirectory);
                    System.out.println(tags.getArtist());
                    System.out.println(tags.getTitle());
                } catch (IOException ex) {
                    Logger.getLogger(FrmMusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedTagException ex) {
                    Logger.getLogger(FrmMusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidDataException ex) {
                    Logger.getLogger(FrmMusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                System.out.println(currentDirectory);
                
            }
        }
    }
    

    public void PlayPause() {
        play.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (!isPress()) {
                    Img = new ImageIcon("./src/images/pause.png");
                    setPress(true);
                } else {
                    Img = new ImageIcon("./src/images/play.png");
                    setPress(false);
                }

                icono = new ImageIcon(Img.getImage().getScaledInstance(play.getWidth(), play.getHeight(),
                        Image.SCALE_DEFAULT));

                play.setIcon(icono);
            }
        });
        
        fwd.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                System.out.println("holaaaa");
            }
        });
    }

    public boolean isPress() {
        return press;
    }

    public void setPress(boolean press) {
        this.press = press;
    }

    private MP3Player mp3Player() {
        MP3Player mp3Player = new MP3Player();
        return mp3Player;
    }

}
