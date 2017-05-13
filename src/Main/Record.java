package Main;

import java.util.ArrayList;

public class Record {
	public ArrayList<Integer> getMovedto() {
		return movedto;
	}
	public void setMovedto(ArrayList<Integer> movedto) {
		this.movedto = movedto;
	}
	public int getNO() {
		return NO;
	}
	public void setNO(int nO) {
		NO = nO;
	}
	public String getTI() {
		return TI;
	}
	public void setTI(String tI) {
		TI = tI;
	}
	public String getCS() {
		return CS;
	}
	public void setCS(String cS) {
		CS = cS;
	}
	public String getCUI() {
		return CUI;
	}
	public void setCUI(String cUI) {
		CUI = cUI;
	}
	private ArrayList<Integer> movedto = new ArrayList<Integer>();
	private int NO;
	private String TI = "";
	private String CS = "";
	private String CUI;
	public String toString()
	{
		return "NO : " + NO + "\nTI : " + TI + "\nCS : " + CS;
	}
}