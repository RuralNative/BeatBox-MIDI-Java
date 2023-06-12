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
    BeatBoxMusicPresenter musicPresenter;
    BeatBoxUIPresenter uiPresenter;
    JFrame applicationFrame;
    JPanel backgroundPanel;
    JPanel checkBoxPanel;
    ArrayList<JCheckBox> checkBoxList;
    String[] instrumentNames;

    public BeatBoxView() {
        uiPresenter = new BeatBoxUIPresenter();
        this.checkBoxList = uiPresenter.fetchCheckBoxList();
        this.instrumentNames = uiPresenter.fetchInstrumentNames();
        buildGUI();

        this.musicPresenter = new BeatBoxMusicPresenter();
        musicPresenter.setUpMIDI();
    }

    private void buildGUI() {
        createFrame();
        setUpBackgroundPanel(applicationFrame);
        setUpButtonBox(backgroundPanel);
        setUpInstrumentLabelBox(backgroundPanel);
        setUpCheckBoxPanel(backgroundPanel);
    }

    private void createFrame() {
        this.applicationFrame = new JFrame("Cyber BeatBox");
        applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        applicationFrame.setBounds(50, 50, 300, 300);
        applicationFrame.pack();
        applicationFrame.setVisible(true);
    }

    private void setUpBackgroundPanel(JFrame frame) {
        BorderLayout layout = new BorderLayout();
        this.backgroundPanel = new JPanel(layout);
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
        checkBoxPanel = new JPanel(grid);
        backgroundPanel.add(BorderLayout.CENTER, checkBoxPanel);
        for (int i = 0; i < 256; i++) {
            JCheckBox c = new JCheckBox();
            c.setSelected(false);
            checkBoxList.add(c);
            checkBoxPanel.add(c);
        }
        uiPresenter.setCheckBoxList(this.checkBoxList);
        System.out.println(uiPresenter.fetchCheckBoxList().size());
    }

    public class MyStartListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            musicPresenter.buildTrackAndStart();
        }
    }

    public class MyStopListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            musicPresenter.stopSequence();
        }
    }

    public class MyUpTempoListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            musicPresenter.upTempo();
        }
    }

    public class MyDownTempoListener implements ActionListener {
        public void actionPerformed(ActionEvent a) {
            musicPresenter.downTempo();
        }
    }
}


