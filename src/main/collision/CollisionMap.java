package main.collision;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class CollisionMap {

    public final Set<Point> collisionPoints = new HashSet<>();

    public void addCollision(int col, int row) {
        collisionPoints.add(new Point(col, row));
    }


    public void addBorderCollision(int maxCol, int maxRow) {
        System.out.println("[COLLISION] Setting max world collision limits: " + maxCol + "x" + maxRow);
        for (int col = 0; col < maxCol; col++) {
            for (int row = 0; row < maxRow; row++) {
                boolean isBorder = (row == 0 || col == 0 ||
                        row == maxRow - 1 || col == maxCol - 1);

                if (isBorder) {
                    addCollision(col, row);
                }
            }
        }
    }

    public void loadFromFile(String filePath) {

        System.out.println("[COLLISION] Loading collisionMap: " + filePath);
        collisionPoints.clear(); // limpa antes de carregar

        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.trim().split("\\s+");
                if (tokens.length == 2) {
                    int col = Integer.parseInt(tokens[0]);
                    int row = Integer.parseInt(tokens[1]);
                    addCollision(col, row);
                }
            }

            br.close();
        } catch (Exception e) {
            System.err.println("[COLLISION] Error loading collision points: " + filePath);
            e.printStackTrace();
        }
    }
}