package Assignment2;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class StartMenu {
    // TODO: Remove all the "Magic number" fields in the methods in the class
    private final GameEngine engine;
    private Clip musicClip;
    private boolean isMusicPlaying = true;

    // Image of the background
    private Image background;
    private Image title;
    private Image musicButton;
    private final Image[] buttonImages = new Image[6];

    // Variables for mouse click position
    private final int[] BUTTON_X_POSITIONS = {150, 300, 140, 280, 20};
    private final int[] BUTTON_Y_POSITIONS = {180, 210, 280, 320, 500,};
//    private final boolean[] ButtonClicked = {false, false, false, false, false};

    // Constructor
    public StartMenu(GameEngine engine) {
        this.engine = engine;

        this.init();
    }
    
    public String menuMouseClicked(MouseEvent e) {
        System.out.println(" > ");
        double mouseX = e.getX();
        double mouseY = e.getY();

        // Handle menu button clicks
        for (int i = 0; i < 5; i++) {
            double ButtonX = this.BUTTON_X_POSITIONS[i];
            double ButtonY = this.BUTTON_Y_POSITIONS[i];
            double radius = 50; // TODO: this is a "magic number" = 50, replace with actual parameter fields

            switch(i) {
                case 0:
                    if (this.clickButton(mouseX, mouseY, ButtonX, ButtonY, radius)) {
                        // Single player was clicked
                        return "single_player";
                    }
//                    break;
                case 1:
                    if (this.clickButton(mouseX, mouseY, ButtonX, ButtonY, radius)) {
                        // Time attack was clicked
                        return "time_attack";
                    }
//                    break;
//                case 2:
//                    if (clickButton(mouseX, mouseY, ButtonX, ButtonY, radius)) {
//
//                    }
//                    break;
                case 3:
                    if (this.clickButton(mouseX, mouseY, ButtonX, ButtonY, radius)) {
                        // Quit was clicked
                        Timer timer = new Timer(1000, new ActionListener() {
                            // Click the Exit and wait 1 second then Exit the program
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.exit(0);
                            }
                        });
                        timer.setRepeats(false);
                        timer.start();
                    }
                    break;
//                case 4:
//                    if (clickButton(mouseX, mouseY, ButtonX, ButtonY, radius)) {
//
//                    }
//                    break;
                default:
                    break;
            }
        }

        // Handle music button toggle
        double musicButtonX = 475;
        double musicButtonY = 475;
        double musicButtonRadius = 25;
        if (this.clickButton(mouseX, mouseY, musicButtonX, musicButtonY, musicButtonRadius)) {
            this.toggleMusic();
        }

        return "nothing";
    }
    
    //check the mouse location
    public boolean clickButton(double mouseX, double mouseY, double buttonX, double buttonY, double radius) {
        double dx = mouseX - buttonX;
        double dy = mouseY - buttonY;
        return (dx * dx + dy * dy) <= (radius * radius);
    }
    
    //draw background
    public void drawBackground() {
        this.engine.saveCurrentTransform();
        this.engine.drawImage(this.background, 0, 0, 500, 500);
        this.engine.restoreLastTransform();
    }
    
    //draw title
    public void drawTitle() {
        this.engine.saveCurrentTransform();
        this.engine.drawImage(this.title, 110, 0, 300, 150);
        this.engine.restoreLastTransform();
    }
    
    //draw button
    public void drawButton() {
        for (int i = 0; i < 5; i++) {
            int buttonX = this.BUTTON_X_POSITIONS[i];
            int buttonY = this.BUTTON_Y_POSITIONS[i];

            this.engine.drawImage(this.buttonImages[i], buttonX-30, buttonY - 30, 120, 50);
        }
        this.engine.drawImage(this.musicButton,450,450,50,50);
    }
    
    public void initMusic() {
        try {
            //import the music file
            File soundFile = new File("Assignment2/assets/music/MENU.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

            //get the music format
            AudioFormat format = audioIn.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            this.musicClip = (Clip) AudioSystem.getLine(info);
            this.musicClip.open(audioIn);

        } catch(Exception e) {
            e.printStackTrace();

        }
    }
    
    //music play
    public void playMusic(){ this.musicClip.start(); }
    
    //control the music play
    public void pauseMusic(){ this.musicClip.stop(); }

//    @Override
    public void init() {
        this.background = this.engine.loadImage("Assignment2/assets/image/B1.png");
        this.title = this.engine.loadImage("Assignment2/assets/image/title.png");

        this.buttonImages[0] = this.engine.loadImage("Assignment2/assets/image/StartButton.png");
        this.buttonImages[1] = this.engine.loadImage("Assignment2/assets/image/TIMERACE.png");
        this.buttonImages[2] = this.engine.loadImage("Assignment2/assets/image/SETTINGS.png");
        this.buttonImages[3] = this.engine.loadImage("Assignment2/assets/image/QUIT.png");
        this.buttonImages[4] = this.engine.loadImage("Assignment2/assets/image/HELP.png");
        this.musicButton = this.engine.loadImage("Assignment2/assets/image/MUSICbutton.png");

        this.initMusic();
        this.playMusic();
    }

    public void toggleMusic() {
        if (this.isMusicPlaying) {
            this.pauseMusic();
        } else {
            this.playMusic();
        }
        this.isMusicPlaying = !this.isMusicPlaying;
    }

//    @Override
//    public void update(double dt) {
//
//    }
    public void drawAll() {
//        this.engine.setWindowSize(this.engine.width(), this.engine.height());
        this.drawBackground();
        this.drawTitle();
        this.drawButton();
    }

//    @Override
//    public void paintComponent() {
//        setWindowSize(500, 500);
//        drawBackground();
//        drawTitle();
//        drawButton();
//    }

//    @Override
//    public int width() {
//        return 500; // Set the window width to 500 pixels
//    }

//    @Override
//    public int height() {
//        return 500; // Set the window height to 500 pixels
//    }
}