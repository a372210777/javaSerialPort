package zd.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class HistoryFrame extends JFrame {
	String data[][] = { { "½ô¼±²¥·Å", "2013.1.1", "admin" },
			{ "½ô¼±²¥·Å", "2013.1.1", "admin" }, { "½ô¼±²¥·Å", "2013.1.1", "admin" }, 
			{ "½ô¼±²¥·Å", "2013.1.1", "admin" },
			{ "½ô¼±²¥·Å", "2013.1.1", "admin" },
			{ "½ô¼±²¥·Å", "2013.1.1", "admin" },
			{ "½ô¼±²¥·Å", "2013.1.1", "admin" },
			{ "½ô¼±²¥·Å", "2013.1.1", "admin" },
			{ "½ô¼±²¥·Å", "2013.1.1", "admin" },
			{ "½ô¼±²¥·Å", "2013.1.1", "admin" },
			{ "½ô¼±²¥·Å", "2013.1.1", "admin" },
			{ "½ô¼±²¥·Å", "2013.1.1", "admin" },
			};

	public HistoryFrame() {
		
		JTable table = new JTable(new RecordTableModel(data));
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);

		// Add the scroll pane to this panel.
		add(scrollPane,BorderLayout.CENTER);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300,200 );
		setVisible(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(screenSize.width / 2 - WIDTH / 2, screenSize.height / 2
				- HEIGHT / 2);
	}

}
