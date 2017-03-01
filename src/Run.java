import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import javax.swing.*;

class data
{
	String comdoc="";
	Map <String,String> code = new HashMap <String,String>();
}

public class Run 
{
	public static void main (String arg[])
	{
		new Run().Gui(); 
	}
	
	void Gui()
	{
		JFrame frm = new JFrame ("LZW");
		frm.setSize(500,300);
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel pnl = new JPanel();
		frm.add(pnl);
		
		pnl.setLayout(null);
		
		JLabel l = new JLabel("File path");
		l.setBounds(211, 51, 71, 31);
		pnl.add(l);
		
		JTextField tf = new JTextField();
		tf.setBounds(99, 91, 277, 33);
		pnl.add(tf);
		
		JButton b1 = new JButton("Compress");
		b1.setBounds(33, 177, 111, 51);
		pnl.add(b1);
		
		JButton b2 = new JButton("Decompress");
		b2.setBounds(333, 177, 111, 51);
		pnl.add(b2);
		
		b1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				Comp(tf.getText());
				JOptionPane.showMessageDialog(null, "Done!!");
			}
		});
		
		b2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				Decomp(tf.getText());
				JOptionPane.showMessageDialog(null, "Done!!");
			}
		});

		frm.setVisible(true);
		
	}
	
	void Comp (String path)
	{
		String doc = new Run().readFromDataFile(path);
		ArrayList <node> nodes = new ArrayList <node>();
		String code [] = new String [128];
		int arr[] = new int [128];
		String comdoc="";
		node leave;
		
		for(int i=0 ; i<doc.length() ; i++)
			arr[(int)doc.charAt(i)]++;
		
		
		
		for(int i=0 ; i<128 ; i++)
			if(arr[i]>0)
			{
				leave = new node();
				leave.ch = (char)i;
				leave.freq = arr[i];
				nodes.add(leave);
			}
		
		Collections.sort(nodes);
		
		tree hufTree = new tree();
		hufTree.constTree(nodes);
		code = hufTree.getCode();
		
		for(int i=0 ; i<doc.length() ; i++)
			comdoc += code[doc.charAt(i)];
		
		new Run().writeToComFile(code,comdoc);
	}
	
	String readFromDataFile (String fileName)
	{
		String doc="", tmp;
		try
		{
			Scanner in = new Scanner (new File (fileName));

			while (in.hasNext())
			{
				tmp = in.next();
				doc+=tmp;
			}
			
			in.close();
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null, "Path is invalid");
		}
		
		return doc;
	}
	
	void writeToComFile (String code[], String comdoc)
	{
		File comFile = new File ("comFile.txt");
		
		try
		{
			comFile.createNewFile();
			FileWriter writer= new FileWriter(comFile);
			
			for(int i=0 ; i<128 ; i++)
				if(code[i]!=null)
					writer.write((char)i + " " + code[i] + " ");
			
			writer.write(comdoc);
			
			writer.close();
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, "Error");
		}
	}
	
	void Decomp (String path)
	{
		data comData = readFromComFile(path);
		String doc="", tmp="";
		
		for(int i=0 ; i<comData.comdoc.length() ; i++)
		{
			tmp+=comData.comdoc.charAt(i);
			if(comData.code.containsKey(tmp))
			{
				doc+=comData.code.get(tmp);
				tmp="";
			}
		}
		
		new Run().writeToDataFile(doc);
	}

	data readFromComFile (String path)
	{
		data comData = new data();
		String s, t;
		
		try
		{
			Scanner in = new Scanner (new File(path));
			
			while (in.hasNext())
			{
				t = in.next();
				if(in.hasNext())
				{
					s = in.next();
					comData.code.put(s,t);
				}
				else
					comData.comdoc=t;
					
			}
						
			in.close();
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null, "Error");
		}
		
		return comData;
	}
	
	void writeToDataFile (String doc)
	{
		File dataFile = new File ("newData.txt");
		
		try
		{
			dataFile.createNewFile();
			FileWriter writer = new FileWriter (dataFile);
			writer.write(doc);
			writer.close();
		}
		catch (Exception ex)
		{
			JOptionPane.showMessageDialog(null, "Error");
		}
	}
}

// E:\WorkSpace\WorkSpace_J\Huffman\data.txt
// E:\WorkSpace\WorkSpace_J\Huffman\comFile.txt