package bit;

public class ShiftSimulation {

    public double compute(double[] similarities) {
        double pro = 0.0;
        int len = similarities.length;
        if (len == 1) return similarities[0];

        int[] choose = new int[len];
        double tmp;
        int sum = sum(choose);
        double half = ((double) len) / 2;
        while (sum < len) {
            if (sum > half) {
                double prod = 1.0;
                for (int i = 0; i < len; i++) {
                    if (choose[i] == 0) {
                        tmp = 1 - similarities[i];
                    } else {
                        tmp = similarities[i];
                    }
                    prod *= tmp;
                }
                pro += prod;
            } else if (sum == half) {
                double prod = 1.0;
                for (int i = 0; i < len; i++) {
                    if (choose[i] == 0) {
                        tmp = 1 - similarities[i];
                    } else {
                        tmp = similarities[i];
                    }
                    prod *= tmp;
                }
                pro += prod / 2;
            }
            simShift(choose);
            sum = sum(choose);
        }

        double prod = 1.0;
        for (int i = 0; i < len; i++) {
            prod *= similarities[i];
        }
        pro += prod;
        return pro;

    }

    public double shiftCompute(double[] similarities) {
        double pro = 0.0;
        int len = similarities.length;
        if (len == 1) return similarities[0];

        long stop = (1 << len) - 1;

        double tmp, tmp2, tmpProd;
        boolean cmp;
        double half = ((double) len) / 2;
        for (int choose = 0; choose < stop; choose++) {
            int bits = Integer.bitCount(choose);
            if (bits >= half) {
                double prod = 1.0;
                for (int i = 0; i < len; i++) {
                    tmp = similarities[i];
                    tmp2 = 1 - similarities[i];
                    cmp = ((choose >>> (len - i - 1)) & 0x01) == 0;
                    if (cmp) {
                        tmp = tmp2;
                    }
                    prod *= tmp;
                }

                tmpProd = prod / 2;
                cmp = bits == half;
                if (cmp) {
                    prod = tmpProd;
                }
                pro += prod;
            }
        }

        double prod = 1.0;
        for (int i = 0; i < len; i++) {
            prod *= similarities[i];
        }
        pro += prod;
        return pro;
    }


    public void simShift(int[] arr) {
        int len = arr.length;
        for (int i = len - 1; i >= 0; i--) {
            if (arr[i] == 0) {
                arr[i] = 1;
                break;
            }
            arr[i] = 0;
        }
    }

    public int sum(int[] arr) {
        int sum = 0;
        for (int i = 0; i < arr.length; i++) sum += arr[i];
        return sum;
    }
}
