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
		dau = new ArrayList<String>();
		doUT = new ArrayList<Double>();

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
		try {
			tinh(pt, k);
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
			// Khong co ky tu phu hop
			if (s.charAt(i) < 48 && s.charAt(i) > 57 && dau.indexOf("" + s.charAt(i)) == -1) {
				k.setTinhDuoc(false);
				return 0;
			}

			// doan them
			// xu ly so am duong
			if (s.charAt(i) == '+' || s.charAt(i) == '-') {
				if (i + 1 < s.length()) {
					if (s.charAt(i + 1) >= 48 && s.charAt(i + 1) <= 57) {
						if (i == 0) {
							if (s.charAt(i) == '+') {
								s = s.substring(1, s.length());
							} else {
								s = "(0-1)*" + s.substring(1, s.length());
							}
							i--;
							continue;
						} else if (s.charAt(i - 1) == '(') {
							if (s.charAt(i) == '+') {
								s = s.substring(0, i) + "(1-0)*" + s.substring(i + 1, s.length());
							} else {
								s = s.substring(0, i) + "(0-1)*" + s.substring(i + 1, s.length());
							}
							i--;
							continue;
						}
					}
				}
				int am = 0;
				int dg = 0;
				int t = i;
				while (s.charAt(t) == '+' || s.charAt(t) == '-') {
					if (s.charAt(t) == '+')
						dg++;
					else
						am++;
					t++;
					if (t == s.length()) {
						k.tinhDuoc = false;
						return 0;
					}
				}
				if (s.charAt(t) == '*' || s.charAt(t) == '/' || s.charAt(t) == ')') {
					k.tinhDuoc = false;
					return 0;
				}
				if (am + dg > 1) {
					if (am % 2 == 1) {
						if (i == 0) {
							s = s.substring(0, i) + "(0-1)*" + s.substring(t, s.length());
						} else if (s.charAt(i - 1) == '(') {
							s = s.substring(0, i) + "(0-1)*" + s.substring(t, s.length());
						} else
							s = s.substring(0, i) + "+(0-1)*" + s.substring(t, s.length());
					} else {
						if (i == 0) {
							s = s.substring(0, i) + "(1-0)*" + s.substring(t, s.length());
						} else if (s.charAt(i - 1) == '(') {
							s = s.substring(0, i) + "(1-0)*" + s.substring(t, s.length());
						} else
							s = s.substring(0, i) + "+(1-0)*" + s.substring(t, s.length());
					}
				}

			} else if (s.charAt(i) == '*' || s.charAt(i) == '/') {
				String kyT ="*";
				int am = 0;
				int dg = 0;
				int t = i + 1;
				
				if(s.charAt(i)=='/') {
					kyT = "/";
				}
				if (s.charAt(t) == '+' || s.charAt(t) == '-') {
					while (s.charAt(t) == '+' || s.charAt(t) == '-') {
						if (s.charAt(t) == '+')
							dg++;
						else
							am++;
						t++;
						if (t == s.length()) {
							k.tinhDuoc = false;
							return 0;
						}
					}
					if (s.charAt(t) == ')') {
						k.tinhDuoc = false;
						return 0;
					}
				} else if (s.charAt(t) == '*' || s.charAt(t) == '/') {
					k.tinhDuoc = false;
					return 0;
				}
//				if (am + dg > 1) {
				if (am % 2 == 1) {
					s = s.substring(0, i) + "*(0-1)"+kyT + s.substring(t, s.length());
				} else {
					s = s.substring(0, i) + kyT + s.substring(t, s.length());
				}
//				}
			}

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
					if (flag && s.charAt(i + 1) == ')') {
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

			if (toanTu.size() == 0 || s.charAt(i) == '(') {
				toanTu.add(s.charAt(i) + "");
				continue;
			}

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

			// trg hop do uu tien nho hon hoac bang
			if (s.charAt(i) != '(' && s.charAt(i) != ')') {
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