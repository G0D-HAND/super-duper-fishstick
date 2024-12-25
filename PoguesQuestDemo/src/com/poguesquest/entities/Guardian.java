package com.poguesquest.entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Guardian extends Entity {
    private static final int MOVEMENT_SPEED = 3; // Constant movement speed
    private static final int ATTACK_DAMAGE = 2; // Constant attack damage
    private static final int MAX_HEALTH = 5; // Constant max health
    private int health = MAX_HEALTH;
    private int lineOfSight;
    private Player player;

    public Guardian(int x, int y, int width, int height, BufferedImage sprite, int lineOfSight, Player player) {
        super(x, y, width, height);
        this.lineOfSight = lineOfSight;
        this.player = player;
    }

    @Override
    public void damage(int amount) {
        health -= amount;
        if (health < 0) {
            health = 0;
        }
    }

    @Override
    public void update() {
        if (player.isEnemyInSight(this)) {
            retreatFromPlayer();
        } else if (isPlayerInSight()) {
            if (isHealthLow()) {
                retreatFromPlayer();
            } else if (isPlayerClose()) {
                player.damage(ATTACK_DAMAGE); // Use the constant attack value
            } else {
                pursuePlayer();
            }
        }
    }

    private boolean isPlayerInSight() {
        double distance = getDistance(x, y, player.getX(), player.getY());
        return distance <= lineOfSight;
    }

    private boolean isPlayerClose() {
        double distance = getDistance(x, y, player.getX(), player.getY());
        return distance <= 20; // Player is within 20 pixels
    }

    private boolean isHealthLow() {
        return health <= (MAX_HEALTH / 3); // Health is 1/3 or less
    }

    private void pursuePlayer() {
        int playerX = player.getX();
        int playerY = player.getY();

        if (x < playerX) {
            x += MOVEMENT_SPEED;
        } else if (x > playerX) {
            x -= MOVEMENT_SPEED;
        }

        if (y < playerY) {
            y += MOVEMENT_SPEED;
        } else if (y > playerY) {
            y -= MOVEMENT_SPEED;
        }

        updateHitbox();
    }

    private void retreatFromPlayer() {
        int playerX = player.getX();
        int playerY = player.getY();

        if (x < playerX) {
            x -= MOVEMENT_SPEED;
        } else if (x > playerX) {
            x += MOVEMENT_SPEED;
        }

        if (y < playerY) {
            y -= MOVEMENT_SPEED;
        } else if (y > playerY) {
            y += MOVEMENT_SPEED;
        }

        updateHitbox();
    }

    // Helper method to calculate distance between two points
    private double getDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    @Override
    public void render(Graphics g) {
        // Add additional rendering for the Guardian if needed
    }
}