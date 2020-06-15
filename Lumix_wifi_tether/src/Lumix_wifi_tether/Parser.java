package Lumix_wifi_tether;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.InputSource;

public class Parser {
private 	static List<ArrayList<String>> listOfCommands = new ArrayList<ArrayList<String>>();

public Parser(String xml1,String xml2) {
	  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    dbf.setValidating(false);
		List<ArrayList<String>> listOfLists = new ArrayList<ArrayList<String>>();
		listOfCommands = new ArrayList<ArrayList<String>>();
		List<ArrayList<String>> listOfCommandsall = new ArrayList<ArrayList<String>>();
		List<ArrayList<String>> listOfEnabled = new ArrayList<ArrayList<String>>();
	  try {
		  DocumentBuilder db = dbf.newDocumentBuilder();

     
    Document doc = db.parse(new InputSource(new StringReader(xml1)));
     
    NodeList entries = doc.getElementsByTagName("item");
     
    int num = entries.getLength();

	

    
    for (int i=0; i<num; i++) {
        Element node = (Element) entries.item(i);
        NamedNodeMap attributes = node.getAttributes();
        ArrayList<String> list1 = new ArrayList<String>();
        // get the number of nodes in this map
        int numAttrs = attributes.getLength();
        for (int j = 0; j < numAttrs; j++) {
        	
            Attr attr = (Attr) attributes.item(j);
            String attrName = attr.getNodeName();
            String attrValue = attr.getNodeValue();
            list1.add(attrName);
            list1.add(attrValue);
        }
//        System.out.println(list1);	
		listOfLists.add(list1);

    }

	ArrayList<String> list = new ArrayList<String>();

    for (int i=0; i< listOfLists.size();i++) {
    	list=listOfLists.get(i);
    	if (list.get(1).equals("setsetting")) {
    		ArrayList<String> list2=new ArrayList<String>();
    			list2.clear();
    			list2.add(list.get(3));
    			list2.add(list.get(5));
    			if (list.get(6).equals("cmd_value2")) {
    				list2.add(list.get(7));
    				list2.add(list.get(9));
    			}
    			else {
    				list2.add("");
    				list2.add(list.get(7));
    			}
    			
    			if(!list.get(6).equals("func_type")) {
    			listOfCommandsall.add(list2);
    			}
    			
//        System.out.println(list2);
    	}

    }
//    for (int j=0; j<listOfCommands.size();j++) System.out.println(listOfCommands.get(j));
    Document doc2 = db.parse(new InputSource(new StringReader(xml2)));
    
    NodeList entries2 = doc2.getElementsByTagName("item");
    int num2 = entries2.getLength();

    	
    
    for (int i=0; i<num2; i++) {
        Element node = (Element) entries2.item(i);
        NamedNodeMap attributes = node.getAttributes();
        ArrayList<String> list1 = new ArrayList<String>();
        // get the number of nodes in this map
        int numAttrs = attributes.getLength();
        for (int j = 0; j < numAttrs; j++) {
        	
            Attr attr = (Attr) attributes.item(j);
            String attrName = attr.getNodeName();
            String attrValue = attr.getNodeValue();
            list1.add(attrName);
            list1.add(attrValue);
        }
        listOfEnabled.add(list1);
//        System.out.println(list1);	
    }
    for (int k=0;k<listOfCommandsall.size();k++)
    {
    for (int i=0; i<listOfEnabled.size();i++) {
    	if (listOfEnabled.get(i).get(3).equals(listOfCommandsall.get(k).get(3))) {
    		if (listOfEnabled.get(i).get(1).contentEquals("yes")) listOfCommands.add(listOfCommandsall.get(k));
//    		System.out.println(listOfCommandsall.get(k));
    	}
    }
    }

    	
    

    }catch (Exception e) {
    	listOfLists.clear();
    	listOfCommands.clear();
    } 
     
}
public List<ArrayList<String>>GetParsed() {
	return this.listOfCommands;
}
}

	



