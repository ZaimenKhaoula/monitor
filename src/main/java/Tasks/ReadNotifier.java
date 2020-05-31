package Tasks;

import java.util.ArrayList;

public class ReadNotifier extends Task {
	private boolean all =false;
	private ArrayList<String> result = new ArrayList<String>();
	
	public boolean isAll() {
		return all;
	}
	public void setAll(boolean all) {
		this.all = all;
	}

	public ArrayList<String> getResult() {
		return result;
	}
	public void setResult(ArrayList<String> resulat) {
		this.result = resulat;
	}
	
	@Override
	public String toString () {
		String r = "";
		for(String s: result) {
			r=r+" "+s;
		}
		
		return r;
	}

}
