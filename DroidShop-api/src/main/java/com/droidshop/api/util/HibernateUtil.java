package com.droidshop.api.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.Dialect;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.DatabaseMetadata;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class HibernateUtil {
	private static final SessionFactory sessionFactory;

	private static final String configFile = "hibernate.cfg.xml";
	
	static {
		try {
			Configuration configuration = new Configuration();
			configuration.configure(configFile);
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
					.applySettings(configuration.getProperties())
					.buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}
	
	public void updateSchema()
	{
		Configuration configuration = new Configuration();
		configuration.configure(configFile);
		Dialect dialect = new org.hibernate.dialect.MySQL5Dialect();
		Connection connection = null;
		try
		{
			connection = DriverManager
					.getConnection("jdbc:mysql://fittrdb.ch30tsalfl52.ap-southeast-1.rds.amazonaws.com/fittr?user=root&password=fittr12345");
			String[] sql = configuration.generateSchemaUpdateScript(dialect, new DatabaseMetadata(connection, dialect));

			for(String query : sql)
			{
				System.out.println(query);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void dumpSchema()
	{
		Configuration configuration = new Configuration();
		configuration.configure(configFile);
		SchemaExport export = new SchemaExport(configuration);
		export.execute(true, false, false, false);
	}
}
