package com.kragleh.kbffa.db;

import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerStorage {

    public static boolean init() {
        try (Connection con = DataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS players (uuid VARCHAR(48) NOT NULL UNIQUE, kit VARCHAR(24) DEFAULT 'default', kills INT DEFAULT 0, deaths INT DEFAULT 0, rampage INT DEFAULT 0, pearl INT DEFAULT 8);");
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean create(Player player) {
        try (Connection con = DataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO players (uuid) VALUES (?) ON DUPLICATE KEY UPDATE uuid = uuid;");
            stmt.setString(1, player.getUniqueId().toString());
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getKills(Player player) {
        try (Connection con = DataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT kills FROM players WHERE uuid = ?;");
            stmt.setString(1, player.getUniqueId().toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("kills");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getDeaths(Player player) {
        try (Connection con = DataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT deaths FROM players WHERE uuid = ?;");
            stmt.setString(1, player.getUniqueId().toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("deaths");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getRampage(Player player) {
        try (Connection con = DataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT rampage FROM players WHERE uuid = ?;");
            stmt.setString(1, player.getUniqueId().toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("rampage");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getKit(Player player) {
        try (Connection con = DataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT kit FROM players WHERE uuid = ?;");
            stmt.setString(1, player.getUniqueId().toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("kit");
            } else {
                return "default";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "default";
        }
    }

    public static int getPearl(Player player) {
        try (Connection con = DataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("SELECT pearl FROM players WHERE uuid = ?;");
            stmt.setString(1, player.getUniqueId().toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("pearl");
            } else {
                return 8;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 8;
        }
    }

    public static void addKill(Player player) {
        int kills = getKills(player);
        try (Connection con = DataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("UPDATE players SET kills = ? WHERE uuid = ?;");
            stmt.setInt(1, (kills + 1));
            stmt.setString(2, player.getUniqueId().toString());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addDeath(Player player) {
        int deaths = getDeaths(player);
        try (Connection con = DataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("UPDATE players SET deaths = ? WHERE uuid = ?;");
            stmt.setInt(1, (deaths + 1));
            stmt.setString(2, player.getUniqueId().toString());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setRampage(Player player, int rampage) {
        try (Connection con = DataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("UPDATE players SET rampage = ? WHERE uuid = ?;");
            stmt.setInt(1, rampage);
            stmt.setString(2, player.getUniqueId().toString());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setKit(Player player, String kit) {
        try (Connection con = DataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("UPDATE players SET kit = ? WHERE uuid = ?;");
            stmt.setString(1, kit);
            stmt.setString(2, player.getUniqueId().toString());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setPearl(Player player, int slot) {
        try (Connection con = DataSource.getConnection()) {
            PreparedStatement stmt = con.prepareStatement("UPDATE players SET pearl = ? WHERE uuid = ?;");
            stmt.setInt(1, slot);
            stmt.setString(2, player.getUniqueId().toString());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
