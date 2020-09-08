package monitoringApi;



public class RateModel {

   private String type;
   private String params; 

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

public String getParams() {
	return params;
}

public void setParams(String params) {
	this.params = params;
}
 
public String traitementParam() {
	
String[] input = params.split(",");
 

String s="";
String number;
int i;
int j=0;
if(type.compareTo("stochastic")==0) {s=s+input[0]+" ";j++; System.out.println(s);}
while( j<input.length) {
	
	number="";
	i=0;
	char [] ch= input[j].toCharArray();
     for(char c : ch) { if(c !='u' && c !='s'&&  c!='m'  && c !='i' && c !='n' && c !='h' && c !='j' && c!='r') 
    		 {i++;number = number+Character.toString(c);}
     System.out.println(c);
     }
     s=s+" "+number+" "+input[j].substring(i,input[j].length())+" ";
	  j++;
}
	
	
	return s;
}


@Override
public String toString() {
	 
	return " % Rythme de collecte = type "+type+" params {"+ traitementParam()+"}";
	
}

}
