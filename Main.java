
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.beans.*;
import java.io.*;
import java.lang.reflect.*;
import java.text.ParseException;
import java.util.*;

import static java.lang.System.exit;
import static javax.swing.JOptionPane.showMessageDialog;


public class Main {
    private static JTextArea dateArea;
    private static JCheckBox zip;
    private static JFrame choiceFrame;
    private static JTextArea keyArea;
    private static JFrame deleteFrame;
    private static JMenuItem exit;
    private static JFrame saveFrame;
    private static JFrame frame;

    static class appendItemBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] args = new String[7];

            args[0] = personArea.getText();
            args[1] = sectionArea.getText();
            args[2] = exerciseArea.getText();
            args[3] = coachArea.getText();
            args[4] = dateArea.getText();
            args[5] = durationArea.getText();
            args[6] = priceArea.getText();
            for (int i = 0; i < 7; i++)
                if (args[i].equals("")) {
                    showMessageDialog(inputFrame, "Все поля должны быть заполнены!");
                    return;
                }
            try {
                appendFile(args, zip.isSelected());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (KeyNotUniqueException keyNotUniqueException) {
                keyNotUniqueException.printStackTrace();
            } catch (CustomException customException) {
                customException.printStackTrace();
            }
            inputFrame.setVisible(false);
        }
    }

    // The main program
    static ArrayList<Sportsman> array;

    private static JTextArea textArea = new JTextArea();

    public static FontChooser fChooser;
    private static JFrame inputFrame;
    private static JTextArea personArea;
    private static JTextArea sectionArea;
    private static JTextArea exerciseArea;
    private static JTextArea coachArea;
    private static JTextArea durationArea;
    private static JTextArea priceArea;

    public static void main(String[] args) {

        frame = new JFrame("Lab 8");

        // Handle window close requests by exiting the VM
        frame.addWindowListener(new WindowAdapter() { // Anonymous inner class
            public void windowClosing(WindowEvent e) {
                exit(0);
            }
        });

        textArea.setEditable(false);


        JMenuBar menubar = new JMenuBar();

        JMenu menu;
        JMenuItem item;
        // File/Quite
        menu = new JMenu("File");
        menubar.add(menu);

        JMenuItem open = new JMenuItem("Open");
        JFileChooser fileChooser = new JFileChooser();
        open.addActionListener(actionEvent -> {
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                textArea.setText(null);
                String[] strs = fileChooser.getSelectedFile().getName().split("\\.");
                if (!strs[strs.length - 1].equals("dat")) {
                    textArea.append("Choose file with extension .dat");
                }
                else
                    filename = fileChooser.getSelectedFile().getName();
            }
        });
        menu.add(open);

        JMenuItem save = new JMenuItem("Save data in file...");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createSaveFile();
            }
        });
        menu.add(save);

        exit = new JMenuItem("Quit");
        exit.addActionListener(actionEvent -> {
            Object[] options = {"Yes!", "No!"};
            int n = JOptionPane.showOptionDialog(frame,
                    "Exit application?",
                    "Exit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            if (n == JOptionPane.OK_OPTION) {
                deleteFile();
                frame.setVisible(false);
                System.exit(0);
            }
        });
        menu.add(exit);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //////////////////View
        menu = new JMenu("View");

        /////////////View/Font
        item = new JMenuItem("Font");
        fChooser = new FontChooser(frame);
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                fChooser.setVisible(true);
                textArea.setFont(fChooser.selectedFont);

            }
        });
        menu.add(item);
        menubar.add(menu);


        //////////////////View/Look and Feel


        JMenu plafmenu = new JMenu("Look and Feel");
        ButtonGroup radiogroup = new ButtonGroup();
        UIManager.LookAndFeelInfo[] plafs =
                UIManager.getInstalledLookAndFeels();
        for (int i = 0; i < plafs.length; i++) {
            String plafName = plafs[i].getName();
            final String plafClassName = plafs[i].getClassName();

            item = plafmenu.add(new JRadioButtonMenuItem(plafName));

            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        // Set the new look and feel
                        UIManager.setLookAndFeel(plafClassName);
                        // Tell each component to change its look-and-feel
                        SwingUtilities.updateComponentTreeUI(frame);
                        // Tell the frame to resize itself to the its
                        // children's new desired sizes
                        frame.pack();
                    } catch (Exception ex) {
                        showMessageDialog(frame,ex);
                    }
                }

            });

            // Only allow one menu item to be selected at once
            radiogroup.add(item);
        }
        menu.add(plafmenu);


        ///////////////////Help
        menu = new JMenu("Help");
        menubar.add(menu);
        ///////////////////Help/About
        item = new JMenuItem("About");

        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JDialog dialog = new JDialog(frame, "Info", true);
                JPanel panel = new JPanel();
                JTextArea text = new JTextArea("Student: Belevich Mikhail\nGroup: 13\nCourse: 2\n" +
                        "Faculty: FAMCS\nUniversity: BSU\nTask: 8. Пользователь спорткомплекса имеет атрибуты: ФИО, " +
                        "ID секции, вид упражнений, ФИО тренера, дату и время начала занятия, длительность занятия, стоимость занятия.");
                text.setSize(500, 200);
                panel.setSize(500, 200);

                dialog.setMinimumSize(new Dimension(600, 200));
                text.setLineWrap(true);
                panel.add(text);
                text.setEditable(false);

                JButton button = new JButton("OK");
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        dialog.dispose();
                    }
                });
                button.setSize(50, 50);
                button.isVisible();
                panel.add(button);

                dialog.add(panel);
                dialog.setResizable(false);
                dialog.setLocation(300, 300);

                dialog.setVisible(true);
                dialog.pack();
                dialog.addWindowListener(new WindowAdapter() { // Anonymous inner class
                    public void windowClosing(WindowEvent e) {
                        dialog.dispose();
                    }
                });
            }

        });
        menu.add(item);


