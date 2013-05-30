package com.droidshop.api.util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
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

import com.droidshop.api.Logger;

public class HibernateUtil
{
	private static final SessionFactory sessionFactory;

	private static final String configFile = "hibernate.cfg.xml";

	static
	{
		try
		{
			Configuration configuration = new Configuration();
			configuration.configure(configFile);
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
					.buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
		catch (Throwable ex)
		{
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

	public static void shutdown()
	{
		// Close caches and connection pools
		getSessionFactory().close();
	}

	public void generateUpdateScript()
	{
		Configuration configuration = new Configuration();
		configuration.configure(configFile);
		Dialect dialect = new org.hibernate.dialect.MySQL5Dialect();
		Connection connection = null;
		try
		{
			connection = DriverManager.getConnection("jdbc:mysql://localhost/droidshop?user=root&password=1234567");
			String[] sql = configuration.generateSchemaUpdateScript(dialect, new DatabaseMetadata(connection, dialect));

			PrintWriter writer = null;
			try
			{
				writer = new PrintWriter("src/main/resources/database/schema-update.sql");
				for (String query : sql)
				{
					System.out.println(query);
					writer.println(query);
				}
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			finally
			{
				writer.close();
			}

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		Logger.info(this.getClass(), "Update script generated");

	}

	public void generateCreateScript(boolean exportToDB, boolean justDrop, boolean justCreate)
	{
		Configuration configuration = new Configuration();
		configuration.configure(configFile);
		SchemaExport export = new SchemaExport(configuration);
		export.execute(true, exportToDB, justDrop, justCreate);
	}

	public static void main(String args[])
	{
		HibernateUtil util = new HibernateUtil();
		util.generateUpdateScript();
		/*util.generateCreateScript(true, false, true);*/
	}
}
