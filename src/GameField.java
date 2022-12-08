import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int Size = 320; //максимум яблок поместится в данное поелямоежт 20 штук
    private final int Dot_Size = 16; //величина 1 пикселя
    private final int All_Dots = 400; //20 яблок в длину * 20 яблок в ширину = 400
    private Image dot;
    private Image apple;
    private int appleX; //X позиция яблока
    private int appleY; //Y позиция яблока
    private int[] x = new int[All_Dots]; //Массив для хранения x
    private int[] y = new int[All_Dots];
    private int  dots; //Размер змейки
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;

    public GameField(){
    setBackground(Color.black);
    loadImages();
    initGame();
    addKeyListener(new FieldKeyListener());
    setFocusable(true); //Фокус нажатий клавишей на Поле
    }

    public void initGame(){
        dots = 3;  //Начальное значение
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i*Dot_Size; //48 так как кратно 16
            y[i] = 48;
        }
        timer = new Timer(250, this);
        timer.start();
        createApple();
    }
    public void createApple(){
        appleX = new Random().nextInt(20)*Dot_Size; //20 разных позиций и по х и по y
        appleY = new Random().nextInt(20)*Dot_Size;
    }
    public void loadImages(){
        ImageIcon iia = new ImageIcon("applev2.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);  //Стандапртная перерисвока всего компонента
        if(inGame){
            g.drawImage(apple,appleX,appleY,this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i],this);
            }
        }else{
            String str = "Game Over";
            g.setColor(Color.WHITE);
            g.drawString(str,125,Size/2);
        }
    }

    public void move(){
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){ //перемещение головы змеи
            x[0] -= Dot_Size;
        }if(right){
            x[0] += Dot_Size;
        }if(up){
            y[0] -= Dot_Size;
        }if(down){
            y[0] += Dot_Size;
        }
    }

    public void checkApple(){
        if(x[0] == appleX && y[0] == appleY){
            dots++;
            createApple();
        }
    }

    public void checkCollisions(){
        for (int i = dots; i > 0 ; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
            }
        }
            if(x[0]>Size){
                inGame = false;
            }
            if(x[0]<0){
                inGame = false;
            }
            if(y[0]>Size){
                inGame = false;
            }
            if(y[0]<0){
                inGame = false;
            }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){   //Условия на проверку бортиков яблока размер и тд
            checkApple();
            checkCollisions();
            move();
        }
        repaint(); //Перерисовка поля
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();  //Отслеживание какая клавиша была нажата
            if(key == KeyEvent.VK_LEFT && !right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && !left){
                right = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_UP && !down){
                up = true;
                left = false;
                right = false;
            }
            if(key == KeyEvent.VK_DOWN && !up){
                down = true;
                left = false;
                right = false;
            }
        }
    }
}
