import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
class Pusta extends JComponent {
	int W; //szerokość całego obrazka
	int H; //wysokość całego obrazka
	Pusta() {
	}
	Pusta setDim(int W, int H) {
		this.W = W;
		this.H = H;
		setPreferredSize(new Dimension(W, H));
		return this;
	}
	public void paintComponent(Graphics rysunek) { //przygotowanie tła
		rysunek.setColor(Color.YELLOW);
		rysunek.fillRect(0, 0, getWidth(), getHeight());
		System.out.println(getWidth());
		System.out.println(getHeight());
	}
	
}


class Drzewo extends Pusta {
	int x; //szerokość na najniższym poziomie
	int y; //wysokość jednego poziomu
	int h; //wysokość drzewa w poziomach
	int r; //promień kółka z liczbą
	Drzewo (int x, int y, int h, int r) {
		this.x = x;
		this.y = y;
		this.h = h;
		this.r = r;
		H = h*y + 3*r;
		W = ((int)Math.pow(2, h-1))*x + 3*r;
		super.setDim(W, H);
	}	
		
		
		
		
		
	public void paintComponent(Graphics rysunek) {
	
	}
}
class Zestaw extends JPanel {
	 JPanel naZmienne[];
	 JButton naRysuj;
	Zestaw() {
		String labels[] = {"x", "y", "h", "r"};
		naZmienne = new JPanel[4];
		for(int n = 0; n!=4; n++) {
			naZmienne[n] = new JPanel();
			naZmienne[n].setLayout(new BorderLayout());
			naZmienne[n].add(new JLabel(labels[n]), "North");
			naZmienne[n].add(new TextField(), "South");
			naZmienne[n].setPreferredSize(new Dimension(30, 30));
			add(naZmienne[n]);
		}
		naRysuj = new JButton("Rysuj");
		add(naRysuj);
	}
}
	






class Rysunek extends JFrame implements ActionListener {
	 Container cp = getContentPane();
	 JScrollPane naDrzewo;
	 Zestaw naReszte;
	 Rysunek() {
		 naDrzewo = new JScrollPane();
		 naReszte = new Zestaw();
		 cp.add(naDrzewo, "South");
		 cp.add(naReszte, "North");
		 naReszte.setLayout(new FlowLayout());
		 naDrzewo.setPreferredSize(new Dimension(500, 500));
		 naDrzewo.setViewportView(new Pusta().setDim(400, 400));
		 setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		 pack();
		 setVisible(true);
	 }
	 public void actionPerformed(ActionEvent e) {
	 }
	 public static void main(String Ara[]) {
		 new Rysunek();
	 }
 }
	 
