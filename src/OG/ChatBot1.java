package OG;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.Color;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.lang.Math;

public class ChatBot1 extends JFrame implements KeyListener{

	JPanel p=new JPanel();
	JTextArea dialog=new JTextArea(15,50);
	JTextArea input=new JTextArea(1,50);
	JScrollPane scroll=new JScrollPane(
		dialog,
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
	);
	
	String[][] chat1={
		{"About you","about you","ABOUT YOU"},
		{"Hey Welcome to Charbot. Trough Digital library you can simply windowshop books and can issue them for you"},
                
                {"hi", "hello", "hey"},
                {"Hi there!", "Hello!", "Hey!"},
                
                {"about","About", "ABOUT"},
                {"This Project is made by students of LPU"},
                
                {"how are you?","hwr","how are you"},
                {"I'm doing well, thank you. How about you?"},
                {"I'm a chatbot, so I don't have feelings like humans."}
                
                
	};
	
	public static void main(String[] args){
		new ChatBot1();
	}
	
	public ChatBot1(){
		setSize(600,350);
		setResizable(false);
		
		dialog.setEditable(false);
		input.addKeyListener(this);
	
		p.add(scroll);
		p.add(input);
		p.setBackground(new Color(238,232,170));
		add(p);
		
		setVisible(true);
	}
	
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			input.setEditable(false);
			//-----grab quote-----------
			String quote=input.getText();
			input.setText("");
			addText("-->You:\t"+quote);
			quote.trim();
			while(
				quote.charAt(quote.length()-1)=='!' ||
				quote.charAt(quote.length()-1)=='.' ||
				quote.charAt(quote.length()-1)=='?'
			){
				quote=quote.substring(0,quote.length()-1);
			}
			quote.trim();
			byte response=0;

			int j=0;
			while(response==0){
				if(inArray(quote.toLowerCase(),chat1[j*2])){
					response=2;
					int r=(int)Math.floor(Math.random()*chat1[(j*2)+1].length);
					addText("\n-->Rohit\t"+chat1[(j*2)+1][r]);
				}
				j++;
				if(j*2==chat1.length-1 && response==0){
					response=1;
				}
			}
			
			if(response==1){
				int r=(int)Math.floor(Math.random()*chat1[chat1.length-1].length);
				addText("\n-->Rohit\t"+chat1[chat1.length-1][r]);
			}
			addText("\n");
		}
	}
	
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			input.setEditable(true);
		}
	}
	
	public void keyTyped(KeyEvent e){}
	
	public void addText(String str){
		dialog.setText(dialog.getText()+str);
	}
	
	public boolean inArray(String in,String[] str){
		boolean match=false;
		for(int i=0;i<str.length;i++){
			if(str[i].equals(in)){
				match=true;
			}
		}
		return match;
	}
}