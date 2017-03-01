import java.util.*;

class node implements Comparable<node>
{
	char ch;
	int freq=0;
	node r=null, l=null;
	
	public int compareTo(node x) 
	{
		return (this.freq > x.freq ? 1 : -1);
	}
		
}

public class tree
{
	private node head;
	private String code[] = new String [128];
	
	void constTree (ArrayList <node> nodes)
	{
		if(nodes.size()==1)
		{
			head=new node();
			head.r=nodes.get(0);
			return ;
		}
		
		node par, tmp;
		
		while (nodes.size()>1)
		{
			par = new node();
			tmp = new node();
			par.freq = nodes.get(0).freq + nodes.get(1).freq;
			par.r= nodes.get(0);
			par.l= nodes.get(1);
			nodes.set(0, tmp);
			nodes.set(1, par);
			nodes.remove(0);
			Collections.sort(nodes);
		}
		
		head=nodes.get(0);
	}
	
	String[] getCode ()
	{
		calCode(head,"");
		return code;
	}
	
	private void calCode (node tmp, String s)
	{
		if(tmp.r==null && tmp.l==null)
		{
			code[(int)tmp.ch]=s;
			return;
		}
		
		if(tmp.r!=null)
			calCode(tmp.r,s+'1');
		if(tmp.l!=null)
			calCode(tmp.l,s+'0');
	}	
}
