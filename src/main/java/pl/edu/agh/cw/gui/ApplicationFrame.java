package pl.edu.agh.cw.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import pl.edu.agh.cw.CwBrowser;
import pl.edu.agh.cw.dictionary.InteliCwDB;
import pl.edu.agh.cw.exceptions.FailedToGenerateCrosswordException;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class ApplicationFrame extends JFrame {
	private CwBrowser cwb = new CwBrowser(".");
	private InteliCwDB db = new InteliCwDB("cwdb.txt");
	boolean solve = false;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCwCount;
	private CrosswordPanel cwpanel;
	private JButton btnNextCw;
	private JButton btnPrevCw;
	private JLabel lblCwCount;
	private JButton btnLoadCws;
	private JButton btnDeleteCw;
	private JSpinner spinnerWidth;
	private JSpinner spinnerHeight;
	private JLabel lblWidth;
	private JLabel lblHeight;
	private JButton btnGenSimple;
	private JButton btnGenMultiCross;
	private JPanel menu;
	private JSplitPane splitPane;
	private JLabel lblCwIndex;
	private JButton btnSaveCw;
	private JButton btnPrintToPDF;
	private JButton btnCellHelp;
	private JButton btnCwHelp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicationFrame frame = new ApplicationFrame();
					frame.setVisible(true);
					frame.validate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ApplicationFrame() {
		setTitle("Generator krzy\u017C\u00F3wek");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000,600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		splitPane = new JSplitPane();
		contentPane.add(splitPane);
		
		menu = new JPanel();
		menu.setBackground(Color.LIGHT_GRAY);
		splitPane.setLeftComponent(menu);
		menu.setLayout(null);
		
		btnLoadCws = new JButton("Wczytaj krzy\u017C\u00F3wki");
		btnLoadCws.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser c = new JFileChooser();
				
				c.setCurrentDirectory(new java.io.File("."));
				c.setDialogTitle("Wybierz FOLDER z krzyzowkami");
				c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				c.setAcceptAllFileFilterUsed(false);
				
				if(c.showOpenDialog(contentPane) == JFileChooser.APPROVE_OPTION){
					cwb.setDir( c.getCurrentDirectory().getName() );
					try {
						cwb.loadAll();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(contentPane, "Wyst¹pi³ problem z wczytaniem krzy¿ówek!", "B³¹d!", JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}
				}
			}
		});
		btnLoadCws.setBounds(10, 11, 179, 23);
		menu.add(btnLoadCws);
		
		lblCwCount = new JLabel("Liczba krzy\u017C\u00F3wek:");
		lblCwCount.setBounds(10, 48, 128, 14);
		menu.add(lblCwCount);
		
		txtCwCount = new JTextField();
		txtCwCount.setText( Integer.toString(cwb.getCwCount()) );
		txtCwCount.setBounds(139, 45, 50, 20);
		menu.add(txtCwCount);
		txtCwCount.setColumns(10);
		
		btnPrevCw = new JButton("Poprzednia");
		btnPrevCw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnPrevCwActionPerfmormed(e);
			}
		});
		btnPrevCw.setBounds(0, 109, 99, 23);
		menu.add(btnPrevCw);
		
		btnNextCw = new JButton("Nast\u0119pna");
		btnNextCw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNextCwActionPerfmormed(e);
			}
		});
		
		btnNextCw.setBounds(99, 109, 99, 23);
		menu.add(btnNextCw);
		
		btnDeleteCw = new JButton("Usu\u0144");
		btnDeleteCw.setBounds(56, 143, 89, 23);
		menu.add(btnDeleteCw);
		
		spinnerWidth = new JSpinner();
		spinnerWidth.setModel(new SpinnerNumberModel(10, 4, 20, 1));
		spinnerWidth.setBounds(118, 177, 50, 20);
		menu.add(spinnerWidth);
		
		spinnerHeight = new JSpinner();
		spinnerHeight.setModel(new SpinnerNumberModel(10, 4, 20, 1));
		spinnerHeight.setBounds(118, 199, 50, 20);
		menu.add(spinnerHeight);
		
		lblWidth = new JLabel("Szeroko\u015B\u0107");
		lblWidth.setBounds(35, 177, 64, 14);
		menu.add(lblWidth);
		
		lblHeight = new JLabel("Wysoko\u015B\u0107");
		lblHeight.setBounds(35, 202, 64, 14);
		menu.add(lblHeight);
		
		btnGenSimple = new JButton("Generuj prost\u0105 krzy\u017C\u00F3wk\u0119");
		btnGenSimple.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnGenSimpleActionPerformed(evt);
			}
		});
		btnGenSimple.setBounds(10, 230, 179, 23);
		menu.add(btnGenSimple);
		
		btnGenMultiCross = new JButton("Generuj z\u0142o\u017Con\u0105 krzy\u017C\u00F3wk\u0119");
		btnGenMultiCross.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				btnGenMultiCrossActionPerformed(evt);
			}
		});
		btnGenMultiCross.setBounds(10, 264, 179, 23);
		menu.add(btnGenMultiCross);
		
		lblCwIndex = new JLabel("dupa");
		lblCwIndex.setText(new Integer(cwb.getIndex()).toString());
		lblCwIndex.setBounds(143, 84, 46, 14);
		menu.add(lblCwIndex);
		
		btnSaveCw = new JButton("Zapisz krzy\u017C\u00F3wk\u0119 do pliku");
		btnSaveCw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser c = new JFileChooser();
				
				c.setCurrentDirectory(new java.io.File("."));
				c.setDialogTitle("Wybierz folder do zapisu krzyzowki");
				c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				c.setAcceptAllFileFilterUsed(false);
				
				if(c.showSaveDialog(contentPane) == JFileChooser.APPROVE_OPTION){
					cwb.setDir( c.getCurrentDirectory().getName() );
					try {
						cwb.save(cwpanel.getCrossword());
					} catch (IOException ex) {
						JOptionPane.showMessageDialog(contentPane, "Wyst¹pi³ problem z zapisem krzy¿ówki!", "B³¹d!", JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
					}
				}
			}
		});
		btnSaveCw.setBounds(10, 482, 179, 23);
		menu.add(btnSaveCw);
		
		btnPrintToPDF = new JButton("Zapisz krzy\u017C\u00F3wk\u0119 do PDF");
		btnPrintToPDF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser c = new JFileChooser();
				
				c.setCurrentDirectory(new java.io.File("."));
				c.setDialogTitle("Wybierz sciezke do zapisu krzyzowki do pliku PDF");
				c.setFileFilter(new FileNameExtensionFilter("PDF", "pdf", "pdf"));
				
				if(c.showSaveDialog(contentPane) == JFileChooser.APPROVE_OPTION){
					Document document = new Document(PageSize.A4.rotate());
					try {
					    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(c.getSelectedFile()+".pdf"));
					    document.open();
					    PdfContentByte contentByte = writer.getDirectContent();
					    PdfTemplate template = contentByte.createTemplate(1000, 500);
					    Graphics2D g2 = template.createGraphics(1000, 500);
					    g2.setFont(new Font("Arial Unicode MS", Font.PLAIN, 12));
					    cwpanel.print(g2);
					    g2.dispose();
					    contentByte.addTemplate(template, 0, 0);
					} catch (Exception ex) {
					    ex.printStackTrace();
					}
					finally{
					    if(document.isOpen()){
					        document.close();
					    }
					}
				}
			}
		});
		btnPrintToPDF.setBounds(10, 516, 179, 23);
		menu.add(btnPrintToPDF);
		
		btnCellHelp = new JButton("Poka\u017C pole");
		btnCellHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cwpanel.solveCurrentCell();
			}
		});
		btnCellHelp.setBounds(10, 322, 135, 23);
		menu.add(btnCellHelp);
		
		btnCwHelp = new JButton("Rozwi\u0105\u017C krzy\u017C\u00F3wk\u0119");
		btnCwHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cwpanel.solveCrossword();
			}
		});
		btnCwHelp.setBounds(10, 356, 135, 23);
		menu.add(btnCwHelp);
		
		JLabel lblCwIndexLabel = new JLabel("Indeks krzy\u017C\u00F3wki:");
		lblCwIndexLabel.setBounds(10, 84, 119, 14);
		menu.add(lblCwIndexLabel);
		
		cwpanel = new CrosswordPanel();
		cwpanel.setBackground(Color.WHITE);
		splitPane.setRightComponent(cwpanel);
		cwpanel.setLayout(null);
		splitPane.setDividerLocation(200);
	}
	
	public JPanel getCwpanel() {
		return cwpanel;
	}
	public JButton getBtnNextCw() {
		return btnNextCw;
	}
	public JButton getBtnPrevCw() {
		return btnPrevCw;
	}
	public JTextField getTxtCwCount() {
		return txtCwCount;
	}
	public JLabel getLblCwCount() {
		return lblCwCount;
	}
	public JButton getBtnLoadCws() {
		return btnLoadCws;
	}
	public JButton getBtnDeleteCw() {
		return btnDeleteCw;
	}
	public JPanel getMenu() {
		return menu;
	}
	public JSplitPane getSplitPane() {
		return splitPane;
	}
	
	public void btnGenSimpleActionPerformed(ActionEvent evt){
		try{
			cwpanel.setCrossword(cwb.generateSimple((Integer)spinnerWidth.getValue(), (Integer)spinnerHeight.getValue(), db));
		}catch(FailedToGenerateCrosswordException ex){
			JOptionPane.showMessageDialog(contentPane, ex.getMessage(), "B³¹d!", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
		
		txtCwCount.setText(new Integer(cwb.getCwCount()).toString());
		lblCwIndex.setText(new Integer(cwb.getIndex()).toString());
		cwpanel.revalidate();
		cwpanel.repaint();
		//cwpanel.getCrossword().printCrossword();
	}
	
	public void btnGenMultiCrossActionPerformed(ActionEvent evt){
		try{
			cwpanel.setCrossword(cwb.generateMultiCross((Integer)spinnerWidth.getValue(), (Integer)spinnerHeight.getValue(), db));
		}catch(FailedToGenerateCrosswordException ex){
			JOptionPane.showMessageDialog(contentPane, ex.getMessage(), "B³¹d!", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		}
	
		txtCwCount.setText(new Integer(cwb.getCwCount()).toString());
		lblCwIndex.setText(new Integer(cwb.getIndex()).toString());
		cwpanel.revalidate();
		cwpanel.repaint();
		//cwpanel.getCrossword().printCrossword();
	}

	public void btnPrevCwActionPerfmormed(ActionEvent evt){
		if(cwb.getIndex() > 1){
			cwpanel.setCrossword(cwb.getCrossword(cwb.getIndex()-1));
			cwb.setIndex(cwb.getIndex()-1);
			lblCwIndex.setText(new Integer(cwb.getIndex()).toString());
			cwpanel.revalidate();
			cwpanel.repaint();
		}
	}
	
	public void btnNextCwActionPerfmormed(ActionEvent evt){
		if(cwb.getIndex() < cwb.getCwCount()){
			cwpanel.setCrossword(cwb.getCrossword(cwb.getIndex()+1));
			cwb.setIndex(cwb.getIndex()+1);
			lblCwIndex.setText(new Integer(cwb.getIndex()).toString());
			cwpanel.revalidate();
			cwpanel.repaint();
		}
	}
}
