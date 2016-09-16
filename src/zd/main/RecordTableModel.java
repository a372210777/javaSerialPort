package zd.main;

import javax.swing.table.AbstractTableModel;

public class RecordTableModel extends AbstractTableModel {

	private String[] clumnName={"操作","时间","用户"};
	private String[][] data;
	
	public RecordTableModel(String[][] data){
		this.data=data;
	}
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.length;
	}

	@Override
	public String getColumnName(int column) {
		return clumnName[column];
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return clumnName.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		return data[rowIndex][columnIndex];
	}

}
