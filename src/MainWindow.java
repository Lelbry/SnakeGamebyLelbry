import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow(){
        setTitle("Игра Змекйка by Lelbry");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(400,400);
        setLocation(400,400);
        add(new GameField());
        setVisible(true);
    }
    public static void main(String[] aps){
        MainWindow mw = new MainWindow();
    }
}
