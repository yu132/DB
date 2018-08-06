package db.util.randomstr;

import java.util.Random;

public final class StdRandom {
    
	/**
	 * @author http://www.cnblogs.com/dongliyang/archive/2013/04/01/2994554.html
	 * 
	 * �Ǳ�����Ʒ����ѧϰʹ�ã�δ����ҵʹ�ã�����Ȩ��ɾ��
	 */
	
    //�����������
    private static Random random;
    //����ֵ
    private static long seed;
    
    //��̬����飬��ʼ������ֵ�������������
    static {
        seed = System.currentTimeMillis();
        random = new Random(seed);
    }
    
    //˽�й��캯������ֹʵ����
    private StdRandom() {}
    
    /**
     * ��������ֵ
     * @param s �����������������ֵ
     */
    public static void setSeed(long s){
        seed = s;
        random = new Random(seed);
    }
    
    /**
     * ��ȡ����ֵ
     * @return long �����������������ֵ
     */
    public static long getSeed(){
        return seed;
    }
    
    /**
     * �������0��1֮���ʵ�� [0,1)
     * @return double �����
     */
    public static double uniform(){
        return random.nextDouble();
    }
    
    /**
     * �������0��N-1֮������� [0,N)
     * @param N ����
     * @return int �����
     */
    public static int uniform(int N){
        return random.nextInt(N);
    }
    
    /**
     * �������0��1֮���ʵ�� [0,1)
     * @return double �����
     */
    public static double random(){
        return uniform();
    }
    
    /**
     * �������a��b-1֮������� [a,b)
     * @param a ����
     * @param b ����
     * @return int �����
     */
    public static int uniform(int a,int b){
        return a + uniform(b - a);
    }
    
    /**
     * �������a��b֮���ʵ��
     * @param a ����
     * @param b ����
     * @return double �����
     */
    public static double uniform(double a,double b){
        return a + uniform() * (b - a);
    }
}
