import java.util.concurrent.TimeUnit;

import static java.lang.Math.sin;
import static java.lang.Math.cos;

public class Console3DCubes {
    
    static double cubeWidth = 20;
    static int width = 160, height = 44;
    static char backgroundASCIICode = ' ';
    static double incrementSpeed = 0.6f;
    static int distanceFromCam = 100;

    static char[] buffer = new char[width * height];
    static double[] zBuffer = new double[width * height];

    static double horizontalOffset = -2 * cubeWidth;
    static double k1 = 40;

    static double A, B, C;
    
    public static void main(String[] args) {
        System.out.print("\033[2J");

        while (true) {

            for (int i = 0; i < buffer.length; i++)
                buffer[i] = backgroundASCIICode;
            
            for (int i = 0; i < zBuffer.length; i++)
                zBuffer[i] = 0;
            
            cubeWidth = 20;
            horizontalOffset = -2 * cubeWidth;
            // first cube
            for (double cubeX = -cubeWidth; cubeX < cubeWidth; cubeX += incrementSpeed) {
                for (double cubeY = -cubeWidth; cubeY < cubeWidth; cubeY += incrementSpeed) {
                    calculateForSurface(cubeX, cubeY, -cubeWidth, '@');
                    calculateForSurface(cubeWidth, cubeY, cubeX, '$');
                    calculateForSurface(-cubeWidth, cubeY, -cubeX, '~');
                    calculateForSurface(-cubeX, cubeY, cubeWidth, '#');
                    calculateForSurface(cubeX, -cubeWidth, -cubeY, ';');
                    calculateForSurface(cubeX, cubeWidth, cubeY, '+');
                }
            }
            
            cubeWidth = 10;
            horizontalOffset = 1 * cubeWidth;
            // second cube
            for (double cubeX = -cubeWidth; cubeX < cubeWidth; cubeX += incrementSpeed) {
                for (double cubeY = -cubeWidth; cubeY < cubeWidth; cubeY += incrementSpeed) {
                    calculateForSurface(cubeX, cubeY, -cubeWidth, '@');
                    calculateForSurface(cubeWidth, cubeY, cubeX, '$');
                    calculateForSurface(-cubeWidth, cubeY, -cubeX, '~');
                    calculateForSurface(-cubeX, cubeY, cubeWidth, '#');
                    calculateForSurface(cubeX, -cubeWidth, -cubeY, ';');
                    calculateForSurface(cubeX, cubeWidth, cubeY, '+');
                }
            }
            
            cubeWidth = 5;
            horizontalOffset = 8 * cubeWidth;
            // third cube
            for (double cubeX = -cubeWidth; cubeX < cubeWidth; cubeX += incrementSpeed) {
                for (double cubeY = -cubeWidth; cubeY < cubeWidth; cubeY += incrementSpeed) {
                    calculateForSurface(cubeX, cubeY, -cubeWidth, '@');
                    calculateForSurface(cubeWidth, cubeY, cubeX, '$');
                    calculateForSurface(-cubeWidth, cubeY, -cubeX, '~');
                    calculateForSurface(-cubeX, cubeY, cubeWidth, '#');
                    calculateForSurface(cubeX, -cubeWidth, -cubeY, ';');
                    calculateForSurface(cubeX, cubeWidth, cubeY, '+');
                }
            }
            
            System.out.print("\033[H");
            for (int k = 0; k < width * height; k++) {
                if (k % width == 0) {
                    System.out.println();
                } else {
                    System.out.print(buffer[k]);
                }
            }
            
            A += 0.05;
            B += 0.05;
            C += 0.01;
            
            usleep(8000 * 2);
        }
    }
    
    static void calculateForSurface(double cubeX, double cubeY, double cubeZ, char ch) {
        double x = calculateX(cubeX, cubeY, cubeZ);
        double y = calculateY(cubeX, cubeY, cubeZ);
        double z = calculateZ(cubeX, cubeY, cubeZ) + distanceFromCam;
        
        double ooz = 1 / z;

        int xp = (int) (width / 2 + horizontalOffset + k1 * ooz * x * 2);
        int yp = (int) (height / 2 + k1 * ooz * y);
        
        int idx = xp + yp * width;
        if (idx >= 0 && idx < width * height) {
            if (ooz > zBuffer[idx]) {
                zBuffer[idx] = ooz;
                buffer[idx] = ch;
            }
        }
    }

    static double calculateX(double i, double j, double k) {
        return j * sin(A) * sin(B) * cos(C) - k * cos(A) * sin(B) * cos(C) +
               j * cos(A) * sin(C) + k * sin(A) * sin(C) + i * cos(B) * cos(C);
    }

    static double calculateY(double i, double j, double k) {
        return j * cos(A) * cos(C) + k * sin(A) * cos(C) -
               j * sin(A) * sin(B) * sin(C) + k * cos(A) * sin(B) * sin(C) -
               i * cos(B) * sin(C);
    }

    static double calculateZ(double i, double j, double k) {
        return k * cos(A) * cos(B) - j * sin(A) * cos(B) + i * sin(B);
    }
    
    static void usleep(long microseconds) {
        try {
            TimeUnit.MICROSECONDS.sleep(microseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
