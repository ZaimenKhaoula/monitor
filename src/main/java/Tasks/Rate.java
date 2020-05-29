package Tasks;

import java.util.ArrayList;

public abstract class Rate {
private ArrayList<String> parameters;
 	
 	public Rate() {
 		setParameters(new ArrayList<String>());
 	}

	public ArrayList<String> getParameters() {
		return parameters;
	}

	public void setParameters(ArrayList<String> parameters) {
		this.parameters = parameters;
	}

}