///////////////////////////////////////////////////////////////////////////
        //Add
        JMenu commands = new JMenu("Commands");
        menubar.add(commands);

        JMenu addMenu = new JMenu("Append");

        JMenuItem addNew = new JMenuItem("Append data");
        addNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createInputFrame();
            }
        });

        commands.add(addMenu);
        addMenu.add(addNew);
        //addMenu.add(addNewCompressed);*/


        //////////////////////////////////////////////////////////////////////////////////
        JMenu printMenu = new JMenu("Print");
        //////////////////////////////////////////////////////////////////////////////////
        JMenu printAllRecords = new JMenu("Print all data");
        JMenu printAllRecordsReverse = new JMenu("Print all data reversed");
        /////////////////////////////////////////////////////////////////////////////////
        JMenuItem printUnsorted = new JMenuItem("Print all data unsorted");
        printUnsorted.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    printFile();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
            }
        });
        JMenuItem printSortedByPersonsFullName = new JMenuItem("Print all data sorted by personsFullName");
        printSortedByPersonsFullName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    printFile("p", false);
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        JMenuItem printSortedByPersonsFullNameR = new JMenuItem("Print all data sorted by personsFullName");
        printSortedByPersonsFullNameR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    printFile("p", true);
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        JMenuItem printSortedByCoachsFullName = new JMenuItem("Print all data sorted by coachsFullName");
        printSortedByCoachsFullName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    printFile("c", false);
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        JMenuItem printSortedByCoachsFullNameR = new JMenuItem("Print all data sorted by coachsFullName");
        printSortedByCoachsFullNameR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    printFile("c", true);
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        JMenuItem printSortedByDate = new JMenuItem("Print all data sorted by date");
        printSortedByDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    printFile("d", false);
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        JMenuItem printSortedByDateR = new JMenuItem("Print all data sorted by date");
        printSortedByDateR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    printFile("d", true);
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        commands.add(printMenu);
        printMenu.add(printAllRecords);
        printMenu.add(printAllRecordsReverse);
        printAllRecords.add(printUnsorted);
        printAllRecords.add(printSortedByPersonsFullName);
        printAllRecords.add(printSortedByCoachsFullName);
        printAllRecords.add(printSortedByDate);
        printAllRecordsReverse.add(printSortedByPersonsFullNameR);
        printAllRecordsReverse.add(printSortedByCoachsFullNameR);
        printAllRecordsReverse.add(printSortedByDateR);

