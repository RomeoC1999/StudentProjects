package crstandard.gui;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Romeo Carrara
 * @version 1.0 (2016.02.27)
 */
public abstract class CRTable extends AbstractTableModel implements TableCellRenderer{

    
    @Override
    public abstract int getRowCount();
    
    @Override
    public abstract int getColumnCount();
    
    @Override
    public abstract Object getValueAt(int rowIndex, int columnIndex);
    
    @Override
    public abstract String getColumnName(int col);
    
    @Override
    public abstract Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column);
    
}
