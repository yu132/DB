package db.util.randomstr;

public class RandomStr {

	/**
	 * @author http://www.cnblogs.com/dongliyang/archive/2013/04/01/2994554.html
	 * 
	 * �Ǳ�����Ʒ����ѧϰʹ�ã�δ����ҵʹ�ã�����Ȩ��ɾ��
	 * 
	 * ��������ַ�����ͬʱ�������֡���Сд��ĸ
	 * 
	 * @param len
	 *            �ַ������ȣ�����С��3
	 * @return String ����ַ���
	 */
	public static void main(String[] args) {
		System.out.println(randomStr(10));
	}
	
	public static String randomStr(int len) {
		if (len < 3) {
			throw new IllegalArgumentException("�ַ������Ȳ���С��3");
		}
		// ���飬���ڴ������ַ�
		char[] chArr = new char[len];
		// Ϊ�˱�֤����������֡���Сд��ĸ
		chArr[0] = (char) ('0' + StdRandom.uniform(0, 10));
		chArr[1] = (char) ('A' + StdRandom.uniform(0, 26));
		chArr[2] = (char) ('a' + StdRandom.uniform(0, 26));

		char[] codes = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
				'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd',
				'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
				'z' };
		// charArr[3..len-1]�������codes�е��ַ�
		for (int i = 3; i < len; i++) {
			chArr[i] = codes[StdRandom.uniform(0, codes.length)];
		}

		// ������chArr�������
		for (int i = 0; i < len; i++) {
			int r = i + StdRandom.uniform(len - i);
			char temp = chArr[i];
			chArr[i] = chArr[r];
			chArr[r] = temp;
		}

		return new String(chArr);
	}
}
