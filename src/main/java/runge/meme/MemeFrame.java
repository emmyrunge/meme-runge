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

        JTextField upperText = new JTextField("Enter top text here");
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

        controller.addToList();

        JScrollPane listScroller = new JScrollPane(nameList,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        listScroller.setPreferredSize(new Dimension(200, 80));

        textPanel.add(listScroller, BorderLayout.EAST);
        controller.updateImage("Drake Hotline Bling");

        MouseListener mouseListener = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String selectedItem = nameList.getSelectedValue();
                    controller.updateImage(selectedItem);
                }
            }
        };
        nameList.addMouseListener(mouseListener);

        JButton refresh = new JButton("Refresh");
        lowerField.add(refresh, BorderLayout.SOUTH);

        refresh.addActionListener(e ->
        {
            upperText.setEditable(true);
            upperText.setSize(1000, 30);
            upperText.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
            upperText.setForeground(Color.BLACK);

            lowerText.setEditable(true);
            lowerText.setSize(1000, 30);
            lowerText.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
            lowerText.setForeground(Color.BLACK);
            lowerText.setBackground(Color.WHITE);
            controller.updateImage("Drake Hotline Bling");
        });

        updateButton.addActionListener(e ->
        {
            upperText.setEditable(false);
            upperText.setSize(800, 50);
            upperText.setFont(new Font(Font.MONOSPACED, Font.BOLD, 48));
            upperText.setForeground(Color.BLACK);

            lowerText.setEditable(false);
            lowerText.setSize(800, 50);
            lowerText.setFont(new Font(Font.MONOSPACED, Font.BOLD, 48));
            lowerText.setBackground(Color.BLACK);
            lowerText.setForeground(Color.WHITE);
        });
    }
}



/*

        JComboBox<String> boxOfImageNames = new JComboBox<>();
        //this is coming back as null, getMemes is returning null
        Memes[] memes = memeList.getData().getMemes();

        //add each name to the combobox
        for(int i = 0; i < memes.length; i++)
        {
            boxOfImageNames.addItem(memes[i].getName());
        }

        //if name/box is selected get name
        boxOfImageNames.addActionListener(e ->
        {
            //after getting image display name
            selectedMemeImage.setText((String) boxOfImageNames.getSelectedItem());
            //now get the image, how?
        });

        textPanel.add(boxOfImageNames, BorderLayout.WEST);
*/

//need to figure out if there is anything i need to do in the controller
//potentially get picture but wouldn't that be the same thing as before with calling back to api
//ask prof?

//controller.findImage(imageName.getText());
//upperText.setFont(new Font(Font.MONOSPACED, Font.BOLD, 26));
//upperText.setForeground(Color.WHITE);
//lowerText.setFont(new Font(Font.MONOSPACED, Font.BOLD, 26));
//lowerText.setForeground(Color.WHITE);