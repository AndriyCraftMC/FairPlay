package FairPlay.utils;

import org.bukkit.Location;

public class GetPlayerRotation {
    public static float getRotation(Location loc, Location target) {
        double xDiff = target.getX() - loc.getX();
        double zDiff = target.getZ() - loc.getZ();
        float yaw = (float) Math.toDegrees(-Math.atan(xDiff / zDiff));
        if (zDiff < 0.0D) {
            yaw += 180.0F;
        }
        return yaw;
    }
}
