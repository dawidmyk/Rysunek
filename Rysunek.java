import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
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
class Vertex extends Point {
	Vertex parent;
	Vertex leftSon;
	Vertex rightSon;
	boolean white;
	boolean whichi;
	Vertex(int x, int y) {
		super(x, y);
		white = false;
		whichi = false;
	}
	void connect(Vertex other, int side) {
		parent = other;
		if(side == 0) {
			whichi = false;
			other.leftSon = this;
		}
		else {
			whichi = true;
			other.rightSon = this;
		}
	}
	void comeLine(Graphics rysunek, int r) {
		rysunek.setColor(Color.BLUE);
		if(leftSon!=null) rysunek.drawLine(x, y, leftSon.x, leftSon.y);
		if(rightSon!=null) rysunek.drawLine(x, y, rightSon.x, rightSon.y);
		rysunek.setColor(Color.GREEN);
		rysunek.fillOval(x-r, y-r, 2*r, 2*r);
		rysunek.setColor(Color.RED);
		rysunek.drawOval(x-r, y-r, 2*r, 2*r);
	}
}
	
	
	

class Drzewo extends Pusta {
	int x; //szerokość na najniższym poziomie
	int y; //wysokość jednego poziomu
	int h; //wysokość drzewa w poziomach
	int r; //promień kółka z liczbą
	ArrayList Points;
	Drzewo (int params[]) {
		this(params[0], params[1], params[2], params[3]);
	}
	Drzewo (int x, int y, int h, int r) {
		this.x = (x+r)*2;
		this.y = y+2*r; //żeby nie było za mało
		this.h = h;
		this.r = r;
		Points = new ArrayList();
		H = h*this.y + 4*r;
		W = ((int)Math.pow(2, h))*this.x + 4*r;
		super.setDim(W, H);
	}
	void count_points() {
		Points.clear();
		int k = 1;
		int generalCount = 0;
		int element = W - 4*r;
		for(int i = -1; i!=h; i++) {
			element = element/2;
			for(int j = 0; j!=k; j++) {
				int x = 2*r + element*(2*j+1);
				int y = 2*r + this.y*(i+1);
				Vertex node = new Vertex(x, y);
				int place = generalCount - j - k/2 + j/2;
				if(i!=-1) node.connect((Vertex) Points.get(place), j%2);
				Points.add(node);
				generalCount++;
				
			}
			k = k*2;
		}
		
		System.out.println(W);
		System.out.println(H);
		ListIterator ire = Points.listIterator();
		while(ire.hasNext()) {
			System.out.println(ire.next());
		}
	 //debugowanie
	}
	
		
	public void paintComponent(Graphics rysunek) {
		super.paintComponent(rysunek);
		ListIterator ire = Points.listIterator();
		while(ire.hasNext()) ((Vertex) ire.next()).comeLine(rysunek, r);
			
	}
}
class Zestaw extends JPanel implements ActionListener {
	 JPanel naZmienne[];
	 JButton naRysuj;
	 JScrollPane naDrzewo;
	 Drzewo drzewko;
	Zestaw() {
		String labels[] = {"x", "y", "h", "r"};
		naZmienne = new JPanel[4];
		for(int n = 0; n!=4; n++) {
			naZmienne[n] = new JPanel();
			naZmienne[n].setLayout(new BorderLayout());
			naZmienne[n].add(new JLabel(labels[n]), "North");
			naZmienne[n].add(new JTextField(), "South");
			naZmienne[n].setPreferredSize(new Dimension(30, 30));
			add(naZmienne[n]);
		}
		naRysuj = new JButton("Rysuj");
		naRysuj.addActionListener(this);
		add(naRysuj);
	}
	void connect(JScrollPane naDrzewo) {
		this.naDrzewo = naDrzewo;
	}
	 public void actionPerformed(ActionEvent e) {
		 System.out.println("Widzę");
		 if(e.getSource()==naRysuj) {
			 int params[] = new int[4];
			 for(int i = 0; i!=4; i++) {
				 for(Component comp: naZmienne[i].getComponents()) {
					 if(comp instanceof JTextField) 
					 params[i] = Integer.parseInt(((JTextField) comp).getText());
					 System.out.println("Tutaj");
					 System.out.println(comp);

				}
			}
			for(int i : params) System.out.println(i);
			naDrzewo.setViewportView(drzewko = new Drzewo(params));
			drzewko.count_points();
			naDrzewo.repaint();
			 
		 }
	 }
		
		
}
	






class Rysunek extends JFrame {
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
		 //Drzewo drzewko = new Drzewo(15, 20, 3, 5);
		 naReszte.connect(naDrzewo);
		 //drzewko.count_points();
	 }
	
	 public static void main(String Ara[]) {
		 new Rysunek();
	 }
 }
	 
