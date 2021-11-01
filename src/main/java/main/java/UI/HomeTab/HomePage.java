package main.java.UI.HomeTab;

import main.java.UI.Resources.CustomColor;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class HomePage {

    public static JLayeredPane generateRoomSelectionContentArea() {
//        JPanel contentArea = new JPanel();
//
//        contentArea.setLayout(new BorderLayout());
//        contentArea.setBounds(600, 0, 600, 700);
//        contentArea.setBackground(Color.BLUE);

//        JPanel contentArea = new JPanel();
//        //contentArea.setPreferredSize(new Dimension( 2000,2000));
//        contentArea.setBounds(600, 0, 600, 1400);
//        //contentArea.setBackground(Color.BLUE);
//
//
////        JPanel someStupidThing = new JPanel();
////        someStupidThing.setBounds(0, 750, 100, 600);
////        someStupidThing.setBackground(Color.GREEN);
//
//        JTextArea textArea = new JTextArea(5,5);
//        textArea.setText("xx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\nxx\n");
//        JScrollPane scrollFrame = new JScrollPane(textArea);
//        //someStupidThing.setAutoscrolls(true);
//        scrollFrame.setPreferredSize(new Dimension( 600,700));
//        scrollFrame.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//
//        contentArea.add(scrollFrame, BorderLayout.CENTER);

        JLayeredPane contentArea = new JLayeredPane();
        contentArea.setBounds(600, 0, 600, 1400);


        JPanel scrollContent = new JPanel();
        scrollContent.setLayout(null);
        scrollContent.setBounds(0, 0, 600, 1400);
        //scrollContent.setBackground(Color.PINK);

        JScrollPane scrollFrame = new JScrollPane(scrollContent);
        //scrollFrame.setPreferredSize(new Dimension( 600,300));
        scrollFrame.setBounds(0, 0, 600, 700);
        scrollFrame.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollFrame.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JScrollBar scrollbar = new JScrollBar(JScrollBar.VERTICAL){
            @Override
            public boolean isVisible(){
                return true;
            }
        };

        //scrollbar.setBackground(CustomColor.LOGIN_CONTAINER_THEME);

        scrollFrame.setVerticalScrollBar(scrollbar);

        //contentArea.add(scrollContent);

        for (int i = 0; i < 100; i++) {
            JPanel panel = generateRoomSelectionBox(1, "Executive",
                    new Date(2021, 01, 01),
                    new Date(2020, 02, 01),
                    false,
                    (i*100)+(i*20));
            JTextArea textArea = new JTextArea();
            panel.add(textArea);

            scrollContent.add(panel);
            scrollContent.setComponentZOrder(panel, 0);
        }

        contentArea.add(scrollFrame);
        return contentArea;
    }

    public static JPanel generateRoomSelectionBox(int bedCnt, String roomType, Date fromDate, Date toDate, boolean isSmoking, int top) {
        JPanel box = new JPanel();
        box.setLayout(null);
        box.setBounds(20, top+20, 560, 100);
        box.setBackground(CustomColor.PURPLE_THEME_TXT);


        return box;
    }


}
