package runge.meme;

import javax.inject.Inject;
import javax.inject.Named;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MemeFrame extends JFrame
{
    MemeList memeList;
    private final JList<String> nameList;
    private final DefaultListModel<String> listModel;

    @Inject
    public MemeFrame(
            MemeController controller,
            @Named("listModel") DefaultListModel<String> listModel,
            @Named("imageLabel") JLabel imageLabel
    )
    {
        this.listModel = listModel;
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        setSize(1000, 1000);
        setTitle("Meme Generator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);

        JPanel textPanel = new JPanel(new BorderLayout());
        JPanel lowerField = new JPanel(new BorderLayout());

        mainPanel.add(textPanel);

        textPanel.add(lowerField, BorderLayout.SOUTH);

        JTextField upperText = new JTextField("Enter upper text here");
        textPanel.add(upperText, BorderLayout.NORTH);

        JTextField lowerText = new JTextField("Enter lower text here");
        upperText.setSize(1000, 30);
        upperText.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
        lowerText.setSize(1000, 30);
        lowerText.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
        JLabel lowerLabel = new JLabel();
        lowerField.add(lowerLabel, BorderLayout.NORTH);
        JButton updateButton = new JButton("Update");
        lowerField.add(lowerText, BorderLayout.NORTH);
        lowerField.add(updateButton, BorderLayout.EAST);
        imageLabel.setSize(500, 450);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        textPanel.add(imageLabel, Image.SCALE_DEFAULT);

        nameList = new JList<>(listModel);
        nameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        nameList.setLayoutOrientation(JList.VERTICAL);

        JScrollPane listScroller = new JScrollPane(nameList,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        listScroller.setPreferredSize(new Dimension(200, 80));

        textPanel.add(listScroller, BorderLayout.EAST);
        controller.addAndUpdateImage("Drake Hotline Bling");

        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    String selectedItem = nameList.getSelectedValue();
                    controller.addAndUpdateImage(selectedItem);
                }
            }
        };
        nameList.addMouseListener(mouseListener);

        JButton refresh = new JButton("Refresh");
        lowerField.add(refresh, BorderLayout.SOUTH);

        refresh.addActionListener(e ->
        {
            controller.refreshMainPanel(upperText, lowerText);
        });

        updateButton.addActionListener(e ->
        {
            controller.updateText(upperText, lowerText);
        });
    }
}