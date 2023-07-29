package com.kragleh.kbffa.db;

import com.kragleh.kbffa.KBFFA;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl(
                "jdbc:mysql://" +
                        KBFFA.getPlugin().getConfig().getString("mysql.host") +
                        ":" +
                        KBFFA.getPlugin().getConfig().getString("mysql.port")  +
                        "/" +
                        KBFFA.getPlugin().getConfig().getString("mysql.name")
        );
        config.setUsername( KBFFA.getPlugin().getConfig().getString("mysql.user") );
        config.setPassword( KBFFA.getPlugin().getConfig().getString("mysql.pass") );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
