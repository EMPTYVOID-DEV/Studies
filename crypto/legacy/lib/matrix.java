package lib;

import java.util.Random;

import Jama.Matrix;

public class matrix extends Matrix {
      static final String alpt = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

      public matrix(double[] arg0, int arg1) {
            super(arg0, arg1);
            // TODO Auto-generated constructor stub
      }

      public matrix(double[][] hill) {
            super(hill);
      }

      public matrix(int arg0, int arg1) {
            super(arg0, arg1);
      }

      public static matrix toMatrix(String msg) {
            int n = (int) Math.ceil(Math.sqrt(msg.length()));
            double[][] hill = new double[n][n];
            matrix ma = new matrix(hill);
            msg = msg.toUpperCase();
            for (int i = 0; i < n; i++) {
                  for (int j = 0; j < n; j++) {
                        int index = i * n + j;
                        if (index < msg.length()) {
                              ma.set(i, j, alpt.indexOf(msg.charAt(index)));
                        } else
                              ma.set(i, j, 25);
                  }
            }
            return ma;
      }

      public static matrix random(int rows, int columns, int modulo) {
            Random rand = new Random();
            matrix a = new matrix(rows, columns);
            for (int i = 0; i < rows; i++) {
                  for (int j = 0; j < columns; j++) {
                        a.set(i, j, rand.nextInt(modulo));
                  }
            }
            return a;
      }

      public matrix cast() {
            for (int i = 0; i < this.getRowDimension(); i++) {
                  for (int j = 0; j < this.getColumnDimension(); j++) {
                        this.set(i, j, (int) this.get(i, j));
                  }
            }
            return this;
      }

      public static matrix generateKey(int size) {
            matrix key = matrix.random(size, size, 26);
            while (math.elucdian((int) key.det(), 26) != 1) {
                  key = matrix.random(size, size, 26);
            }
            return key.toModular();
      }

      public static matrix modularInverse(matrix a) throws Exception {
            double det = a.det();
            int detinv = math.modInverse((int) det, 26);
            Matrix modularInv = a.inverse().times(det);
            return new matrix(modularInv.times(detinv).getArray());
      }

      public matrix toModular() {
            for (int i = 0; i < this.getRowDimension(); i++) {
                  for (int j = 0; j < this.getRowDimension(); j++) {
                        if (this.get(i, j) >= 0)
                              this.set(i, j, ((int) this.get(i, j)) % 26);
                        else {
                              int pos = (int) this.get(i, j);
                              while (pos < 0) {
                                    pos += 26;
                              }
                              this.set(i, j, pos);
                        }
                  }
            }
            return this;
      }

      @Override
      public String toString() {
            String res = "";
            for (int i = 0; i < getRowDimension(); i++) {
                  for (int j = 0; j < getRowDimension(); j++) {
                        res += this.get(i, j) + " ";
                  }
            }
            return res;
      }

      public void print() {
            for (int i = 0; i < getRowDimension(); i++) {
                  for (int j = 0; j < getRowDimension(); j++) {
                        System.out.println(this.get(i, j) + " ");
                  }
            }
      }
}
