package process;

public class FormatString {
	public String formatString(String pt) {
		for (int i = 0; i < pt.length(); i++) {
			// xu ly nhap nhieu ngoac hon can thiet
			if (pt.charAt(i) == '(') {
				if(i+1<pt.length()) {
					if(pt.charAt(i+1)=='-') {
						if (i == 0) {
							pt = "(0" + pt.substring(1, pt.length());
							i = 1;
						} else if (i + 1 < pt.length()) {
							pt = pt.substring(0, i + 1) + "0" + pt.substring(i + 1, pt.length());
							System.out.println(pt);
							i = i + 1;
						}
						continue;
					}
				}
				if (i == 0) {
					pt = "(0+" + pt.substring(1, pt.length());
					i = 2;
				} else if (i + 1 < pt.length()) {
					pt = pt.substring(0, i + 1) + "0+" + pt.substring(i + 1, pt.length());
					System.out.println(pt);
					i = i + 2;
				}
				continue;
			}

			// xu ly am duong
			if (pt.charAt(i) == '-' || pt.charAt(i) == '+') {
				//de fix loi tren doan su ly nhap nhieu ngoac
				if(i-1>=0) {
					if(pt.charAt(i-1)=='+'||pt.charAt(i-1)=='-') {
						i-=2;
						continue;
					}
				}//
				int am = 0, tg = i;
				if (pt.charAt(i) == '-')
					am = 1;
				while (i + 1 < pt.length()) {
					if (pt.charAt(i + 1) != '-' && pt.charAt(i + 1) != '+')
						break;
					if (pt.charAt(i + 1) == '-') {
						am++;
					}
					i++;
				}
				i++; // i la vi tri dau tien khac +-
				if (tg == 0) {
					if (am % 2 == 0) {
						pt = pt.substring(i, pt.length());
						i = -1;
					} else {
						pt = "(0-1)*" + pt.substring(i, pt.length());
						i = 5;
					}
				} else {
					if (am % 2 == 0) {
						if (pt.charAt(tg - 1) == '*' || pt.charAt(tg - 1) == '/' || pt.charAt(tg - 1) == '(') {
							if(pt.charAt(tg-1)=='/') {
								pt = pt.substring(0, tg) + "(1-0)/" + pt.substring(i, pt.length());
							}else {
								pt = pt.substring(0, tg) + "(1-0)*" + pt.substring(i, pt.length());
							}
							i = tg + 5;
						} else {
							if(pt.charAt(tg-1)=='/') {	
								pt = pt.substring(0, tg) + "+(1-0)/" + pt.substring(i, pt.length());
							}else {
								pt = pt.substring(0, tg) + "+(1-0)*" + pt.substring(i, pt.length());
							}
							i = tg + 6;
						}
					} else {
						if (pt.charAt(tg - 1) == '*' || pt.charAt(tg - 1) == '(') {
							pt = pt.substring(0, tg) + "(0-1)*" + pt.substring(i, pt.length());
							i = tg + 5;
						} else if (pt.charAt(tg - 1) == '/' || pt.charAt(tg - 1) == '(') {
							pt = pt.substring(0, tg) + "(0-1)/" + pt.substring(i, pt.length());
							i = tg + 5;
						} else {
							pt = pt.substring(0, tg) + "+(0-1)*" + pt.substring(i, pt.length());
							i = tg + 6;
						}
					}
				}
			}
		}

		return pt;
	}
}