//delete and find by key
        JMenu find = new JMenu("Find");
        JMenuItem findBy = new JMenuItem("Find by key...");
        findBy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createFindFrame();
            }
        });
        commands.add(find);
        find.add(findBy);

        JMenu delete = new JMenu("Delete");
        JMenuItem deleteAll = new JMenuItem("Delete all data");
        deleteAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteFile();
            }
        });
        JMenuItem deleteKey = new JMenuItem("Delete by key");
        deleteKey.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createDeleteFrame();
            }
        });
        commands.add(delete);
        delete.add(deleteAll);
        delete.add(deleteKey);


        frame.setJMenuBar(menubar);
        frame.pack();              //Make frame as big as its kids need
        frame.setVisible(true);    //Make the frame visible on the screen
        frame.setLocation(300, 300);
        frame.setMinimumSize(new Dimension(300, 300));
        JScrollPane scroller = new JScrollPane(textArea);

        frame.getContentPane().add(BorderLayout.CENTER, scroller);
        //plafmenu.setFont(fChooser.selectedFont);
        //menu.setFont(fChooser.selectedFont);
        //menubar.setFont(fChooser.selectedFont);
        //plafmenu.setFont(fChooser.selectedFont);
        ///////////////body
        String html = "<html>" +
                "<input>" +
                "</html>";
        // JEditorPane htmlP=new JEditorPane(html);

    }

    static void createDeleteFrame() {
        deleteFrame = new JFrame("Choose key");
        deleteFrame.setVisible(true);
        deleteFrame.setSize(200, 350);
        JPanel inputPanel = new JPanel(new GridLayout(0, 1));

        JLabel keyPanel = new JLabel("Enter key");
        keyArea = new JTextArea();
        keyArea.setColumns(15);

        final String[] criterionStr = {""};

        JLabel criterion = new JLabel("Choose criterion");
        ButtonGroup group3 = new ButtonGroup();
        JRadioButton deletePersons = new JRadioButton("Delete persons");
        deletePersons.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criterionStr[0] = "p";
            }
        });
        JRadioButton deleteCoaches = new JRadioButton("Delete coaches");
        deleteCoaches.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criterionStr[0] = "c";
            }
        });
        JRadioButton deleteDates = new JRadioButton("Delete dates");
        deleteDates.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criterionStr[0] = "d";
            }
        });
        group3.add(deletePersons);
        group3.add(deleteCoaches);
        group3.add(deleteDates);

        JButton enter = new JButton("Enter");
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //String str=criterionStr[0]+keyArea.getText();
                String[] args = new String[]{"-dk", criterionStr[0], keyArea.getText()};
                if (args[0].equals("") || args[1].equals("") || args[2].equals("")) {
                    showMessageDialog(choiceFrame, "Все поля должны быть заполнены!");
                    return;
                }
                try {
                    deleteFile(args);
                } catch (ClassNotFoundException | KeyNotUniqueException | IOException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
                deleteFrame.setVisible(false);
            }
        });

        deleteFrame.add(inputPanel);

        inputPanel.add(keyPanel);
        inputPanel.add(keyArea);

        inputPanel.add(criterion);
        inputPanel.add(deletePersons);
        inputPanel.add(deleteCoaches);
        inputPanel.add(deleteDates);

        inputPanel.add(enter);
    }

    static void createFindFrame() {
        choiceFrame = new JFrame("Choose key");
        choiceFrame.setVisible(true);
        choiceFrame.setSize(190, 350);
        JPanel inputPanel = new JPanel();

        JLabel keyPanel = new JLabel("Enter key");
        keyArea = new JTextArea();
        keyArea.setColumns(15);

        final String[] criterionStr = {""};

        JLabel criterion = new JLabel("Choose criterion");
        ButtonGroup group1 = new ButtonGroup();
        JRadioButton findPersons = new JRadioButton("Find persons");
        findPersons.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criterionStr[0] = "p";
            }
        });
        JRadioButton findCoaches = new JRadioButton("Find coaches");
        findCoaches.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criterionStr[0] = "c";
            }
        });
        JRadioButton findDates = new JRadioButton("Find dates");
        findDates.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criterionStr[0] = "d";
            }
        });
        group1.add(findPersons);
        group1.add(findCoaches);
        group1.add(findDates);

        final String[] comparisonStr = {""};
        JLabel comparison = new JLabel("Choose comparison");
        ButtonGroup group2 = new ButtonGroup();
        JRadioButton equal = new JRadioButton("Find equal...");
        equal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comparisonStr[0] = "-f";
            }
        });
        JRadioButton before = new JRadioButton("Find before...");
        before.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comparisonStr[0] = "-fl";
            }
        });
        JRadioButton after = new JRadioButton("Find after...");
        after.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comparisonStr[0] = "-fr";
            }
        });
        group2.add(equal);
        group2.add(before);
        group2.add(after);

        //final String[] str = {""};
        JButton enter = new JButton("Enter");
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //String str=criterionStr[0]+keyArea.getText();
                String[] args = new String[]{comparisonStr[0], criterionStr[0], keyArea.getText()};
                textArea.setText(null);
                if (args[0].equals("") || args[1].equals("") || args[2].equals("")) {
                    showMessageDialog(choiceFrame, "Все поля должны быть заполнены!");
                    return;
                }
                if (equal.isSelected()) {
                    try {
                        if (!findByKey(args)) {
                            //textArea.setText("");
                            textArea.setText("Key not found");
                        }
                    } catch (ClassNotFoundException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                } else if (before.isSelected()) {
                    try {
                        if (!findByKey(args, new KeyComp())) {
                            textArea.setText("Key not found");
                        }
                    } catch (ClassNotFoundException | IOException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                } else {
                    try {
                        if (!findByKey(args, new KeyCompReverse())) {
                            textArea.setText("Key not found");
                        }
                    } catch (ClassNotFoundException | IOException classNotFoundException) {
                        classNotFoundException.printStackTrace();
                    }
                }
                choiceFrame.setVisible(false);
            }
        });

        choiceFrame.add(inputPanel);

        inputPanel.add(keyPanel);
        inputPanel.add(keyArea);

        inputPanel.add(criterion);
        inputPanel.add(findPersons);
        inputPanel.add(findCoaches);
        inputPanel.add(findDates);

        inputPanel.add(comparison);
        inputPanel.add(equal);
        inputPanel.add(before);
        inputPanel.add(after);

        inputPanel.add(enter);
    }

    static void createSaveFile() {
        saveFrame = new JFrame("Save data");
        saveFrame.setVisible(true);
        saveFrame.setSize(200, 300);
        JPanel inputPanel = new JPanel(new GridLayout(0, 1));

        JLabel saveLabel = new JLabel("Enter file name");
        JTextArea saveArea = new JTextArea();

        JButton enter = new JButton("Enter");
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*try (FileWriter fw = new FileWriter(saveArea.getText() + ".dat")) {
                    saveData(fw);
                } catch (IOException ex) {
                    showMessageDialog(frame,);("Don't create save file!");
                }*/
                new File(filename).renameTo(new File(saveArea.getText() + ".dat"));
                filename=saveArea.getText() + ".dat";
                saveFrame.setVisible(false);
            }
        });

        saveFrame.add(inputPanel);
        inputPanel.add(saveLabel);
        inputPanel.add(saveArea);
        inputPanel.add(enter);
    }

    /*static void saveData(FileWriter file) throws FileNotFoundException {
        long pos;
        int rec = 0;
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            while ((pos = raf.getFilePointer()) < raf.length()) {
                boolean[] wasZipped = new boolean[]{false};
                Sportsman sportsman = (Sportsman) Buffer.readObject(raf, pos, wasZipped);
                file.write(sportsman + "\n");
            }
            System.out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/


    static void createInputFrame() {
        inputFrame = new JFrame("Enter data");
        inputFrame.setVisible(true);
        inputFrame.setSize(80, 450);
        JPanel inputPanel = new JPanel();

        JLabel personLabel = new JLabel("Person full name: ");
        personArea = new JTextArea();
        personArea.setColumns(15);

        JLabel sectionLabel = new JLabel("Section ID: ");
        sectionArea = new JTextArea();
        sectionArea.setColumns(15);

        JLabel exerciseLabel = new JLabel("Exercise type");
        exerciseArea = new JTextArea();
        exerciseArea.setColumns(15);

        JLabel coachLabel = new JLabel("Coach full name");
        coachArea = new JTextArea();
        coachArea.setColumns(15);


        //JLabel dateLabel = new JLabel("Дата выполнения");
        JLabel dateLabel = new JLabel("Date & time");
        dateArea = new JTextArea();
        dateArea.setColumns(15);

        JLabel durationLabel = new JLabel("Duration: ");
        durationArea = new JTextArea();
        durationArea.setColumns(15);

        JLabel priceLabel = new JLabel("Price: ");
        priceArea = new JTextArea();
        priceArea.setColumns(15);

        final boolean[] needZip = {false};
        zip = new JCheckBox("Needed to zip");
        zip.addActionListener((ActionListener) e -> needZip[0] = !needZip[0]);

        JButton appendItemBtn = new JButton("Add data");
        appendItemBtn.addActionListener(new appendItemBtnListener());
        inputPanel.add(personLabel);
        inputPanel.add(personArea);

        inputPanel.add(sectionLabel);
        inputPanel.add(sectionArea);

        inputPanel.add(exerciseLabel);
        inputPanel.add(exerciseArea);

        inputPanel.add(coachLabel);
        inputPanel.add(coachArea);

        inputPanel.add(dateLabel);
        inputPanel.add(dateArea);

        inputPanel.add(durationLabel);
        inputPanel.add(durationArea);

        inputPanel.add(priceLabel);
        inputPanel.add(priceArea);

        inputPanel.add(zip);

        inputPanel.add(appendItemBtn);

        inputFrame.getContentPane().add(BorderLayout.CENTER, inputPanel);

    }


    static String filename = "Sportsmen.dat";
    static final String filenameBak = "Sportsmen.~dat";
    static final String idxname = "Sportsmen.idx";
    static final String idxnameBak = "Sportsmen.~idx";

    // input file encoding:
    private static String encoding = "Cp866";
    private static PrintStream SportsmanOut = System.out;

    static Sportsman readSportsman(String[] args) throws IOException, CustomException {
        return Sportsman.read(args, SportsmanOut);
    }

    private static void deleteBackup() {
        new File(filenameBak).delete();
        new File(idxnameBak).delete();
    }

    static void deleteFile() {
        deleteBackup();
        new File(filename).delete();
        new File(idxname).delete();
    }

    private static void backup() {
        deleteBackup();
        new File(filename).renameTo(new File(filenameBak));
        new File(idxname).renameTo(new File(idxnameBak));
    }

    static boolean deleteFile(String[] args)
            throws ClassNotFoundException, IOException, KeyNotUniqueException {
        //-dk  {p|c|d} key      - clear data by key
        if (args.length != 3) {
            showMessageDialog(frame,"Invalid number of arguments");
            return false;
        }
        Vector<Long> poss = null;
        try (Index idx = Index.load(idxname)) {
            IndexBase pidx = indexByArg(args[1], idx);
            if (pidx == null) {
                return false;
            }
            if (!pidx.contains(args[2])) {
                showMessageDialog(frame,"Key not found: " + args[2]);
                return false;
            }
            poss = pidx.get(args[2]);
        }
        backup();
        Collections.sort(poss);
        try (Index idx = Index.load(idxname);
             RandomAccessFile fileBak = new RandomAccessFile(filenameBak, "rw");
             RandomAccessFile file = new RandomAccessFile(filename, "rw")) {
            boolean[] wasZipped = new boolean[]{false};
            long pos;
            while ((pos = fileBak.getFilePointer()) < fileBak.length()) {
                Sportsman Sportsman = (Sportsman)
                        Buffer.readObject(fileBak, pos, wasZipped);
                if (Collections.binarySearch(poss, pos) < 0) { // if not found in deleted
                    long ptr = Buffer.writeObject(file, Sportsman, wasZipped[0]);
                    idx.put(Sportsman, ptr);
                }
            }
        }
        return true;
    }

    static void appendFile(String[] args, Boolean zipped)
            throws FileNotFoundException, IOException, ClassNotFoundException,
            KeyNotUniqueException, CustomException {
        //Scanner fin = new Scanner(System.in, encoding);
        try (Index idx = Index.load(idxname);
             RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            Sportsman sportsman = readSportsman(args);
            if (sportsman == null)
                return;
            idx.test(sportsman);
            long pos = Buffer.writeObject(raf, sportsman, zipped);
            idx.put(sportsman, pos);
        }
    }

    private static void printRecord(RandomAccessFile raf, long pos)
            throws ClassNotFoundException, IOException {
        boolean[] wasZipped = new boolean[]{false};
        Sportsman sportsman = (Sportsman) Buffer.readObject(raf, pos, wasZipped);
        if (wasZipped[0]) {
            textArea.append(" compressed");
        }
        textArea.append(" record at position " + pos + ": \n" + sportsman + "\n");
    }

    private static void printRecord(RandomAccessFile raf, String key,
                                    IndexBase pidx) throws ClassNotFoundException, IOException {
        Vector<Long> poss = pidx.get(key);
        for (long pos : poss) {
            textArea.append("*** Key: " + key + " points to");
            printRecord(raf, pos);
        }
    }

    static void printFile()
            throws FileNotFoundException, IOException, ClassNotFoundException {
        textArea.setText(null);
        long pos;
        int rec = 0;
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            while ((pos = raf.getFilePointer()) < raf.length()) {
                textArea.append("#" + (++rec));
                printRecord(raf, pos);
            }
            System.out.flush();
        }
    }

    private static IndexBase indexByArg(String arg, Index idx) {
        IndexBase pidx = null;
        switch (arg) {
            case "p" -> pidx = idx.persons;
            case "c" -> pidx = idx.coachs;
            case "d" -> pidx = idx.dates;
            default -> showMessageDialog(frame,"Invalid index specified: " + arg);
        }
        return pidx;
    }

    static boolean printFile(String arg, boolean reverse)
            throws ClassNotFoundException, IOException {
        textArea.setText(null);
        try (Index idx = Index.load(idxname);
             RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            IndexBase pidx = indexByArg(arg, idx);
            if (pidx == null) {
                return false;
            }
            String[] keys =
                    pidx.getKeys(reverse ? new KeyCompReverse() : new KeyComp());
            for (String key : keys) {
                printRecord(raf, key, pidx);
            }
        }
        return true;
    }

    static boolean findByKey(String[] args)
            throws ClassNotFoundException, IOException {
        if (args.length != 3) {
            showMessageDialog(frame,"Invalid number of arguments");
            return false;
        }
        try (Index idx = Index.load(idxname);
             RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            IndexBase pidx = indexByArg(args[1], idx);
            if (!pidx.contains(args[2])) {
                showMessageDialog(frame,"Key not found: " + args[2]);
                return false;
            }
            printRecord(raf, args[2], pidx);
        }
        return true;
    }

    static boolean findByKey(String[] args, Comparator<String> comp)
            throws ClassNotFoundException, IOException {
        if (args.length != 3) {
            showMessageDialog(frame,"Invalid number of arguments");
            return false;
        }
        try (Index idx = Index.load(idxname);
             RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {
            IndexBase pidx = indexByArg(args[1], idx);
            if (!pidx.contains(args[2])) {
                showMessageDialog(frame,"Key not found: " + args[2]);
                return false;
            }
            String[] keys = pidx.getKeys(comp);
            for (String key : keys) {
                if (key.equals(args[2])) {
                    break;
                }
                printRecord(raf, key, pidx);
            }
        }
        return true;
    }
}


