package formularios;

import jaco.mp3.player.MP3Player;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Samuel David Ortiz
 */
public class FrmMusicPlayer implements ActionListener {

    private JFrame player;
    private JPanel panelLista, panelInfo, panelPlayer;
    private ImageIcon Img;
    private Icon icono;
    private JLabel play, stop, fwd, prev, aleat, abrir;
    private JMenu menu, smenu;
    private JMenuItem e1, e2;
    private JList lista;

    private boolean press = false;

    File songFile[];
    String currentDirectory = "home.user";
    String currentPath;
    String imagePath;

    private Media archivo;
    private MediaPlayer repro;
    private JFileChooser openFile;
    int seleccion, cont = 0, contAuxNext = 1, indice = 0, indiceAuxNext, indiceAuxPrev;
    private ArrayList arrayFormato = new ArrayList();
    private ArrayList arrayNombreMusica = new ArrayList();
    private ArrayList arrayRuta = new ArrayList();
    String rutaFormato, rutaNombreMusica;
    DefaultListModel modelo = new DefaultListModel();

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
        panelLista = new JPanel();
        panelLista.setSize(570, 150);
        panelLista.setLocation(5, 0);
        panelLista.setLayout(null);
        panelLista.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        panelLista.setVisible(true);
        player.add(panelLista, 0);
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
        
        // <editor-fold defaultstate="collapsed" desc="Boton aleatorio">
        aleat = new JLabel();
        aleat.setSize((panelPlayer.getWidth() / 10), 70);
        aleat.setLocation(10, play.getY());
        Img = new ImageIcon("./src/images/shuffle.png");
        icono = new ImageIcon(Img.getImage().getScaledInstance(play.getWidth() - 15, play.getHeight() - 15,
                Image.SCALE_DEFAULT));

        aleat.setIcon(icono);
        aleat.setVisible(true);
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="playlist">
        lista = new JList();
        lista.setLocation(10, 10);
        lista.setSize(panelLista.getWidth() - 20, panelLista.getHeight() - 20);
        lista.setVisible(true);
        // </editor-fold>
        
        panelLista.add(lista, 0);
        panelPlayer.add(play, 0);
        panelPlayer.add(prev, 0);
        panelPlayer.add(fwd, 0);
        panelPlayer.add(aleat, 0);

        player.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == e1) {
            SeleccionarMusica();
        }
    }

    public void SeleccionarMusica() {
        openFile = new JFileChooser();
        openFile.setFileFilter(new FileNameExtensionFilter("Archivos *MP3", "mp3"));
        openFile.setFileSelectionMode(JFileChooser.FILES_ONLY);
        openFile.setMultiSelectionEnabled(true);
        int selec = openFile.showOpenDialog(null);

        if (selec == JFileChooser.APPROVE_OPTION) {
            File files[] = openFile.getSelectedFiles();

            for (File file : files) {
                arrayNombreMusica.add(file.getName());
                arrayRuta.add(file.getPath());
                //DefaultListModel modelo = (DefaultListModel) lista.getModel();
                modelo.addElement(arrayNombreMusica.get(cont));
                lista.setModel(modelo);
                arrayFormato.add(file.toURI().toString());
                System.out.println(arrayFormato.get(cont));
                System.out.println(arrayRuta.get(cont));
                cont++;
            }
        } else {
            JOptionPane.showMessageDialog(null, "No selecciono ningun archivo");
        }

    }

    public void PlayPause() {
        play.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (arrayFormato.size() == 0) {
                    JOptionPane.showMessageDialog(null, "No hay musica para reproducir");
                } else {
                    if (!isPress()) {
                        Img = new ImageIcon("./src/images/pause.png");
                        indice = lista.getSelectedIndex();
                        if (repro != null) {
                            repro.play();
                        } else {
                            play(indice);
                        }
                        setPress(true);
                    } else {
                        Img = new ImageIcon("./src/images/play.png");
                        if (repro != null) {
                            repro.pause();
                        }
                        setPress(false);
                    }

                    icono = new ImageIcon(Img.getImage().getScaledInstance(play.getWidth(), play.getHeight(),
                            Image.SCALE_DEFAULT));

                    play.setIcon(icono);
                }

            }
        });

        fwd.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (arrayFormato.size() == 0) {
                    JOptionPane.showMessageDialog(null, "No hay musica para reproducir");
                } else {
                    if (repro == null) {
                        JOptionPane.showMessageDialog(null, "No hay musica reproduciondose");
                    } else {
                        repro.dispose();
                        indiceAuxNext = indice + contAuxNext;

                        if (indiceAuxNext < arrayFormato.size()) {
                            next(indiceAuxNext);
                            contAuxNext++;
                        } else {
                            contAuxNext = 0;
                        }
                    }

                }
            }
        });

        prev.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (arrayFormato.size() == 0) {
                    JOptionPane.showMessageDialog(null, "No hay musica para reproducir");
                } else {
                    if (repro == null) {
                        JOptionPane.showMessageDialog(null, "No hay musica reproduciondose");
                    } else {
                        indice--;
                        repro.dispose();
                        indiceAuxPrev = indice;

                        if (indiceAuxPrev >= 0) {
                            prev(indiceAuxPrev);

                        } else {
                            indice = arrayFormato.size() - 1;
                        }
                    }

                }
            }
        });
    }

    public void play(int indice) {
        com.sun.javafx.application.PlatformImpl.startup(() -> {
        });

        rutaFormato = (String) arrayFormato.get(indice).toString();
        rutaNombreMusica = (String) arrayNombreMusica.get(indice).toString();

        archivo = new Media(rutaFormato);
        repro = new MediaPlayer(archivo);
        repro.play();
    }

    public void next(int indiceAux) {
        com.sun.javafx.application.PlatformImpl.startup(() -> {
        });

        rutaFormato = (String) arrayFormato.get(indiceAux).toString();
        rutaNombreMusica = (String) arrayNombreMusica.get(indiceAux).toString();
        archivo = new Media(rutaFormato);
        repro = new MediaPlayer(archivo);
        repro.play();
    }

    public void prev(int indiceAux) {
        com.sun.javafx.application.PlatformImpl.startup(() -> {
        });

        rutaFormato = (String) arrayFormato.get(indiceAux).toString();
        rutaNombreMusica = (String) arrayNombreMusica.get(indiceAux).toString();
        archivo = new Media(rutaFormato);
        repro = new MediaPlayer(archivo);
        repro.play();
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
