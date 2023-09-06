package COM.JRHCODES;

public class MarsRoverApp { /*

    Mission mission = new Mission(new Point(plateauSizeX, plateaySizeY));

    private static void createAndShowMainUI() {

        final JFrame displayFrame = new JFrame("Mars Rover Mission Control");
        File inputFile;
        displayFrame.setSize(500, 400);

        displayFrame.setVisible(true);
        displayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set flow layout for the frame
        displayFrame.getContentPane().setLayout(new FlowLayout());

        JButton button = new JButton("Select mission file");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputFile = selectMissionFile(displayFrame);
            }
        });

        displayFrame.getContentPane().add(button);

    }

    private static File selectMissionFile(final JFrame frame) {

        String filename = File.separator + "mars";
        JFileChooser fileChooser = new JFileChooser(new File(filename));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileFilter marsFileFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() || file.getName().toLowerCase().endsWith(".mars");
            }

            @Override
            public String getDescription() {
                return "Mars Mission Files (*.mars)";
            }
        };

        fileChooser.setFileFilter(marsFileFilter);


        // pop up an "Open File" file chooser dialog
        fileChooser.showOpenDialog(frame);

        return fileChooser.getSelectedFile();

    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                createAndShowMainUI();

            }

        });
    }






*/}