package process;

import java.util.ArrayList;

public class Process {

	private ArrayList<String> toanTu, dau;
	private ArrayList<Double> toanHang, doUT;
	private KQ k;
	public ArrayList arKQ;

	public Process() {
		k = new KQ();
		arKQ = new ArrayList();
		toanTu = new ArrayList<String>();
		toanHang = new ArrayList<Double>();
		dau = new ArrayList<String>(); //dau
		doUT = new ArrayList<Double>(); //do uu tien

		doUT.add(0d);
		doUT.add(0d);
		doUT.add(1d);
		doUT.add(1d);
		doUT.add(2d);
		doUT.add(2d);

		dau.add("+");
		dau.add("-");
		dau.add("*");
		dau.add("/");
		dau.add("(");
		dau.add(")");
	}

	public ArrayList<Object> getKQ(String pt) {
		FormatString f = new FormatString();
		try {
			tinh(f.formatString(pt), k);
		} catch (Exception e) {
			arKQ.add(false);
			return arKQ;
		}
		arKQ.add(k.getTinhDuoc());
		arKQ.add(k.getKQ());
		return arKQ;
	}

	public double tinh(String s, KQ k) {
		double kq = 0;
		for (int i = 0; i < s.length(); i++) {
			// truong hop la so
			if (s.charAt(i) >= 48 && s.charAt(i) <= 57) {
				String so = "";
				int tg = i;
				boolean flag = false;
				if (i > 0 && i < s.length()) {
					if (s.charAt(i - 1) == '(') {
						flag = true;
					}
				}

				while (i < s.length() && ((s.charAt(i) >= 48 && s.charAt(i) <= 57) || s.charAt(i) == '.')) {
					so += s.charAt(i);
					if (i + 1 < s.length()
							&& ((s.charAt(i + 1) >= 48 && s.charAt(i + 1) <= 57) || s.charAt(i + 1) == '.')) {
						i++;
					} else {
						break;
					}
				}
				if (i + 1 < s.length()) {
					if (flag && s.charAt(i + 1) == ')') { //neu co dong mo ngoac vd  1+(3) -> 1+3 bo 2 ca ngoac thua di
						if (i + 2 < s.length()) {
							s = s.substring(0, tg) + s.substring(tg + 1, i + 1) + s.substring(i + 2, s.length());
						} else {
							s = s.substring(0, tg) + s.substring(tg + 1, i + 1);
						}
					}
				}
				toanHang.add(Double.parseDouble(so));
				continue;
			}
			//stack rong hoac toan tu la ( thi auto them
			if (toanTu.size() == 0 || s.charAt(i) == '(') {
				toanTu.add(s.charAt(i) + "");
				continue;
			}
			
			//neu dinh stack la ( cx auto them
			if (toanTu.get(toanTu.size() - 1).equals("(")) {
				toanTu.add(s.charAt(i) + "");
				continue;
			}

			// trg hop doong ngoac
			if (s.charAt(i) == ')') {
				double s1, s2;
				while (!toanTu.get(toanTu.size() - 1).equals("(")) {
					String tt = toanTu.get(toanTu.size() - 1);
					toanTu.remove(toanTu.size() - 1);
					s1 = toanHang.get(toanHang.size() - 1);
					s2 = toanHang.get(toanHang.size() - 2);
					toanHang.remove(toanHang.size() - 1);
					toanHang.remove(toanHang.size() - 1);
					if (tt.equals("+")) {
						toanHang.add(s1 + s2);
					} 
					if (tt.equals("-")) {
						toanHang.add(s2 - s1);
					}
					if (tt.equals("*")) {
						toanHang.add(s1 * s2);
					}
					if (tt.equals("/")) { 
						try {
							toanHang.add(s2 / s1);
						} catch (Exception e) {
							// TODO: handle exception
							k.setTinhDuoc(false);
							return 0;
						}
					}
				}

				toanTu.remove(toanTu.size() - 1);
			}
			// do uu tien nhon hon hoac bang
			if (s.charAt(i) != '(' && s.charAt(i) != ')') {

				System.out.println(s.charAt(i));
				double dut1 = doUT.get(dau.indexOf(s.charAt(i) + ""));
				double s1, s2;
				double dut0 = doUT.get(dau.indexOf(toanTu.get(toanTu.size() - 1)));
				if (dut1 > dut0) {
					toanTu.add(s.charAt(i) + "");
					continue;
				}
				String tt = toanTu.get(toanTu.size() - 1);
				toanTu.remove(toanTu.size() - 1);
				s1 = toanHang.get(toanHang.size() - 1);
				s2 = toanHang.get(toanHang.size() - 2);
				toanHang.remove(toanHang.size() - 1);
				toanHang.remove(toanHang.size() - 1);
				if (tt.equals("+")) {
					toanHang.add(s1 + s2);
				}
				if (tt.equals("-")) {
					toanHang.add(s2 - s1);
				}
				if (tt.equals("*")) {
					toanHang.add(s1 * s2);
				}
				if (tt.equals("/")) {
					try {
						toanHang.add(s2 / s1);
					} catch (Exception e) {
						// TODO: handle exception
						k.setTinhDuoc(false);
						return 0;
					}
				}
				toanTu.add(s.charAt(i) + "");
			}
		}
		// buoc cuoi va kiem tra dung sai
		while (toanHang.size() != 1) {
			double s1, s2;
			String tt = toanTu.get(toanTu.size() - 1);
			toanTu.remove(toanTu.size() - 1);
			s1 = toanHang.get(toanHang.size() - 1);
			s2 = toanHang.get(toanHang.size() - 2);
			toanHang.remove(toanHang.size() - 1);
			toanHang.remove(toanHang.size() - 1);
			if (tt.equals("+")) {
				toanHang.add(s1 + s2);
			}
			if (tt.equals("-")) {
				toanHang.add(s2 - s1);
			}
			if (tt.equals("*")) {
				toanHang.add(s1 * s2);
			}
			if (tt.equals("/")) {
				try {
					toanHang.add(s2 / s1);
				} catch (Exception e) {
					// TODO: handle exception
					k.setTinhDuoc(false);
					return 0;
				}
			}
		}

		if (toanTu.size() == 0) {
			kq = toanHang.get(0);
		} else {
			k.setTinhDuoc(false);
		}

		k.setKQ(kq);
		return kq;
	}

}

class KQ {
	boolean tinhDuoc = true;
	double kq;

	public void setTinhDuoc(boolean b) {
		this.tinhDuoc = b;
	}

	public void setKQ(double kq) {
		this.kq = kq;
	}

	public boolean getTinhDuoc() {
		return this.tinhDuoc;
	}

	public double getKQ() {
		return this.kq;
	}
}