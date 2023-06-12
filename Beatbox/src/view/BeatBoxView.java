package view;

import model.BeatBoxMusicPresenter;
import model.BeatBoxUIModel;
import model.BeatBoxUIPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static javax.swing.BoxLayout.Y_AXIS;

public class BeatBoxView {
    JPanel mainPanel;
    JFrame applicationFrame;
    ArrayList<JCheckBox> checkBoxList;
    String[] instrumentNames;

    public BeatBoxView() {
        BeatBoxUIPresenter uiPresenter = new BeatBoxUIPresenter();
        this.checkBoxList = uiPresenter.fetchCheckBoxList();
        this.instrumentNames = uiPresenter.fetchInstrumentNames();
    }

    public void buildGUI() {


        //Instantiates the JFrame of the Application with necessary properties
        //Set JFrame properties


        //Instantiates the main JPanel with BorderLayout and Border


        //Instantiates CheckBox List
        checkBoxList = new ArrayList<JCheckBox>();

        //Instantiate Box to contain Buttons
        //Instantiates and adds Button to our Box (buttonBox), then attach listeners to each of them


        //Instantiates Box to contain 16 labels created from a for loop and based from an array of String of instrument names


        //Add all containers - buttonBox and nameBox - to our JPanel (background)
        //Add main JPanel to JFrame


        //Create a GridLayout of 16x16 dimension as Layout for a JPanel (mainPanel) and adds it to the main JPanel


        // Using a for loop, instantiates 256 unchecked JCheckboxes that will be added to an ArrayList of JCheckboxes (checkBoxList) and to the JPanel (mainPanel)


        //Calls function setUpMIDI()
        setUpMIDI();
    }

    private void createFrame(JFrame frame) {
        frame = new JFrame("Cyber BeatBox");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(50, 50, 300, 300);
        frame.pack();
        frame.setVisible(true);
    }

    private void setUpBackgroundPanel(JFrame frame, JPanel backgroundPanel) {
        BorderLayout layout = new BorderLayout();
        backgroundPanel = new JPanel(layout);
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.getContentPane().add(backgroundPanel);
    }

    private void setUpButtonBox(JPanel backgroundPanel) {
        Box buttonBox = new Box(Y_AXIS);

        JButton startButton = new JButton("Start");
        buttonBox.add(startButton);
        startButton.addActionListener(new BeatBoxView.MyStartListener());

        JButton stopButton = new JButton("Stop");
        buttonBox.add(stopButton);
        stopButton.addActionListener(new BeatBoxView.MyStopListener());

        JButton upTempoButton = new JButton("Tempo Up");
        buttonBox.add(upTempoButton);
        upTempoButton.addActionListener(new BeatBoxView.MyUpTempoListener());

        JButton downTempoButton = new JButton("Tempo Down");
        buttonBox.add(downTempoButton);
        downTempoButton.addActionListener(new BeatBoxView.MyDownTempoListener());

        backgroundPanel.add(BorderLayout.EAST, buttonBox);
    }

    private void setUpInstrumentLabelBox(JPanel backgroundPanel) {
        Box nameBox = new Box(Y_AXIS);
        for (int i = 0; i < 16; i++) {
            nameBox.add(new Label(instrumentNames[i]));
        }
        backgroundPanel.add(BorderLayout.WEST, nameBox);
    }

    private void setUpCheckBoxPanel(JPanel backgroundPanel) {
        GridLayout grid = new GridLayout(16, 16);
        grid.setVgap(1);
        grid.setHgap(2);
        mainPanel = new JPanel(grid);
        backgroundPanel.add(BorderLayout.CENTER, mainPanel);
        for (int i = 0; i < 256; i++) {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkBoxList.add(c);
            mainPanel.add(c);
        }
    }

    public class MyStartListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            buildTrackAndStart();
        }
    }

    public class MyStopListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            sequencer.stop();
        }
    }

    public class MyUpTempoListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor * 1.03));
        }
    }

    public class MyDownTempoListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            float tempoFactor = sequencer.getTempoFactor();
            sequencer.setTempoFactor((float) (tempoFactor * 0.97));
        }
    }
}


